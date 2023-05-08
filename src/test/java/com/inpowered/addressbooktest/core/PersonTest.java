package com.inpowered.addressbooktest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.time.Duration;
import java.util.Date;

import com.inpowered.addressbooktest.core.person.Gender;
import com.inpowered.addressbooktest.core.person.Person;
import org.junit.jupiter.api.Test;

public class PersonTest {
    @Test
    void assertNonNulls() {
        assertThrows(NullPointerException.class,
                () -> new Person(null,
                        Gender.MALE,
                        new Date()));
        assertThrows(NullPointerException.class,
                () -> new Person("random",
                        null,
                        new Date()));
        assertThrows(NullPointerException.class,
                () -> new Person("random",
                        Gender.MALE,
                        null));
    }

    @Test
    void getAgeDifferenceBetween() {
        Date date = new Date();
        Person person1 = Person.builder()
                .name("p1")
                .gender(Gender.MALE)
                .birthDate(date)
                .build();
        Person person2 = Person.builder()
                .name("p2")
                .gender(Gender.FEMALE)
                .birthDate(date)
                .build();
        Duration ageDifference = Person.getAgeDifferenceBetween(person1, person2);
        long result = ageDifference.toDays();
        long expected = 0;

        assertEquals(expected, result);
    }
}
