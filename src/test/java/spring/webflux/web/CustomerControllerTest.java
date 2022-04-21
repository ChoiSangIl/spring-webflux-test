package spring.webflux.web;

import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import spring.webflux.controller.CustomerController;
import spring.webflux.domail.Customer;
import spring.webflux.domail.CustomerRepository;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CustomerController.class)
public class CustomerControllerTest {
	
	@MockBean
	CustomerRepository repository;
	 
	@Autowired
	private WebTestClient webClient;
	
	@Test
	@DisplayName("Customer 한건 찾기")
	public void findById() {
		Customer customer = new Customer("gildong", "hong");
		doReturn(Mono.just(customer)).when(repository).findById(1L);
		
		webClient.get().uri("/customer/{id}", 1L)
	      .header(HttpHeaders.ACCEPT, "application/json")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBodyList(Customer.class);
	}
}
