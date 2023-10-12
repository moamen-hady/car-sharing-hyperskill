package carsharing.customers;

import java.util.List;

public interface CustomerDao {
    void add(String name);
    void update(Integer rentedCarId ,int id);
    List<Customer> listAll();
}
