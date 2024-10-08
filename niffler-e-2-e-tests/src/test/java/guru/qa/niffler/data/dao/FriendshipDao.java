package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;

import java.util.List;

public interface FriendshipDao {
    void create(UserEntity requester, UserEntity addressee, FriendshipStatus status);

    void delete(UserEntity requester, UserEntity addressee);

    List<FriendshipEntity> findAll();
}
