# from flask import Flask, request, jsonify
# from flask_cors import CORS  # CORS 추가

# app = Flask(__name__)
# CORS(app)  # CORS 활성화

# import boto3

# def download_from_s3(s3_uri, download_path):
#     """
#     S3 URI를 받아 파일을 다운로드하는 함수
#     :param s3_uri: S3 URI (예: "s3://bucket-name/path/to/file.txt")
#     :param download_path: 다운로드할 로컬 파일 경로
#     """
#     # S3 URI에서 버킷 이름과 키 추출
#     if not s3_uri.startswith("s3://"):
#         raise ValueError("Invalid S3 URI. Must start with 's3://'")

#     s3_parts = s3_uri[5:].split("/", 1)
#     bucket_name = s3_parts[0]
#     object_key = s3_parts[1]

#     # S3 클라이언트 생성
#     s3 = boto3.client("s3")

#     try:
#         # 파일 다운로드
#         print(f"Downloading {s3_uri} to {download_path}...")
#         s3.download_file(bucket_name, object_key, download_path)
#         print(f"Download complete: {download_path}")
#     except Exception as e:
#         print(f"Error downloading file: {e}")
#         raise


# # 외부 요청을 받을 엔드포인트
# @app.route("/metadata", methods=["POST"])  # Lambda에서 요청하는 URL과 맞춤
# def metadata():
#     # Lambda에서 보낸 데이터 수신
#     incoming_data = request.json
#     if not incoming_data:
#         return jsonify({"error": "No data provided"}), 400

#     # Lambda에서 보낸 S3 URI 로그 출력
#     s3_uri = incoming_data.get("message")
#     if not s3_uri:
#         return jsonify({"error": "No 'message' field provided"}), 400

#     print(f"Received S3 URI: {s3_uri}")

#     download_from_s3(s3_uri, "./tmp.mp4")

#     return jsonify({"status": "success"}), 200

# if __name__ == "__main__":
#     app.run(host="0.0.0.0", port=8080)

from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from pydantic import BaseModel
import boto3
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()


app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], 
    allow_credentials=True,
    allow_methods=["*"],  
    allow_headers=["*"],  
)


class MetadataRequest(BaseModel):
    message: str  # S3 URI

def download_from_s3(s3_uri: str, download_path: str):
    """
    S3 URI를 받아 파일을 다운로드하는 함수
    :param s3_uri: S3 URI (예: "s3://bucket-name/path/to/file.txt")
    :param download_path: 다운로드할 로컬 파일 경로
    """
    if not s3_uri.startswith("s3://"):
        raise ValueError("Invalid S3 URI. Must start with 's3://'")

    s3_parts = s3_uri[5:].split("/", 1)
    bucket_name = s3_parts[0]
    object_key = s3_parts[1]

    s3 = boto3.client("s3")

    try:
        print(f"Downloading {s3_uri} to {download_path}...")
        s3.download_file(bucket_name, object_key, download_path)
        print(f"Download complete: {download_path}")
    except Exception as e:
        print(f"Error downloading file: {e}")
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/metadata")
async def metadata(request: MetadataRequest):
    s3_uri = request.message
    if not s3_uri:
        raise HTTPException(status_code=400, detail="No 'message' field provided")

    print(f"Received S3 URI: {s3_uri}")

    # Download the file from S3
    download_from_s3(s3_uri, "./tmp.mp4")

    return JSONResponse(content={"status": "success"}, status_code=200)

# if __name__ == "__main__":
#     import uvicorn
#     uvicorn.run(app, host="0.0.0.0", port=8080)
