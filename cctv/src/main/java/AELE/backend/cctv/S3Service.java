package AELE.backend.cctv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Presigner s3Presigner;

    @Autowired
    public S3Service(S3Presigner s3Presigner){
        this.s3Presigner = s3Presigner;
    }

    String createPresignedUrl(String path) { // presigned 발급해주는 함수

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()//S3에 파일을 업로드할 때 사용되는 요청 객체를 빌더 패턴으로 생성
                .bucket(bucket)//어떤 bucket에 넣을지
                .key(path)//어디에 저장될지
                .build();// 객체 완성

        PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder() // Presigned URL을 생성할 때 필요한 요청 객체를 빌더 패턴으로 생성
                .signatureDuration(Duration.ofMinutes(3)) // 유효기간(분단위) 3 //
                .putObjectRequest(putObjectRequest) // 업로드 할 파일 위치, 버킷 이름 지정
                .build();

        return s3Presigner.presignPutObject(preSignRequest).url().toString();
    }

}