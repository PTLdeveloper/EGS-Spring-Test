package restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import datamodel.Customer;

@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/getCustomer/{customerRef}")
	public Customer retrieve(@PathVariable String customerRef) {
		return customerRepository.findByCustomerRef(customerRef);
	}

	@PostMapping(value = "/insertCustomer", consumes = "application/json", produces = "application/json")
	public Customer newEmployee(@RequestBody Customer customer) {
		Customer createdCustomer = customerRepository.save(customer);
		return createdCustomer;
	}

}
