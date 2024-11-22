import re
import logging

logger = logging.getLogger(__name__)

def cot_reflection(system_prompt, initial_query, client, model: str, return_full_response: bool=False):
    cot_completion_tokens = 0
    cot_prompt = f"""
        {system_prompt}

        You are an AI assistant that uses a Chain of Thought (CoT) approach with reflection to answer queries.
         Follow these steps:

        1. Think through the problem step by step within the <thinking> tags.
        2. Reflect on your thinking to check for any errors or improvements within the <reflection> tags.
        3. Make any necessary adjustments based on your reflection.
        4. Provide your final, concise answer within the <output> tags.

        Important: The <thinking> and <reflection> sections are for your internal reasoning process only. 
        Do not include any part of the final answer in these sections. 
        The actual response to the query must be entirely contained within the <output> tags.

        Use the following format for your response:
        <thinking>
        [Your step-by-step reasoning goes here. This is your internal thought process, not the final answer.]
        <reflection>
        [Your reflection on your reasoning, checking for errors or improvements]
        </reflection>
        [Any adjustments to your thinking based on your reflection]
        </thinking>
        <output>
        [Your final, concise answer to the query. This is the only part that will be shown to the user.]
        </output>
        """
# 문제를 단계별로 사고 사슬 내에서 <thinking> 태그 안에 생각하십시오.
# <reflection> 태그 내에서 자신의 사고를 반영하여 오류나 개선점을 확인하십시오.
# 반영에 따라 필요한 조정을 하십시오.
# 최종적인 간결한 답변을 <output> 태그 안에 제공하십시오.

# 중요: <thinking> 및 <reflection> 섹션은 내부 추론 과정에만 사용됩니다. 최종 답변의 일부는 이러한 섹션에 포함되지 않습니다. 
# 실제 질의에 대한 답변은 전적으로 <output> 태그 안에 들어가야 합니다.

# 이 형식을 사용하여 응답하십시오: 
# <thinking> 
# [여기에 단계별 추론을 입력합니다. 이는 최종 답변이 아닌 내부 사고 과정입니다.] 
# <reflection> 
# [여기에 자신의 사고에 대한 반성을 입력합니다. 오류나 개선점을 확인합니다.] 
# </reflection> 
# [reflexion에 따른 조정을 수행합니다.] 
# </thinking> 
# </output> 
# [여기에 최종적으로 간결한 답변을 입력합니다. 이는 사용자에게 표시되는 유일한 부분입니다.]
#  </output> </output>
 
    # Make the API call
    response = client.chat.completions.create(
        model=model,
        messages=[
            {"role": "system", "content": cot_prompt},
            {"role": "user", "content": initial_query}
        ],
        temperature=0.7,
        max_tokens=4096
    )

    full_response = response.choices[0].message.content
    cot_completion_tokens += response.usage.completion_tokens
    logger.info(f"CoT with Reflection :\n{full_response}")

    # Use regex to extract the content within <thinking> and <output> tags
    thinking_match = re.search(r'<thinking>(.*?)</thinking>', full_response, re.DOTALL)
    output_match = re.search(r'<output>(.*?)(?:</output>|$)', full_response, re.DOTALL)

    thinking = thinking_match.group(1).strip() if thinking_match else "No thinking process provided."
    output = output_match.group(1).strip() if output_match else full_response

    logger.info(f"Final output :\n{output}")

    if return_full_response:
        return full_response, cot_completion_tokens
    else:
        return output, cot_completion_tokens