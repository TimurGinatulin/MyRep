package sample.database;

import java.sql.*;

public class DataBaseHandler extends Config {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FN + "," + Const.USER_LN +
                "," + Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," + Const.USER_LOCATION + "," +
                Const.USER_GENDER+")VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prSt=getDbConnection().prepareStatement(insert);
            prSt.setString(1,user.getFirstName());
            prSt.setString(2,user.getLastName());
            prSt.setString(3,user.getLogin());
            prSt.setString(4,user.getPassword());
            prSt.setString(5,user.getLocation());
            prSt.setString(6,user.getGender());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAdmin(Admin admin) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.ADMIN_TABLE_ADMIN + " WHERE " +
                Const.ADMIN_LOGIN + "=? AND " + Const.ADMIN_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, admin.getLogin());
            preparedStatement.setString(2, admin.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
