package nz.mikhailov.example.customer;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CustomerRepositorySData extends CrudRepository<Customer, String> {

	@Override
	List<Customer> findAll();	
}
