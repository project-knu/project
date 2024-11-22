import requests
import datetime
from io import BytesIO
import argparse

from PIL import Image
import cv2
import numpy as np
from torchvision.transforms.functional import pil_to_tensor
from torchvision.transforms import Resize
import torch

from moai.load_moai import prepare_moai


def process_video(video_url):
    # Loading MoAI
    moai_model, moai_processor, seg_model, seg_processor, od_model, od_processor, sgg_model, ocr_model \
        = prepare_moai(moai_path='BK-Lee/MoAI-7B', bits=4, grad_ckpt=False, lora=False, dtype='fp16')

    def MoAI(resized_image):
        # Instruction Prompt
        prompt = "Describe this image in detail."

        # Pre-processing for MoAI
        moai_inputs = moai_model.demo_process(image=resized_image, 
                                              prompt=prompt, 
                                              processor=moai_processor,
                                              seg_model=seg_model,
                                              seg_processor=seg_processor,
                                              od_model=od_model,
                                              od_processor=od_processor,
                                              sgg_model=sgg_model,
                                              ocr_model=ocr_model,
                                              device='cuda:0')

        # Generate
        with torch.inference_mode():
            generate_ids = moai_model.generate(**moai_inputs, do_sample=True, temperature=0.9, top_p=0.95, max_new_tokens=256, use_cache=True)

        # Decoding
        answer = moai_processor.batch_decode(generate_ids, skip_special_tokens=True)[0].split('[U')[0]
        # print(answer)
        return answer
    
    def makeDict(d):
        new_d = {}
        for key, value in d.items():
            parts = value.split("ASSISTANT: ")
            if len(parts) > 1:
                assistant_text = parts[1]
                new_d[key] = assistant_text
            else:
                print(f"Warning: 'ASSISTANT: ' not found in value for key {key}. Skipping.")
        return new_d

    vcap = cv2.VideoCapture(video_url)
    fps = vcap.get(cv2.CAP_PROP_FPS)
    sampleRate = fps * 10
    ans = {}
    new_ans = {}
    i = 0
    totalNoFrames = vcap.get(cv2.CAP_PROP_FRAME_COUNT)
    durationInSeconds = totalNoFrames / fps
    while(True):
        # Capture frame-by-frame
        ret, frame = vcap.read()
        if frame is not None:
            if i % sampleRate == 0:
                img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
                image = Image.fromarray(img)

                resized_image = Resize(size=(490, 490), antialias=False)(pil_to_tensor(image))
                resp = MoAI(resized_image)
                
                time_seconds = i / fps
                key = str(datetime.timedelta(seconds=int(time_seconds)))
                ans[key] = resp
                print(ans)
        else:
            print("Frame is None")
            break
        i += 1

    # When everything done, release the capture
    vcap.release()

    new_ans = makeDict(ans)
    # translated_ans = makeTraslation(new_ans)
    
    return new_ans
