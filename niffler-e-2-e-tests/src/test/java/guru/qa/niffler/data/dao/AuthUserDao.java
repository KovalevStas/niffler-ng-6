package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.entity.auth.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthUserDao {

  AuthUserEntity create(UserEntity user);

  Optional<AuthUserEntity> findById(UUID id);

    List<AuthUserEntity> findAll(String username);

    void delete(UserEntity user);
}
