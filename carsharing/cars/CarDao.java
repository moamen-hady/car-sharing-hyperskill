package carsharing.cars;


import java.util.List;

public interface CarDao {

    void add(String name, int companyId);

    List<Car> listAll();
}
