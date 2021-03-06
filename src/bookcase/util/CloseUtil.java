package bookcase.util;

import java.sql.*;

public class CloseUtil {
    public static void close(Statement statement){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connecton){
        if (connecton != null){
            try {
            	connecton.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
