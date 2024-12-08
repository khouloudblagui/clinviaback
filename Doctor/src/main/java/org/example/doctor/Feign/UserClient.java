package org.example.doctor.Feign;


import org.example.doctor.DTOs.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface UserClient {

    @GetMapping("/api/v1/auth/validate-token/{token}")
    Boolean validateToken(@PathVariable("token") String token);
    @GetMapping("/users/{userId}")
    UserResponseDTO getUserByKy(@PathVariable("userKy") Integer userKy);
}



