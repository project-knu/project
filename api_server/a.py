from fastapi import FastAPI
import uvicorn
from qwen_vl_utils import process_vision_info
from qwen import model,processor
import os
from llama3B import *
from keyframe import *

from fastapi.middleware.cors import CORSMiddleware
import requests
from pydantic import BaseModel

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인 허용 (보안상 필요시 특정 도메인으로 제한)
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 HTTP 헤더 허용
)

class HTTPSLinkRequest(BaseModel):
    https_url: str  # 일반 문자열로 처리

def download_https_video(https_url: str, local_path: str) -> None:
    """
    HTTPS URL을 통해 S3에 저장된 동영상을 다운로드하는 함수.

    :param https_url: S3의 HTTPS URL
    :param local_path: 로컬에 저장할 파일 경로
    :raises Exception: 요청 실패 또는 파일 저장 실패 시 발생
    """
    try:
        print(f"Downloading video from {https_url}...")
        response = requests.get(https_url, stream=True)  # Stream으로 데이터 받아오기
        response.raise_for_status()  # 요청 실패 시 예외 발생
        
        with open(local_path, "wb") as file:
            for chunk in response.iter_content(chunk_size=8192):  # 8KB씩 읽기
                if chunk:
                    file.write(chunk)

        print(f"Video downloaded successfully and saved to {local_path}")
    except requests.exceptions.RequestException as e:
        print(f"Error during download: {e}")
        raise
    except Exception as e:
        print(f"Error saving the file: {e}")
        raise

@app.post("/data/")

def f1(request: HTTPSLinkRequest):

    print(f"Received data: {request}")
    print(f"Extracted URL: {request.https_url}")

    #download_https_video(request.https_url, local_path = "/workspace/video3.mp4")
    
    
    frame_interval_file('/workspace/video5.mp4', 2, 10, 120, 0.9,'keyframe')
    txt_list = []
    frame_list=[]
    output_list = []
    for i in os.listdir('keyframe'):
        frame_time = i.split('_')[1].split('.')[0]
        messages = [
                {
                    "role": "user",
                    "content": [
                        {
                            "type": "image",
                            # "image": "file:///workspace/frame/frame_0.00.png",
                            "image" : f"keyframe/{i}"
                        },
                        {"type": "text", "text": "Describe this image."}, #"text": "이미지에 대해 설명해줘"
                    ],
                }
            ]
        
            #  inference
        text = processor.apply_chat_template(messages, tokenize=False, add_generation_prompt=True)
        image_inputs, video_inputs = process_vision_info(messages)
        inputs = processor(
            text=[text],
            images=image_inputs,
            videos=video_inputs,
            padding=True,
            return_tensors="pt",
        )
        inputs = inputs.to("cuda")
    
        # Inference
        generated_ids = model.generate(**inputs, max_new_tokens=128)
        generated_ids_trimmed = [
            out_ids[len(in_ids) :] for in_ids, out_ids in zip(inputs.input_ids, generated_ids)
        ]
        output_text = processor.batch_decode(
            generated_ids_trimmed, skip_special_tokens=True, clean_up_tokenization_spaces=False
        )
        txt_list.append(output_text[0])
        output = f"{frame_time}: {output_text[0]}"
        output_list.append(output)
    output = ",".join(output_list)
    
    messages = [
    {"role": "system", 
    "content": "You are chatbot who should incorporate frame time and corresponding caption text over time into simple crucial report, do not include information of Frame 0.00.png in response"},
    {"role": "user", "content": f"you have to summarize this frame time and description into two sentences{output}"}
    ]


    outputs = pipe(
    messages,
    max_new_tokens=256,pad_token_id=tokenizer.eos_token_id)
    summary = outputs[-1]['generated_text'][-1]['content']
    return {"message": output, "summary": summary}