package jdbc.dao;
import jdbc.MyException;
import jdbc.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDao {
    protected final Connection connection;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    protected int findMaxId(Table table) {
        Integer maxIdOfCustomer = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT max("+String.valueOf(table).toLowerCase()
                    +".id) as maxId FROM "+ String.valueOf(table));
            if (rs.next()) {
                maxIdOfCustomer = rs.getInt("maxId");
            }

            return maxIdOfCustomer;
        } catch (SQLException e) {
            throw new MyException(e,"something went wrong");
        }
    }
}
