package carsharing.dbclient;

import carsharing.cars.Car;
import carsharing.companies.Company;
import carsharing.customers.Customer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbClient {
    private static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    public void run(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Company> selectCompanies(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)) {

            connection.setAutoCommit(true);

            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("ID");
                String name = resultSetItem.getString("NAME");
                Company company = new Company(id, name);
                companies.add(company);
            }

            return companies;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return companies;
    }

    public List<Car> selectCars(String query) {
        List<Car> cars = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)) {

            connection.setAutoCommit(true);

            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("ID");
                String name = resultSetItem.getString("NAME");
                int companyId = resultSetItem.getInt("COMPANY_ID");
                Car car = new Car(id, name, companyId);
                cars.add(car);
            }

            return cars;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cars;
    }

    public List<Customer> selectCustomers(String query) {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)) {

            connection.setAutoCommit(true);

            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("ID");
                String name = resultSetItem.getString("NAME");
                int rentedCarId = resultSetItem.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, name, rentedCarId);
                customers.add(customer);
            }

            return customers;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

}
