package guru.qa.niffler.data.mapper;

import guru.qa.niffler.data.entity.userdata.FriendshipEntity;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipEntityRowMapper implements RowMapper<FriendshipEntity> {

    public static final FriendshipEntityRowMapper instance = new FriendshipEntityRowMapper();

    private FriendshipEntityRowMapper() {
    }

    @Override
    public FriendshipEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendshipEntity result = new FriendshipEntity();
/*    result.setRequester(rs.getString("requester_id"));
    result.setAddressee(rs.getString("password"));*/
        result.setStatus(FriendshipStatus.valueOf("status"));
        result.setCreatedDate(rs.getDate("created_date"));
        return result;
    }
}
