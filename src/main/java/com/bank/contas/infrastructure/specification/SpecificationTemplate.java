package com.bank.contas.infrastructure.specification;

import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "number", spec = Like.class),
            @Spec(path = "type", spec = Equal.class)

    })
    public interface AccountSpec extends Specification<Account> {}

    @And({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "number", spec = Like.class)
    })
    public  interface  AgencySpec extends  Specification<Agency> {}

}
