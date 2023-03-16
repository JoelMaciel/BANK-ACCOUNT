package com.bank.contas.api.models.request;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class AccountDTOUpdate {

    @NotNull
    private String number;

    @NotNull
    private TypeAccount type;

}
