package com.inpowered.addressbooktest.core.addressbook;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.inpowered.addressbooktest.core.person.Gender;
import com.inpowered.addressbooktest.core.person.Person;

public interface AddressbookRepository {

    Set<Person> findAll();
    void add(UUID id, Person person);
    Optional<Person> getOldestPerson();
    int getTotalByGender(Gender gender);
}
