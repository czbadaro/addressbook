package com.inpowered.addressbooktest.infrastructure.addressbook;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.inpowered.addressbooktest.core.person.Gender;
import com.inpowered.addressbooktest.core.person.Person;
import com.inpowered.addressbooktest.core.addressbook.AddressbookRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InMemoryAddressbookRepository implements AddressbookRepository {

    private final Map<UUID, Person> addressbook = new ConcurrentHashMap<>();

    @Override
    public Set<Person> findAll() {
        log.info("Start finding all person on addressbook");

        LinkedHashSet<Person> result = new LinkedHashSet<>(addressbook.values());

        log.info("Finished finding all person on addressbook!");
        return result;
    }

    @Override
    public void add(UUID id, Person person) {
        log.info("Start adding person to addressbook");

        addressbook.put(id, person);

        log.info("Finished adding person to addressbook!");
    }

    @Override
    public Optional<Person> getOldestPerson() {
        return addressbook.values()
                .stream()
                .max(Comparator.comparing(Person::getBirthDate));
    }

    @Override
    public int getTotalByGender(Gender gender) {
        return addressbook.values()
                .stream()
                .filter(person -> person.getGender().equals(gender))
                .toList()
                .size();
    }
}
