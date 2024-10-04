package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.auth.Authority;
import guru.qa.niffler.data.entity.auth.AuthorityEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthAuthorityRowMapper implements RowMapper<AuthorityEntity> {
    public static final AuthAuthorityRowMapper instance = new AuthAuthorityRowMapper();

    private AuthAuthorityRowMapper() {
    }

    @Override
    public AuthorityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthorityEntity result = new AuthorityEntity();
        result.setId(rs.getObject("id", UUID.class));
        result.setUserId(UUID.fromString(rs.getString("user_id")));
        result.setAuthority(Authority.valueOf(rs.getString("authority")));
        return result;
    }
}
