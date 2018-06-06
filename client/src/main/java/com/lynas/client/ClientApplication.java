package com.lynas.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}


@FeignClient("order-service")
interface OrderService{
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	String hello();
}

@RestController
@RequestMapping("/client")
class HelloController {

	private final OrderService orderService;

	HelloController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/hello")
	public String hello() {
		return orderService.hello();
	}

}