package guru.qa.niffler.model;

import java.util.UUID;

public interface IUserJson {
    UUID id();

    String username();

    String firstname();

    String surname();

    CurrencyValues currency();

    String photo();

    String photoSmall();

    FriendState friendState();
}
