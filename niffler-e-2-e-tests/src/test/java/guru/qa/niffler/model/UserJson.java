package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("surname")
        String surname,
        @JsonProperty("fullname")
        String fullname,
        @JsonProperty("password")
        String password,
        @JsonProperty("currency")
        CurrencyValues currency,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("photoSmall")
        String photoSmall,
        @JsonProperty("friendState")
        FriendState friendState,
        List<AuthorityEntity> authorities) implements IUserJson {
    public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity, @Nullable FriendState friendState) {
        return new UserJson(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstname(),
                entity.getSurname(),
                entity.getFullname(),
                null,
                entity.getCurrency(),
                entity.getPhoto() != null && entity.getPhoto().length > 0 ? new String(entity.getPhoto(), StandardCharsets.UTF_8) : null,
                entity.getPhotoSmall() != null && entity.getPhotoSmall().length > 0 ? new String(entity.getPhotoSmall(), StandardCharsets.UTF_8) : null,
                friendState,
                null
        );
    }

    public static @Nonnull UserJson fromEntity(@Nonnull UserEntity entity) {
        return fromEntity(entity, null);
    }

    public static @Nonnull UserJson fromAuthEntity(@Nonnull guru.qa.niffler.data.entity.auth.UserEntity entity) {
        return new UserJson(
                entity.getId(),
                entity.getUsername(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                entity.getAuthorities());
    }
}
