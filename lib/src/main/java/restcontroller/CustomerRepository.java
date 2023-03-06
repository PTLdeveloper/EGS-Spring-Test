package restcontroller;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

import datamodel.Customer;

@EntityScan("datamodel")
public interface CustomerRepository extends CrudRepository<Customer, String> {

}