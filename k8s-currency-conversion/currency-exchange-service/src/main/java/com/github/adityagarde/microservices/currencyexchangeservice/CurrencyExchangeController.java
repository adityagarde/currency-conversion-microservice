package com.github.adityagarde.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange fetchExchangeValue(@PathVariable String from, @PathVariable String to) {

        logger.info("Inside call of fetchExchangeValue - from == " + from + " to " + to);

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("No data found for from == " + from + " and to == " + to);
        }

        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");

        currencyExchange.setEnvironment(port + " v0.1.2 " + host);

        return currencyExchange;
    }
}
