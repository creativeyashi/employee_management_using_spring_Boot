
package com.luv2code.springboot.thymeleafdemo;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
//import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThymeleafdemoApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepository;



	@Test
	public void getConsumersTest(){
		Employee consumer = new Employee(1, "yashika", "gupta", "gupta@gmail.com");

		List<Employee> consumersList=new ArrayList<>(Arrays.asList(consumer));
		when(employeeRepository.findAll()).thenReturn(consumersList);
		assertEquals(0,employeeService.findAll().size());
	}
	@Test
	public void getConsumerById(){
		Employee consumer = new Employee(1, "yashika", "gupta", "gupta@gmail.com");

		when(employeeRepository.findById(1)).thenReturn(Optional.of(consumer));
		assertEquals(consumer,employeeService.findById(consumer.getId()));
	}
	@Test
	public void deleteConsumerTest(){
		Employee consumer = new Employee(1, "yashika", "gupta", "gupta@gmail.com");

		employeeRepository.delete(consumer);
		employeeService.deleteById(consumer.getId());
		verify(employeeRepository,times(1)).deleteById(consumer.getId());
	}
	@Test
	public void save(){
		Employee consumer = new Employee(1, "yashika", "gupta", "gupta@gmail.com");

		employeeService.save(consumer);
		verify(employeeRepository, Mockito.times(1)).save(consumer);
	}

}