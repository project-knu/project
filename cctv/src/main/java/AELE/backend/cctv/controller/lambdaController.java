package AELE.backend.cctv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class lambdaController {

    private static final String API_KEY = "your-secret-api-key";  // 서버가 기대하는 API 키

    @PostMapping("/metadata")
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, String> payload){
        if (!payload.containsKey("message") || payload.get("message").isEmpty()) {
            return ResponseEntity.badRequest().body("Missing or empty 'message' field");
        }
        System.out.println("Received message: " + payload.get("message"));
        return ResponseEntity.ok("Message received successfully");
    }


}