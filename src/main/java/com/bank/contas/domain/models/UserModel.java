package com.bank.contas.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @CPF
    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String userType;

    @Column(nullable = false)
    private String status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    private Set<AccountModel> accounts = new HashSet<>();





}
