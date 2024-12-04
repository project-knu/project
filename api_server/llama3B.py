from transformers import pipeline
from huggingface_hub import login
import torch
# Hugging Face에서 받은 토큰을 사용하여 로그인
login('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')

model_id = "meta-llama/Llama-3.2-3B-Instruct"
pipe = pipeline(
    "text-generation",
    model=model_id,
    torch_dtype=torch.bfloat16,
    device_map="cuda",
)

from transformers import AutoTokenizer
tokenizer = AutoTokenizer.from_pretrained("gpt2")
