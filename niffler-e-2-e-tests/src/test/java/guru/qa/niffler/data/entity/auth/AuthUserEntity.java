package guru.qa.niffler.data.entity.auth;

import guru.qa.niffler.model.UserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AuthUserEntity implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;

    public static AuthUserEntity fromJson(UserJson json) {
        AuthUserEntity ue = new AuthUserEntity();
        ue.id = json.id();
        ue.username = json.username();
        ue.password = null;
        ue.accountNonExpired = true;
        ue.enabled = true;
        ue.accountNonLocked = true;
        ue.credentialsNonExpired = true;
        return ue;
    }
}