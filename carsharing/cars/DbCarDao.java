package carsharing.cars;

import carsharing.dbclient.DbClient;

import java.util.List;

public class DbCarDao implements CarDao {
    private static final String CREATE_CAR_DB = "CREATE TABLE IF NOT EXISTS CAR(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL," +
            "COMPANY_ID INT NOT NULL," +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
    private static final String INSERT_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('%s', %d)";
    private static final String SELECT_ALL = "SELECT * FROM CAR";

    private final DbClient dbClient;

    public DbCarDao(DbClient dbClient) {
        this.dbClient = dbClient;
        this.dbClient.run(CREATE_CAR_DB);
    }

    @Override
    public void add(String name, int companyId) {
        this.dbClient.run(String.format(INSERT_CAR, name, companyId));
    }

    @Override
    public List<Car> listAll() {
        return this.dbClient.selectCars(SELECT_ALL);
    }
}
