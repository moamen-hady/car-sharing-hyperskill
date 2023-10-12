package carsharing.companies;

import carsharing.dbclient.DbClient;

import java.util.List;

public class DbCompanyDao implements CompanyDao {

    private static final String CREATE_COMPANY_DB = "CREATE TABLE IF NOT EXISTS COMPANY(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL);";
    private static final String INSERT_COMPANY = "INSERT INTO COMPANY (NAME) VALUES ('%s')";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";

    private final DbClient dbClient;

    public DbCompanyDao(DbClient dbClient) {
        this.dbClient = dbClient;
        this.dbClient.run(CREATE_COMPANY_DB);
    }

    @Override
    public void add(String name) {
        this.dbClient.run(String.format(INSERT_COMPANY, name));
    }

    @Override
    public List<Company> listAll() {
        return this.dbClient.selectCompanies(SELECT_ALL);
    }


}
