package guru.qa.niffler.data.entity.auth;

import guru.qa.niffler.model.UserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserEntity implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private List<AuthorityEntity> authorities = new ArrayList<>();

    public static UserEntity fromJson(UserJson json) {
        UserEntity ue = new UserEntity();
        ue.id = json.id();
        ue.username = json.username();
        ue.password = json.password() != null && !json.password().isEmpty() ? json.password() : null;
        ue.accountNonExpired = true;
        ue.enabled = true;
        ue.accountNonLocked = true;
        ue.credentialsNonExpired = true;
        if (json.authorities() != null && !json.authorities().isEmpty()) {
            ue.authorities = json.authorities();
        } else {
            AuthorityEntity readAuthorityEntity = new AuthorityEntity();
            readAuthorityEntity.setAuthority(Authority.read);
            AuthorityEntity writeAuthorityEntity = new AuthorityEntity();
            writeAuthorityEntity.setAuthority(Authority.write);
            ue.authorities = (List.of(readAuthorityEntity, writeAuthorityEntity));
        }
        return ue;
    }
}