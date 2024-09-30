package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.AuthAuthorityDao;
import guru.qa.niffler.data.entity.auth.Authority;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthAuthorityDaoJdbc implements AuthAuthorityDao {

    private static final Config CFG = Config.getInstance();
    private final Connection connection;

    public AuthAuthorityDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(AuthorityEntity... authority) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO \"authority\" (user_id, authority) VALUES (?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (AuthorityEntity a : authority) {
                ps.setObject(1, a.getUserId());
                ps.setString(2, a.getAuthority().name());
                ps.addBatch();
                ps.clearParameters();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AuthorityEntity> findAll(String user_id) {
        try (Connection connection = Databases.connection(CFG.authJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM \"authority\" WHERE user_id = ?"
            )) {
                ps.setObject(1, user_id);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    List<AuthorityEntity> authorytyList = new ArrayList<>();
                    while (rs.next()) {
                        AuthorityEntity result = new AuthorityEntity();
                        result.setId(rs.getObject("id", UUID.class));
                        result.setUserId(UUID.fromString(rs.getString("user_id")));
                        result.setAuthority(Authority.valueOf(rs.getString("authority")));
                        authorytyList.add(result);
                    }
                    return authorytyList;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
