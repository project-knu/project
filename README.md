event detection (video)
==========================
- action recognition 
https://paperswithcode.com/task/action-recognition-in-videos
- object detection
https://paperswithcode.com/task/object-detection

VLM - image frame captioning (derived from video)
==========================
-MoA에 대한 성능 비교
![image](https://github.com/user-attachments/assets/5db4180f-9f55-42a3-9c37-47fea0a6f0fc)

![image](https://github.com/user-attachments/assets/593d2c6d-e47d-4323-9463-841923ff625d)

('The image features a Motorola smartphone with a sliding QWERTY keyboard, positioned on a textured surface. Here are the key details:\n\n### Key Features:\n- **Screen**: The smartphone has a large touchscreen display that shows a web page, indicating its capability for internet browsing.\n- **Keyboard**: Below the screen, the device includes a full QWERTY keyboard that slides out for easy typing, combining traditional keyboard functionality with modern touchscreen technology.\n- **Branding**: The Motorola logo is prominently displayed on the device, along with the Verizon logo, suggesting it is designed for use on the Verizon wireless network.\n- **Color and Design**: The phone appears predominantly black, giving it a sleek and modern look, typical of smartphones from its era.\n\nThis design reflects the blend of touch and physical typing capabilities, characteristic of older smartphone models, making it a notable device in the evolution of mobile technology.',

The image shows a Motorola smartphone with a slide-out QWERTY keyboard. The device has a large touchscreen display on the top half, which is currently showing a web page. The keyboard below features standard keys, including letters, numbers, and special characters, along with a trackpad on the right side. The phone is branded with the Motorola logo on the top and the Verizon logo on the side. The background appears to be a textured surface, possibly a table or desk.



- InternVL2-8B
- ![image](https://github.com/user-attachments/assets/7cbbcf1c-5463-4ca0-92e3-ef7492ba8206)

- MiniCPM-V 2.6
- ![image](https://github.com/user-attachments/assets/175b4fd2-5ff9-4746-982d-d729fecb2071)

- Qwen2-VL-7B
- ![image](https://github.com/user-attachments/assets/e4be103f-e5c9-4476-826d-aa0c2f226657)

- PaliGemma-3B
- ![image](https://github.com/user-attachments/assets/3a6f6cfa-1c51-4f61-b775-8caf1bdd3b0b)

3개의 사진 CAPTIONING 비교
-----------------
![image](https://github.com/user-attachments/assets/41caa1cc-9ae8-42c1-b199-cbe9e4b2aeb2)

프롬프트 : 이미지에 대해 설명해줘

Qwem2-VL-7b
- 이 이미지에는 한 남성이 거울 앞에서 사진을 찍고 있는 모습이 있습니다. 남성은 검은색 카메라를 들고 있으며, 그의 옆에 있는 거울에는 그의 반사가 보입니다. 거울 위에는 화분에 심어진 붉은 꽃이 있습니다. 화분은 흰색의 식탁 위에 있으며, 그 옆에는 흰색의 수면대가 있습니다. 배경에는 벽돌 벽과 나무 문이 보입니다.

InternVL2-8B
- 이 사진에는 방안 주위의 시장을 타고리를 걷다가 일부 구조자들이 도로를 통하고 있는 장면이 보이게 되어 있습니다. 

InternVL2-Pro
- 이 이미지는 화장실에서 찍은 거울 사진입니다. 사진 속에는 한 남성이 카메라를 들고 자신을 촬영하고 있습니다. 그는 검은색 재킷과 체크무늬의 셔츠를 입고 있으며, 검은색 바지를 신고 있습니다.거울 앞에는 싱크대가 있고, 싱크대 위에는 두 개의 붉은 꽃이 담긴 화병이 놓여 있습니다. 싱크대와 벽은 모두 흰색 타일로 마감되어 있으며, 거울 오른쪽에는 전기 플러그 소켓이 보입니다. 거울을 통해 볼 수 있는 화장실 문은 나무로 만들어져 있습니다.

PaliGemma-3B
- 화장실 거울 앞에 앉아 있는 꽃.

![NIA_dataset03_000000001519](https://github.com/user-attachments/assets/d757551f-24d6-401d-aa5a-b7ab55cfac82)

프롬프트 : 이미지에 대해 설명해줘

Qwem2-VL-7b
- 이 이미지에는 주로 주방이나 작은 업무 공간의 모습을 보여줍니다. \n\n- 왼쪽에는 냉장고가 있고, 그 위에 라디오가 놓여져 있습니다

![NIA_dataset02_000000050042](https://github.com/user-attachments/assets/64de671a-1e4b-439c-b379-ed0c4fd26764)

프롬프트 : 이미지에 대해 설명해줘

Qwem2-VL-7b
이 이미지는 두 개의 동일한 사진을 보여줍니다. 두 사진 모두 테니스 코트에서 두 명의 선수가 있는 장면을 보여줍니다. 선수들은 모두 오렌지색의 티셔츠와 검은색의 반바지를 입고 있으며, 그 중 한 명은 라켓을 들고 있습니다. 코트는 파란색이며, 배경에는 "Rice"라는 글자가 적혀 있습니다. 코트 위에는 테니스 네트가 설치되어 있으며, 코트 위에 떨

LLM - summarization (generated caption to report)
====================
![image](https://github.com/user-attachments/assets/1178d8db-a655-429e-bd6a-a77ed87388a4)
