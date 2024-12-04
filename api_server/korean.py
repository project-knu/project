from transformers import pipeline 
pipe2 = pipeline("translation_kr_to_en",model="Helsinki-NLP/opus-mt-ko-en")