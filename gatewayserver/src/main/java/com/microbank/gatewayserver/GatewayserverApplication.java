package com.microbank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator micorBankRounteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route((p)-> p
						.path("/microbank/accounts/**")
						.filters(f-> f.rewritePath("/microbank/accounts/(?<segment>.*)","/${segment}"))
						.uri("lb://ACCOUNTS"))

				.route((p)-> p
						.path("/microbank/loans/**")
						.filters(f-> f.rewritePath("/microbank/loans/(?<segment>.*)","/${segment}"))
						.uri("lb://LOANS"))

				.route((p)-> p
						.path("/microbank/cards/**")
						.filters(f-> f.rewritePath("/microbank/cards/(?<segment>.*)","/${segment}"))
						.uri("lb://CARDS"))

				.build();
	}

}
