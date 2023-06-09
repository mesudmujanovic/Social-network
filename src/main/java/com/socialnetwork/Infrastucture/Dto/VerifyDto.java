package com.socialnetwork.Infrastucture.Dto;

import com.socialnetwork.Infrastucture.Request.VerifyRequest;
import com.socialnetwork.Infrastucture.Response.VerifyResponse;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

@Data
public class VerifyDto {

    private Long id;
    private String nameAccount;
    private String lastNameAccount;
    private String age;
    private String job;
    private String phone;


    public static VerifyDto fromRequest(VerifyRequest verifyRequest) {
        VerifyDto verifyDto = new VerifyDto();
        verifyDto.setAge(verifyRequest.getAge());
        verifyDto.setJob(verifyRequest.getJob());
        verifyDto.setNameAccount(verifyRequest.getNameAccount());
        verifyDto.setLastNameAccount(verifyRequest.getLastNameAccount());
        verifyDto.setPhone(verifyRequest.getPhone());
        return verifyDto;
    }

    public VerifyResponse toResponse() {
        VerifyResponse verifyResponse = new VerifyResponse();
        verifyResponse.setId(this.getId());
        verifyResponse.setAge(this.getAge());
        verifyResponse.setJob(this.getJob());
        verifyResponse.setNameAccount(this.getNameAccount());
        verifyResponse.setLastNameAccount(this.getLastNameAccount());
        verifyResponse.setPhone(this.getPhone());
        return verifyResponse;
    }
}
