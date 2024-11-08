from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel

app = FastAPI()

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Pydantic 모델 정의
class VideoInfo(BaseModel):
    location: str
    description: str
    duration: str
    title: str
    date: str

@app.get("/")
def get_info():
    return {
        "location": "seoul",
        "description": "사고 영상",
        "duration": "17:10",
        "title": "남성이 떨어지는영상",
        "date": "2024년 2월 5일"
    }