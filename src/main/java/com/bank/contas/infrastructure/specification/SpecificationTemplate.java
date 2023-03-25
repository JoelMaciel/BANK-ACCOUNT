package com.bank.contas.infrastructure.specification;

import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.AgencyModel;
import com.bank.contas.domain.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "name", spec = Like.class),
            @Spec(path = "number", spec = Like.class)
    })
    public  interface  AgencySpec extends  Specification<AgencyModel> {}

    @And({
            @Spec(path = "number", spec = Like.class),
            @Spec(path = "type", spec = Equal.class)

    })
    public interface AccountSpec extends Specification<AccountModel> {}


    public static Specification<AccountModel> accountUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<AccountModel> account = root;
            Root<UserModel> user = query.from(UserModel.class);
            Expression<Collection<AccountModel>> accountsUsers = user.get("accounts");
            return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(account, accountsUsers));
        };
    }
}
