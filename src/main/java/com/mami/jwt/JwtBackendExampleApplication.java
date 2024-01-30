package com.mami.jwt;

import com.mami.jwt.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtBackendExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtBackendExampleApplication.class, args);
    }
}


/*  // Assuming you have a JWT token
        String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XG4gIFwidXNlck5hbWVcIjogXCJBbGV4XCIsXG4gIFwidXNlclBhc3N3b3JkXCI6IFwicGFzc3dvcmRcIlxufSIsImlhdCI6MTcwNjM3NTg1NywiZXhwIjoxNzA2MzkzODU3fQ.YmtVcycOywxafymiL2raLcVxFn_mt6U1YgEyKm1sa4gQqeQDpd3WvcKj5tHvJBf-qbmyKek2GCJujxHVqp4dwA";
        // Create an instance of JwtUtil
        JwtUtil jwtUtil = new JwtUtil();

        // Call the getUsernameFromToken method
        String username = jwtUtil.getUsernameFromToken(jwtToken);

        // Use the 'username' as needed
        System.out.println("Username from token: " + username);
*/



/* String payload = "{\n" +
                "  \"userName\": \"Alex\",\n" +
                "  \"userPassword\": \"password\"\n" +
                "}";

        JwtUtil jwtUtil = new JwtUtil();
        final String jwtToken = jwtUtil.generateToken(payload);
        System.out.println(jwtToken);
*/