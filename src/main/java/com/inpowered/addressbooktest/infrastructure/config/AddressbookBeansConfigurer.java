package com.inpowered.addressbooktest.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inpowered.addressbooktest.infrastructure.addressbook.InMemoryAddressbookRepository;

@Configuration
public class AddressbookBeansConfigurer {

    @Bean
    public InMemoryAddressbookRepository inMemoryAddressbookRepository() {
        return new InMemoryAddressbookRepository();
    }
}
