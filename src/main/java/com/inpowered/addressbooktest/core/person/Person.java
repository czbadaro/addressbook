package com.inpowered.addressbooktest.core.person;

import java.time.Duration;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@JsonPropertyOrder({ "name", "gender", "birthDate" })
public class Person {

    private final String name;
    private final Gender gender;
    private final Date birthDate;

    @Builder
    @Jacksonized
    public Person(
            @NonNull String name,
            @NonNull Gender gender,
            @NonNull Date birthDate) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public static Duration getAgeDifferenceBetween(Person personOne, Person personTwo) {

        return Duration.between(
                personOne.getBirthDate().toInstant(),
                personTwo.getBirthDate().toInstant());
    }

}
