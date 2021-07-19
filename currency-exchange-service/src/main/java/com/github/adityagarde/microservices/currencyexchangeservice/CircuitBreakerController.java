package com.github.adityagarde.microservices.currencyexchangeservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    //@Retry(name = "sample-api", fallbackMethod = "fallbackMethod")
    @CircuitBreaker(name = "sample-api", fallbackMethod = "fallbackMethod")
    //@RateLimiter(name = "default")
    public String sampleAPI() {
        logger.info("sample api call here");
        ResponseEntity<String> forEntity =
                new RestTemplate().getForEntity("http://localhost:8080/nothing", String.class);
        return forEntity.getBody();
    }

    public String fallbackMethod(Exception ex) {
        return "Fallback method here";
    }

}
