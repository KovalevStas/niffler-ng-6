package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.UserEntity;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserDao {
    UserEntity create(UserEntity user);

  Optional<AuthUserEntity> findById(UUID id);

    void delete(UserEntity user);
}