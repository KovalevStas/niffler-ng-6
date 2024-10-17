package guru.qa.niffler.test.web;

import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.data.repository.impl.UserdatabaseRepositoryJdbc;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UsersDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcTest {

    @Test
    void txTest() {
        SpendDbClient spendDbClient = new SpendDbClient();

        SpendJson spend = spendDbClient.createSpend(
                new SpendJson(
                        null,
                        new Date(),
                        new CategoryJson(
                                null,
                                "cat-name-tx-3",
                                "duck",
                                false
                        ),
                        CurrencyValues.RUB,
                        1000.0,
                        "spend-name-tx-3",
                        "duck"
                )
        );

        System.out.println(spend);
    }

    @Test
    void springJdbcTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserJson user = usersDbClient.createUser(
                new UserJson(
                        null,
                        "valentin-11",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }

    @Test
    void sendInviteToFriendTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserEntity req = new UserEntity();
        req.setUsername("valentin-8");
        req.setId(UUID.fromString("717cc428-824d-11ef-b146-0242ac110002"));
        UserEntity addr = new UserEntity();
        addr.setId(UUID.fromString("750f19e6-8249-11ef-bf02-0242ac110002"));
        addr.setUsername("valentin-4");
        usersDbClient.sendInviteToFriend(addr, req, FriendshipStatus.PENDING);
        System.out.println("111");
    }

    @Test
    void addFriendTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserEntity req = new UserEntity();
        req.setUsername("paul");
        req.setId(UUID.fromString("d0c40858-3982-4613-aadf-5df40c8d79e8"));
        UserEntity addr = new UserEntity();
        addr.setId(UUID.fromString("4405500a-7601-420d-aa24-2f3fb62c1f5d"));
        addr.setUsername("barsik");
        usersDbClient.addToFriend(addr, req);
        System.out.println("111");
    }

    @Test
    void findByIdUser() {
        UserdatabaseRepositoryJdbc udbc = new UserdatabaseRepositoryJdbc();
        Optional<UserEntity> user = udbc.findById(UUID.fromString("4405500a-7601-420d-aa24-2f3fb62c1f5d"));
        System.out.println("");
    }

    @Test
    void createFriendshipTest() {
        UserdatabaseRepositoryJdbc udbc = new UserdatabaseRepositoryJdbc();
        UserEntity req = new UserEntity();
        req.setUsername("barsik");
        req.setId(UUID.fromString("4405500a-7601-420d-aa24-2f3fb62c1f5d"));
        UserEntity addr = new UserEntity();
        addr.setId(UUID.fromString("81b2667e-7525-4bd6-b968-be2c22335955"));
        addr.setUsername("stas");
        udbc.createFriendship(req, addr, FriendshipStatus.ACCEPTED);
        System.out.println("111");
    }

    @Test
    void findAllFriendship() {
        UserdatabaseRepositoryJdbc udbc = new UserdatabaseRepositoryJdbc();
        List<FriendshipEntity> ls = udbc.findAll();
        System.out.println("");
    }
}
