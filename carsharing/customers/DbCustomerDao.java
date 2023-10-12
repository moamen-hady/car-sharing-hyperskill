package carsharing.customers;

import carsharing.dbclient.DbClient;

import java.util.List;

public class DbCustomerDao implements CustomerDao{

    private static final String CREATE_CUSTOMER_DB = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL," +
            "RENTED_CAR_ID INT DEFAULT NULL," +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
    private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMER (NAME) VALUES ('%s')";
    private static final String UPDATE_CUSTOMER = "UPDATE CUSTOMER " +
            "SET RENTED_CAR_ID = (%d)" +
            "WHERE ID = (%d);";
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";

    private final DbClient dbClient;

    public DbCustomerDao(DbClient dbClient) {
        this.dbClient = dbClient;
        this.dbClient.run(CREATE_CUSTOMER_DB);
    }

    @Override
    public void add(String name) {
        this.dbClient.run(String.format(INSERT_CUSTOMER, name));
    }

    @Override
    public void update(Integer rentedCarId, int id) {
        this.dbClient.run(String.format(UPDATE_CUSTOMER, rentedCarId, id));
    }

    @Override
    public List<Customer> listAll() {
        return this.dbClient.selectCustomers(SELECT_ALL);
    }
}
