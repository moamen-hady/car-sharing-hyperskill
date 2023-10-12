package carsharing.ui;

import carsharing.cars.Car;
import carsharing.cars.CarDao;
import carsharing.companies.Company;
import carsharing.companies.CompanyDao;
import carsharing.customers.Customer;
import carsharing.customers.CustomerDao;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public UserInterface(Scanner scanner, CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.scanner = scanner;
        scanner.useDelimiter(System.lineSeparator());
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    public void runMainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            int input = this.scanner.nextInt();
            if (input == 0) {
                System.exit(0);
            } else if (input == 1) {
                runManagerMenu();
            } else if (input == 2) {
                loginAsCustomer();
            } else if (input == 3) {
                createACustomer();
            }
        }
    }

    private void createACustomer() {
        System.out.println();
        System.out.println("Enter the customer name:");
        String input = scanner.next();
        this.customerDao.add(input);
        System.out.println("The customer was added!");
        System.out.println();
    }

    private void loginAsCustomer() {
        System.out.println();
        if (this.customerDao.listAll().isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            runMainMenu();
        }
        while (true) {
            System.out.println("Customer list:");
            for (Customer customer : this.customerDao.listAll()) {
                System.out.println(customer.getId() + ". " + customer.getName());
            }
            System.out.println("0. Back");
            int input = scanner.nextInt();
            if (input == 0) {
                runMainMenu();
            }
            openCustomer(input);
        }
    }

    private void openCustomer(int input) {
        Customer customer = this.customerDao.listAll().get(input - 1);
        System.out.println();
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            input = scanner.nextInt();
            System.out.println();
            if (input == 0) {
                runMainMenu();
            } else if (input == 1) {
                rentACar(customer.getId());
            } else if (input == 2) {
                returnRentedCar(customer.getId());
            } else if (input == 3) {
                printRentedCar(customer.getId());
            }
        }
    }

    private void rentACar(int customerId) {
        if (this.companyDao.listAll().isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        if (this.customerDao.listAll().get(customerId - 1).getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            System.out.println();
            return;
        }
        while (true) {
            System.out.println("Choose a company:");
            for (Company company : this.companyDao.listAll()) {
                System.out.println(company.getId() + ". " + company.getName());
            }
            System.out.println("0. Back");
            int companyId = scanner.nextInt();
            if (companyId == 0) {
                runManagerMenu();
            }
            chooseACar(companyId, customerId);
        }

    }

    private void chooseACar(int companyId, int customerId) {
        System.out.println();

        List<Integer> rentedCarsIds = new ArrayList<>();
        for (Customer customer : this.customerDao.listAll()) {
            if (customer.getRentedCarId() != 0) {
                rentedCarsIds.add(customer.getRentedCarId());
            }
        }

        int i = 0;
        for (; i < this.carDao.listAll().size(); i++) {
            if (this.carDao.listAll().get(i).getCompanyId() == companyId) {
                if (i == 0) {
                    System.out.println("Choose a car:");
                }
                if (!rentedCarsIds.contains(this.carDao.listAll().get(i).getId())) {
                    System.out.println(i + 1 + ". " + this.carDao.listAll().get(i).getName());
                }
            }
        }
        if (i > 0) {
            System.out.println("0. Back");
        }
        if (i == 0) {
            System.out.println("No available cars in the '"
                    + this.companyDao.listAll().get(companyId).getName() + "' company");
            System.out.println();
            return;
        }
        int rentedCarId = scanner.nextInt();
        if (rentedCarId == 0) {
            return;
        }
        this.customerDao.update(rentedCarId, customerId);
        System.out.println();
        System.out.println("You rented '" + this.carDao.listAll().get(rentedCarId - 1).getName() + "'");
        openCustomer(customerId);
    }

    private void returnRentedCar(int customerId) {
        if (this.customerDao.listAll().get(customerId - 1).getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            System.out.println();
            return;
        }
        this.customerDao.update(null, customerId);
        System.out.println("You've returned a rented car!");
        System.out.println();
    }

    private void printRentedCar(int input) {
        int i = 0;
        for (Car car : this.carDao.listAll()) {
            if (car.getId() == this.customerDao.listAll().get(input - 1).getRentedCarId()) {
                if (i == 0) {
                    System.out.println("Your rented car:");
                }
                System.out.println(car.getName());
                System.out.println("Company:");
                System.out.println(this.companyDao.listAll().get(car.getCompanyId() - 1).getName());
                i++;
            }
        }
        if (i == 0) {
            System.out.println("You didn't rent a car!");
            System.out.println();
        }
        System.out.println();
    }


    private void runManagerMenu() {
        while (true) {
            System.out.println();
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int input = this.scanner.nextInt();
            System.out.println();
            if (input == 0) {
                runMainMenu();
            } else if (input == 1) {
                listAllCompanies();
            } else if (input == 2) {
                createACompany();
            }
        }
    }

    private void listAllCompanies() {
        if (this.companyDao.listAll().isEmpty()) {
            System.out.println("The company list is empty!");
            System.out.println();
            return;
        }
        while (true) {
            System.out.println("Choose the company:");
            for (Company company : this.companyDao.listAll()) {
                System.out.println(company.getId() + ". " + company.getName());
            }
            System.out.println("0. Back");
            int input = scanner.nextInt();
            if (input == 0) {
                runManagerMenu();
            }
            openCompany(input);
        }
    }

    private void openCompany(int input) {
        Company company = this.companyDao.listAll().get(input - 1);
        System.out.println();
        System.out.println("'" + company.getName() + "'" + " company");
        while (true) {
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            input = scanner.nextInt();
            if (input == 0) {
                runManagerMenu();
            } else if (input == 1) {
                listCompanyCars(company.getId());
            } else if (input == 2) {
                createACar(company.getId());
            }
        }
    }

    private void createACar(int companyId) {
        System.out.println();
        System.out.println("Enter the car name:");
        String input = scanner.next();
        this.carDao.add(input, companyId);
        System.out.println("The car was added!");
        System.out.println();
    }

    private void listCompanyCars(int id) {
        System.out.println();
        int i = 0;
        for (Car car : this.carDao.listAll()) {
            if (car.getCompanyId() == id) {
                if (i == 0) {
                    System.out.println("Car list:");
                }
                System.out.println(++i + ". " + car.getName());
            }
        }
        if (i == 0) {
            System.out.println("The car list is empty!");
        }
        System.out.println();
    }

    private void createACompany() {
        System.out.println("Enter the company name:");
        String name = scanner.next();
        this.companyDao.add(name);
        System.out.println("The company was created!");
    }

}
