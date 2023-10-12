package carsharing;

import carsharing.cars.CarDao;
import carsharing.cars.DbCarDao;
import carsharing.companies.CompanyDao;
import carsharing.companies.DbCompanyDao;
import carsharing.customers.CustomerDao;
import carsharing.customers.DbCustomerDao;
import carsharing.dbclient.DbClient;
import carsharing.ui.UserInterface;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        DbClient dbClient = new DbClient();
        CompanyDao companyDao = new DbCompanyDao(dbClient);
        CarDao carDao = new DbCarDao(dbClient);
        CustomerDao customerDao = new DbCustomerDao(dbClient);
        UserInterface userInterface = new UserInterface(scanner, companyDao, carDao, customerDao);
        userInterface.runMainMenu();
    }
}
