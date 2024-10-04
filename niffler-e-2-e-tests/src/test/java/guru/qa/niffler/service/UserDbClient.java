package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases.XaConsumer;
import guru.qa.niffler.data.Databases.XaFunction;
import guru.qa.niffler.data.dao.impl.AuthAuthorityDaoJdbc;
import guru.qa.niffler.data.dao.impl.AuthUserDaoJdbc;
import guru.qa.niffler.data.dao.impl.UserdataUserDAOJdbc;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.model.UserJson;

import java.sql.Connection;

import static guru.qa.niffler.data.Databases.xaTransaction;

public class UserDbClient {

    private static final Config CFG = Config.getInstance();

    public UserJson createUser(UserJson user) {
        return xaTransaction(
                Connection.TRANSACTION_SERIALIZABLE,
                new XaFunction<>(connection -> {
                    UserEntity userEntity = UserEntity.fromJson(user);
                    return UserJson.fromEntity(new UserdataUserDAOJdbc(connection).createUser(userEntity)
                    );
                },
                        CFG.userdataJdbcUrl()),
                new XaFunction<>(connection -> {
                    guru.qa.niffler.data.entity.auth.UserEntity userEntity = guru.qa.niffler.data.entity.auth.UserEntity.fromJson(user);
                    return UserJson.fromAuthEntity(new AuthUserDaoJdbc(connection).create(userEntity));
                },
                        CFG.authJdbcUrl()));
    }

    public void deleteUser(UserJson user) {
        xaTransaction(
                Connection.TRANSACTION_SERIALIZABLE,
                new XaConsumer(connection -> {
                    UserEntity userEntity = UserEntity.fromJson(user);
                    new UserdataUserDAOJdbc(connection).delete(userEntity);
                }, CFG.userdataJdbcUrl()),
                new XaConsumer(connection -> {
                    guru.qa.niffler.data.entity.auth.UserEntity userEntity = guru.qa.niffler.data.entity.auth.UserEntity.fromJson(user);
                    if (!userEntity.getAuthorities().isEmpty())
                        for (AuthorityEntity authority : userEntity.getAuthorities()) {
                            new AuthAuthorityDaoJdbc(connection).delete(authority);
                        }
                }, CFG.authJdbcUrl()),
                new XaConsumer(connection -> {
                    guru.qa.niffler.data.entity.auth.UserEntity userEntity = guru.qa.niffler.data.entity.auth.UserEntity.fromJson(user);
                    new AuthUserDaoJdbc(connection).delete(userEntity);
                }, CFG.authJdbcUrl()));
    }
}
