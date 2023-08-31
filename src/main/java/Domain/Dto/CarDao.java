package Domain.Dto;

import Domain.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {

    private Connection connection;


    public CarDao() throws SQLException {
        connection();
    }

    public void connection() throws SQLException {
        String url = "jdbc:sqlite:tablas.db";
        connection = DriverManager.getConnection(url);
       // System.out.println("conectado con base de datos");

    }
    public void desconnection()throws SQLException{
        connection.close();
    }
    public void add(Car coche)throws SQLException{
        String sql = "INSERT INTO tablas(coches,modelos,kilometros,vender,alquilar) VALUES "+
                " (?,?,?,?,?)";
        PreparedStatement statement= connection.prepareStatement(sql);

        statement.setString(1,coche.getSeeAll());
        statement.setString(2, coche.getModels());
        statement.setInt(3, coche.getKilometrers());
        statement.setDouble(4,coche.getPrice());
        statement.setBoolean(5, coche.isRent());
        statement.executeUpdate();
    }

    public List<Car> seeCars() throws SQLException {
        String sql = "SELECT coches, modelos, kilometros,vender,alquilar FROM tablas";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        List<Car> carList = new ArrayList<>();
        while (resultSet.next()) {
            Car coche = new Car(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getBoolean(5));

            carList.add(coche);
        }
        return carList;
    }

    public List<Car> modelsByBrand(String brand) throws SQLException{
        String sql = "SELECT  coches, modelos, kilometros,vender,alquilar FROM tablas WHERE coches = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,brand);
        ResultSet result = statement.executeQuery();

        return resultList(result);
    }

    

    public List<Car> newCars(Car car) throws SQLException {
        String sql = "SELECT  coches, modelos, kilometros,vender,alquilar FROM tablas WHERE kilometros = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,car.getKilometrers());
        ResultSet resultSet = statement.executeQuery();

        return resultList(resultSet);
    }

public List<Car> isRent(Car car)throws  SQLException{
        String sql = "SELECT coches, modelos, kilometros, vender, alquilar FROM tablas WHERE alquilar = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1,car.isRent());
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultList(resultSet);
}

    public List<Car> resultList(ResultSet resultSet)throws SQLException{
        List<Car> cocheLista = new ArrayList<>();
        while (resultSet.next()) {
            Car coche = new Car(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getBoolean(5));

            cocheLista.add(coche);
        }
        return cocheLista;
    }

    public void buy(String modelos) throws  SQLException{
        String sql = "DELETE FROM tablas WHERE modelos = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,modelos);
        statement.executeUpdate();
    }
    public void rentCar(String modelos) throws  SQLException{
        String sql = "DELETE FROM tablas WHERE modelos = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,modelos);
        statement.executeUpdate();
    }
}
