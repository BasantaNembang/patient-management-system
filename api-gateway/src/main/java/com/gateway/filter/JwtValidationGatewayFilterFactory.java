package com.gateway.filter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(@Value("${auth.service.url}") String authURL,
                                             WebClient.Builder webClient){
        this.webClient = webClient.baseUrl(authURL).build();
    }


    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
           String jwtToken = exchange
                   .getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

           if(jwtToken == null || !jwtToken.startsWith("Bearer" )){
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               return exchange.getResponse().setComplete();
           }

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, jwtToken)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));

        });

    }



}
