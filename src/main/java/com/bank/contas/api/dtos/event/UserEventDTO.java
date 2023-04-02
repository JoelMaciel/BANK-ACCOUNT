package com.bank.contas.api.dtos.event;

import com.bank.contas.domain.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventDTO {

    @Id
    private UUID userId;
    private String username;
    private String fullName;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String userType;
    private String status;
    private String actionType;

    public static UserModel toEntity(UserEventDTO userEventDTO) {
        return UserModel.builder()
                .userId(userEventDTO.getUserId())
                .username(userEventDTO.getUsername())
                .fullName(userEventDTO.getFullName())
                .cpf(userEventDTO.getCpf())
                .email(userEventDTO.getEmail())
                .phoneNumber(userEventDTO.getPhoneNumber())
                .userType(userEventDTO.getUserType())
                .status(userEventDTO.getStatus())
                .build();
    }

}
