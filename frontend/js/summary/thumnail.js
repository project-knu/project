// summary.js
async function getImage(imageUrl) {
    try {
        const response = await fetch(imageUrl, {
            method: 'GET',
            headers: {
                'Accept': 'image/*'
            }
        });
        
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const imageBlob = await response.blob();
        const imageObjectURL = URL.createObjectURL(imageBlob);
        
        // 이미지 엘리먼트에 설정
        const img = document.querySelector('.logo-img'); 
        img.src = imageObjectURL;
        
    } catch (error) {
        console.error('Error:', error);
    }
}
// document.querySelector('.video').innerHTML = <img src="assets/video/${date}.png" class = "video">;

// 페이지 로드 시 이미지 가져오기
document.addEventListener('DOMContentLoaded', function() {
    getImage('이미지_URL을_여기에_넣으세요');
});

{/* <script>
        // 1. 파일 업로드로 이미지 로드하기
        document.getElementById('imageInput').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    displayImage(e.target.result);
                };
                reader.readAsDataURL(file);
            }
        });

        // 2. URL로 이미지 로드하기
        function loadImageFromUrl() {
            const url = document.getElementById('imageUrl').value;
            if (url) {
                displayImage(url);
            }
        }

        // 이미지 표시 함수
        function displayImage(src) {
            const container = document.getElementById('imageContainer');
            container.innerHTML = ''; // 기존 이미지 제거
            
            const img = new Image();
            img.src = src;
            img.style.maxWidth = '100%';
            img.style.height = 'auto';
            
            // 이미지 로드 성공
            img.onload = function() {
                container.appendChild(img);
            };
            
            // 이미지 로드 실패
            img.onerror = function() {
                container.innerHTML = '이미지를 로드할 수 없습니다.';
            };
        }
    </script> */}
    document.addEventListener('DOMContentLoaded', function() {
        const videoPlayer = document.getElementById('videoPlayer');
        const loadingMessage = document.getElementById('loadingMessage');
        const errorMessage = document.getElementById('errorMessage');

        async function getVideo() {
            try {
                const videoUrl = 'http://your-server-url/video-endpoint';
                
                const response = await fetch(videoUrl, {
                    method: 'GET',
                    headers: {
                        'Accept': 'video/*'
                    }
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                const videoBlob = await response.blob();
                const videoObjectURL = URL.createObjectURL(videoBlob);
                
                videoPlayer.src = videoObjectURL;
                loadingMessage.style.display = 'none';
                
                videoPlayer.onloadeddata = () => {
                    URL.revokeObjectURL(videoObjectURL);
                };

            } catch (error) {
                console.error('Error:', error);
                errorMessage.textContent = '비디오를 불러오는데 실패했습니다.';
                errorMessage.style.display = 'block';
                loadingMessage.style.display = 'none';
            }
        }

        getVideo();
    });
