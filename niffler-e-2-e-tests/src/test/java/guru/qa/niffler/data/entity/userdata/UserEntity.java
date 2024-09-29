package guru.qa.niffler.data.entity.userdata;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.UserJson;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class UserEntity implements Serializable {
    private UUID id;
    private String username;
    private CurrencyValues currency;
    private String fullname;
    private String firstname;
    private String surname;
    private byte[] photo;
    private byte[] photoSmall;

    public static UserEntity fromJson(UserJson json) {
        UserEntity ue = new UserEntity();
        ue.id = json.id();
        ue.username = json.username();
        ue.currency = json.currency();
        ue.fullname = json.fullname();
        ue.firstname = json.firstname();
        ue.surname = json.surname();
        ue.photo = json.photo() != null && !json.photo().isEmpty() ? json.photo().getBytes() : null;
        ue.photoSmall = json.photoSmall() != null && !json.photoSmall().isEmpty() ? json.photoSmall().getBytes() : null;
        return ue;
    }
}