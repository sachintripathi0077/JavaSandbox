package com.dev.accounts.functions;

import com.dev.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {
    private static final Logger logger = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountService){
        return accountNumber -> {
            logger.info("Updating communication for account number: " + accountNumber.toString());
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}
