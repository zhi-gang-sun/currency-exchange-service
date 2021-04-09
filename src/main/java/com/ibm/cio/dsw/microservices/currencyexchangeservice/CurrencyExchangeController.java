package com.ibm.cio.dsw.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repo;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange exchange(@PathVariable String from, @PathVariable String to) throws RuntimeException {

		//CurrencyExchange currencyExchange = new CurrencyExchange(1L, from, to, BigDecimal.valueOf(6.50));
		
		CurrencyExchange currencyExchange = repo.findByFromAndTo(from, to);
		
		if(currencyExchange ==null) {
			throw new RuntimeException("unable to find data for from " + from + " to " + to);
		}
		
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
		
		return currencyExchange;

	}
}