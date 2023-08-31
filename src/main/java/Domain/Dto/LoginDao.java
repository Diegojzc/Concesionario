package Domain.Dto;

import Domain.User;

import java.sql.*;

public class LoginDao {

    private Connection connection;

    public LoginDao() throws SQLException {
        connection();
    }

    private void connection() throws SQLException {
        String url = "jdbc:sqlite:login.db";
        connection = DriverManager.getConnection(url);
    }
    private void desconection() throws SQLException {
        connection.close();
    }

    public void add(User login) throws SQLException {

        String sql = "INSERT INTO login(user,password,email) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1,login.getUser());
        statement.setString(2, login.getPassword());
        statement.setString(3,login.getCorreo());
        statement.executeUpdate();
    }

    public User loggear(String name, String password) throws SQLException{
        String sql = "SELECT user,password FROM login WHERE user= ? AND password=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setString(2,password);
        ResultSet resultSet = statement.executeQuery();
        return new User(resultSet.getString(1),
                resultSet.getString(2));

    }
    public String mailQuery(String correo) throws SQLException {
        String sql="SELECT email FROM login WHERE email=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,correo);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getString(1);
    }

    public String passwordQuery(String email) throws SQLException {
        String sql= "SELECT password FROM login WHERE email=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getString(1);


    }
    public String nameQuery(String email) throws SQLException {
        String sql= "SELECT user FROM login WHERE email=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getString(1);


    }
}
