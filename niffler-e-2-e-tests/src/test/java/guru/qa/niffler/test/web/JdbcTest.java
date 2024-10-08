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
    void createSpendTest() {
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
    void createCategoryTest() {
        SpendDbClient spendDbClient = new SpendDbClient();
        CategoryJson category = spendDbClient.createCategory(new CategoryJson(null, "new category", "duck", false));
        spendDbClient.deleteCategory(category);
    }

    @Test
    void jdbcTest() {
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
    void springJdbcTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserJson user = usersDbClient.createUserSpring(
                new UserJson(
                        null,
                        "valentin-15",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        usersDbClient.deleteUserSpring(user);
    }

    @Test
    void chainedCreateTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        UserJson user = usersDbClient.chainedCreateUser(
                new UserJson(
                        null,
                        "valentin-12",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void deleteUserTest() {
        UsersDbClient usersDbClient = new UsersDbClient();
        usersDbClient.deleteUser(new UserJson(
                null,
                "valentin-11",
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
