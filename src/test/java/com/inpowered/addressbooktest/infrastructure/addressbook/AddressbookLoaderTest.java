package com.inpowered.addressbooktest.infrastructure.addressbook;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.inpowered.addressbooktest.core.addressbook.AddressbookRepository;
import com.inpowered.addressbooktest.core.person.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@SpringBootTest
public class AddressbookLoaderTest {
    @Autowired
    CsvMapper csvMapper;
    @Autowired
    AddressbookRepository addressbookRepository;
    @MockBean
    ApplicationContext applicationContext;
    @Mock
    Resource resource;

    @Test
    void assertNonNulls() {
        assertThrows(NullPointerException.class,
                () -> new AddressbookLoader(null,
                        addressbookRepository,
                        csvMapper));

        assertThrows(NullPointerException.class,
                () -> new AddressbookLoader(applicationContext,
                        null,
                        csvMapper));

        assertThrows(NullPointerException.class,
                () -> new AddressbookLoader(applicationContext,
                        addressbookRepository,
                        null));
    }

    @Test
    void load() {
        int result = addressbookRepository.findAll().size();
        assertTrue(result > 0);
    }

    @Test
    void loadThrowsWhenNotLoad() throws IOException {
        Resource[] resources = { resource };
        when(applicationContext.getResources("classpath*:addressbook/AddressBook.txt"))
                .thenThrow(IOException.class);

        AddressbookLoader loader = new AddressbookLoader(applicationContext, addressbookRepository,
                csvMapper);

        assertThrows(RuntimeException.class, loader::load);
    }

    @Test
    void loadThrowsWhenNotParse() throws IOException {
        Resource[] resources = { resource };
        when(applicationContext.getResources("classpath*:addressbook/AddressBook.txt"))
                .thenReturn(resources);
        when(resource.getInputStream()).thenThrow(IOException.class);
        when(resource.exists()).thenReturn(true);

        AddressbookLoader loader = new AddressbookLoader(applicationContext, addressbookRepository,
                csvMapper);

        assertThrows(RuntimeException.class, loader::load);
    }
}
