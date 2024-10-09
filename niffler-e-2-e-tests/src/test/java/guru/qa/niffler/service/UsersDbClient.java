package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.FriendshipDao;
import guru.qa.niffler.data.dao.impl.FriendshipDaoSpringJdbc;
import guru.qa.niffler.data.entity.auth.AuthUserEntity;
import guru.qa.niffler.data.entity.auth.Authority;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.data.repository.AuthUserRepository;
import guru.qa.niffler.data.repository.UserdatabaseRepository;
import guru.qa.niffler.data.repository.impl.AuthUserRepositoryJdbc;
import guru.qa.niffler.data.repository.impl.UserdatabaseRepositoryJdbc;
import guru.qa.niffler.data.tpl.DataSources;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.data.tpl.XaTransactionTemplate;
import guru.qa.niffler.model.UserJson;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;


public class UsersDbClient {

    private static final Config CFG = Config.getInstance();
    private static final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final AuthUserRepository authUserRepository = new AuthUserRepositoryJdbc();
    private final UserdatabaseRepository userdatabaseRepository = new UserdatabaseRepositoryJdbc();
    private final FriendshipDao friendshipDao = new FriendshipDaoSpringJdbc();

    private final TransactionTemplate txTemplate = new TransactionTemplate(
            new JdbcTransactionManager(
                    DataSources.dataSource(CFG.authJdbcUrl())
            )
    );
    private final JdbcTransactionTemplate txTemplateUd = new JdbcTransactionTemplate(
            CFG.userdataJdbcUrl()
    );

    private final XaTransactionTemplate xaTransactionTemplate = new XaTransactionTemplate(
            CFG.authJdbcUrl(),
            CFG.userdataJdbcUrl()
    );

    public UserJson createUser(UserJson user) {
        return xaTransactionTemplate.execute(() -> {
                    AuthUserEntity authUser = new AuthUserEntity();
                    authUser.setUsername(user.username());
                    authUser.setPassword(pe.encode("12345"));
                    authUser.setEnabled(true);
                    authUser.setAccountNonExpired(true);
                    authUser.setAccountNonLocked(true);
                    authUser.setCredentialsNonExpired(true);
                    authUser.setAuthorities(
                            Arrays.stream(Authority.values()).map(
                                    e -> {
                                        AuthorityEntity ae = new AuthorityEntity();
                                        ae.setUser(authUser);
                                        ae.setAuthority(e);
                                        return ae;
                                    }
                            ).toList()
                    );
                    authUserRepository.create(authUser);
                    return UserJson.fromEntity(
                            userdatabaseRepository.create(UserEntity.fromJson(user)),
                            null
                    );
                }
        );
    }

    public void sendInviteToFriend(UserEntity requester, UserEntity addressee, FriendshipStatus status) {
        friendshipDao.create(requester, addressee, status);
    }

    public boolean addToFriend(UserEntity user1, UserEntity user2) {
        return xaTransactionTemplate.execute(() -> {
            friendshipDao.create(user1, user2, FriendshipStatus.ACCEPTED);
            friendshipDao.create(user2, user1, FriendshipStatus.ACCEPTED);
            return true;
        });
    }

/*    public void deleteUser(UserJson user) {
        UserEntity userEntity = UserEntity.fromJson(user);
        Optional<AuthUserEntity> authUserEntity = authUserDao.findByUsername(userEntity.getUsername());
        xaTransactionTemplate.execute(() -> {
            if (authUserEntity.isPresent()) {
                List<AuthorityEntity> authorityList = authAuthorityDao.findAll(String.valueOf(authUserEntity.get().getId()));
                if (!authorityList.isEmpty())
                    for (AuthorityEntity authority : authorityList) {
                        authAuthorityDao.delete(authority);
                    }
                authUserDao.delete(authUserEntity.get());
            }
            userEntity.setUsername(null);
            udUserDao.delete(userEntity);
            return null;
        });
    }*/
}
