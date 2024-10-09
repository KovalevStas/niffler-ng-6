package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserdatabaseRepository {

    UserEntity create(UserEntity user);

    Optional<UserEntity> findById(UUID id);

    void createFriendship(UserEntity requester, UserEntity addressee, FriendshipStatus status);

    List<FriendshipEntity> findAll();

    void delete(UserEntity user);
}
