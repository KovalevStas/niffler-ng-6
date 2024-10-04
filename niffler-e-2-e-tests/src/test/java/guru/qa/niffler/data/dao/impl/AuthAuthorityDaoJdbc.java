package guru.qa.niffler.data.dao.impl;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.AuthAuthorityDao;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static guru.qa.niffler.data.tpl.Connections.holder;
import java.sql.*;
import java.util.UUID;

public class AuthAuthorityDaoJdbc implements AuthAuthorityDao {

  private static final Config CFG = Config.getInstance();
    private final Connection connection;

    public AuthAuthorityDaoJdbc(Connection connection) {
        this.connection = connection;
    }

  @Override
  public void create(AuthorityEntity... authority) {
    try (PreparedStatement ps = holder(CFG.authJdbcUrl()).connection().prepareStatement(
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
    public void delete(AuthorityEntity authority) {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM authority WHERE id = ?"
        )) {
            ps.setObject(1, authority.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}