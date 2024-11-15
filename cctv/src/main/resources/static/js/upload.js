let presignedUrl = '';
let selectedFile = null;
function getURL(e){
    selectedFile = e.files[0]; // 선택된 파일 저장

    let name = encodeURIComponent(selectedFile.name); // 선택된 파일 이름 가져오고

    //presigned url 가져오기
    fetch('/presigned-url?filename=' + encodeURIComponent(name))
        .then(response => {
            if (response.ok) {return response.text();// 성공적으로 presigned URL을 받으면 텍스트로 변환
            } else if (response.status === 409) {// 파일이 이미 존재하는 경우
                return response.text().then(errorMessage => {
                    alert(errorMessage);  // 사용자에게 알림
                    throw new Error(errorMessage);  // 흐름 중단
                });
            } else {
                throw new Error('알 수 없는 오류가 발생했습니다.');
            }
        })
        .then(url => {
            // Presigned URL을 성공적으로 받음
            presignedUrl = url;
            console.log('Presigned URL:', presignedUrl);
        })
        .catch(error => console.error('Error fetching presigned URL:', error));

}

function uploadFile() {

    if (!presignedUrl || !selectedFile) {
        alert('파일을 먼저 선택해주세요');
        return;
    }

    /* //동영상만 올릴 수 있게 일단은 주석처리해서 나중에 살리자
    if (!selectedFile.type.startsWith('video/')) {
        alert('동영상 파일만 업로드할 수 있습니다.');
        selectedFile = null;
        return;
    }
    */

    // 파일을 Presigned URL(S3)로 업로드
    fetch(presignedUrl, {
        method: 'PUT',  // PUT 메서드 사용
        body: selectedFile,  // 선택된 파일 데이터를 전송
        headers: {
            'Content-Type': selectedFile.type  // 파일의 어떤 확장자를 가지고 있는지 설정
        }
    })
        .then(response => {
            if (response.ok) {
                file_send_sever();
                presignedUrl = '';
            } else {
                alert('파일 업로드 실패!');
            }
        })
        .catch(error => console.error('Error uploading file:', error));


}

// 보내야 하는거 : 파일 이름, 파일 url
function file_send_sever() {
    fetch('/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            url : presignedUrl.split("?")[0],
            file_name : selectedFile.name
        })
    })
        .then(response => {
            if (response.ok) {
                alert('전송 성공!');

            } else {
                alert('전송 실패!');
                deleteS3File()
            }
        })
        .catch(error => console.error('서버 메타데이터 전송 에러:', error));
}


function deleteS3File() {//s3에 데이터 삭제하기
    const deleteUrl = '/delete-s3-file?url=' + encodeURIComponent(presignedUrl.split("?")[0]);

    fetch(deleteUrl, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                console.log('S3 파일 삭제 성공');
            } else {
                console.error('S3 파일 삭제 실패');
            }
        })
        .catch(error => console.error('S3 파일 삭제 에러:', error));
}
