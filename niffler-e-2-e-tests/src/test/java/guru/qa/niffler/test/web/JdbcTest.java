package guru.qa.niffler.test.web;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UsersDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
                                "cat-name-tx-2",
                                "duck",
                                false
                        ),
                        CurrencyValues.RUB,
                        1000.0,
                        "spend-name-tx",
                        "duck"
                )
        );
        System.out.println(spend);
        spendDbClient.deleteSpend(spend);
        spendDbClient.deleteCategory(spend.category());
    }

    @Test
    void springJdbcTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserJson user = usersDbClient.createUser(
                new UserJson(
                        null,
                        "valentin-9",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        usersDbClient.deleteUser(user);
    }

    @Test
    void chainedCreateTest() {
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
        usersDbClient.deleteUser(user);
    }

    @Test
    void name() {
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.deleteUser(new UserJson(
                null,
                "valentin-7",
                null,
                null,
                null,
                CurrencyValues.RUB,
                null,
                null,
                null
        ));

    }
}
