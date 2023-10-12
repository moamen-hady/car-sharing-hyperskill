package carsharing.companies;

import java.util.List;

public interface CompanyDao {
    void add(String name);

    List<Company> listAll();

}
