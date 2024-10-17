package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.data.mapper.UdUserEntityRowMapper;
import guru.qa.niffler.data.repository.UserdatabaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static guru.qa.niffler.data.tpl.Connections.holder;

public class UserdatabaseRepositoryJdbc implements UserdatabaseRepository {

    private static final Config CFG = Config.getInstance();

    @Override
    public UserEntity create(UserEntity user) {
        try (PreparedStatement userPs = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "INSERT INTO \"user\" (username, currency, firstname, surname, photo, photo_small, full_name)" +
                        "VALUES (?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement friendshipPs = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                     "INSERT INTO \"friendship\" (requester_id, addressee_id, status) VALUES (? , ?, ?)")) {
            userPs.setString(1, user.getUsername());
            userPs.setString(2, user.getCurrency().name());
            userPs.setString(3, user.getFirstname());
            userPs.setString(4, user.getSurname());
            userPs.setBytes(5, user.getPhoto());
            userPs.setBytes(6, user.getPhotoSmall());
            userPs.setString(7, user.getFullname());
            userPs.executeUpdate();

            final UUID generatedKey;
            try (ResultSet rs = userPs.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getObject("id", UUID.class);
                } else {
                    throw new SQLException("Can`t find id in ResultSet");
                }
            }
            user.setId(generatedKey);

            List<FriendshipEntity> friendshipEntities = user.getFriendshipRequests();
            friendshipEntities.addAll(user.getFriendshipAddressees());
            for (FriendshipEntity a : friendshipEntities) {
                friendshipPs.setObject(1, a.getRequester());
                friendshipPs.setObject(2, a.getAddressee());
                friendshipPs.setString(3, a.getStatus().name());
                friendshipPs.addBatch();
                friendshipPs.clearParameters();
                if ("ACCEPTED".equals(a.getStatus().name())) {
                    friendshipPs.setObject(1, a.getAddressee());
                    friendshipPs.setObject(2, a.getRequester());
                    friendshipPs.setString(3, a.getStatus().name());
                    friendshipPs.addBatch();
                    friendshipPs.clearParameters();
                }
            }
            friendshipPs.executeBatch();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        try (PreparedStatement ps = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "select * from \"user\" u join friendship f " +
                        "on u.id = f.requester_id " +
                        "or u.id = f.addressee_id " +
                        "where u.id = ? and ((f.requester_id = ? and f.status = 'ACCEPTED')" +
                        "or (f.addressee_id = ? and f.status = 'PENDING'))"
        );
             PreparedStatement usr = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                     "select * from \"user\" u where u.id = ?"
             )) {
            ps.setObject(1, id);
            ps.setObject(2, id);
            ps.setObject(3, id);

            ps.execute();

            try (ResultSet rs = ps.getResultSet()) {
                UserEntity user = null;
                List<FriendshipEntity> friendshipRequests = new ArrayList<>();
                List<FriendshipEntity> friendshipAddressees = new ArrayList<>();
                while (rs.next()) {
                    if (user == null) {
                        user = UdUserEntityRowMapper.instance.mapRow(rs, 1);
                    }
                    if ("PENDING".equals(rs.getString("status"))) {
                        usr.setObject(1, UUID.fromString(rs.getString("requester_id")));
                        usr.execute();
                        try (ResultSet usr_res = usr.getResultSet()) {
                            UserEntity friend = null;
                            FriendshipEntity ae = new FriendshipEntity();
                            while (usr_res.next()) {
                                if (friend == null) {
                                    friend = UdUserEntityRowMapper.instance.mapRow(usr_res, 1);
                                }
                            }
                            ae.setRequester(friend);
                            ae.setAddressee(user);
                            ae.setStatus(FriendshipStatus.valueOf("PENDING"));
                            ae.setCreatedDate(rs.getDate("created_date"));
                            friendshipAddressees.add(ae);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if ("ACCEPTED".equals(rs.getString("status"))) {
                        usr.setObject(1, UUID.fromString(rs.getString("addressee_id")));
                        usr.execute();
                        try (ResultSet usr_res = usr.getResultSet()) {
                            UserEntity friend = null;
                            FriendshipEntity ae = new FriendshipEntity();
                            while (usr_res.next()) {
                                if (friend == null) {
                                    friend = UdUserEntityRowMapper.instance.mapRow(usr_res, 1);
                                }
                            }
                            ae.setRequester(user);
                            ae.setAddressee(friend);
                            ae.setStatus(FriendshipStatus.valueOf("ACCEPTED"));
                            ae.setCreatedDate(rs.getDate("created_date"));
                            friendshipRequests.add(ae);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (user == null) {
                    return Optional.empty();
                } else {
                    user.setFriendshipAddressees(friendshipAddressees);
                    user.setFriendshipRequests(friendshipRequests);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createFriendship(UserEntity requester, UserEntity addressee, FriendshipStatus status) {
        try (PreparedStatement friendship = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "INSERT INTO \"friendship\" (requester_id, addressee_id, status) VALUES (? , ?, ?)")) {
            friendship.setObject(1, requester.getId());
            friendship.setObject(2, addressee.getId());
            friendship.setString(3, String.valueOf(status));
            friendship.addBatch();
            friendship.clearParameters();
            if ("ACCEPTED".equals(String.valueOf(status))) {
                friendship.setObject(1, addressee.getId());
                friendship.setObject(2, requester.getId());
                friendship.setString(3, String.valueOf(status));
                friendship.addBatch();
                friendship.clearParameters();
            }
            friendship.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FriendshipEntity> findAll() {
        List<FriendshipEntity> friendshipList = new ArrayList<>();
        try (PreparedStatement friendship = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                "SELECT * from \"friendship\"");
             PreparedStatement usr = holder(CFG.userdataJdbcUrl()).connection().prepareStatement(
                     "select * from \"user\" u where u.id = ?"
             )) {
            friendship.execute();
            try (ResultSet rs = friendship.getResultSet()) {
                while (rs.next()) {
                    UserEntity requester = null;
                    UserEntity addressee = null;
                    FriendshipEntity friendshipEntity = new FriendshipEntity();
                    usr.setObject(1, UUID.fromString(rs.getString("requester_id")));
                    usr.execute();
                    try (ResultSet usr_res = usr.getResultSet()) {
                        while (usr_res.next()) {
                            if (requester == null) {
                                requester = UdUserEntityRowMapper.instance.mapRow(usr_res, 1);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    usr.setObject(1, UUID.fromString(rs.getString("addressee_id")));
                    usr.execute();
                    try (ResultSet usr_res = usr.getResultSet()) {
                        while (usr_res.next()) {
                            if (addressee == null) {
                                addressee = UdUserEntityRowMapper.instance.mapRow(usr_res, 1);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    friendshipEntity.setRequester(requester);
                    friendshipEntity.setAddressee(addressee);
                    friendshipEntity.setStatus(FriendshipStatus.valueOf(rs.getString("status")));
                    friendshipEntity.setCreatedDate(rs.getDate("created_date"));
                    friendshipList.add(friendshipEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendshipList;
    }
}
