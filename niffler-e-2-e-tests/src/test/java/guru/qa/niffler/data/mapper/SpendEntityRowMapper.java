package guru.qa.niffler.data.mapper;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.CategoryDaoSpringJdbc;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static guru.qa.niffler.data.Databases.dataSource;

public class SpendEntityRowMapper implements RowMapper<SpendEntity> {
    public static final SpendEntityRowMapper instance = new SpendEntityRowMapper();
    private static final Config CFG = Config.getInstance();

    private SpendEntityRowMapper() {
    }

    @Override
    public SpendEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpendEntity result = new SpendEntity();
        result.setId(rs.getObject("id", UUID.class));
        result.setUsername(rs.getString("username"));
        result.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        result.setCategory(new CategoryDaoSpringJdbc(dataSource(CFG.spendJdbcUrl())).findCategoryById((UUID) rs.getObject("category_id")).get());
        result.setAmount(rs.getDouble("amount"));
        result.setDescription(rs.getString("description"));
        result.setSpendDate(rs.getDate("spend_date"));
        return result;
    }
}