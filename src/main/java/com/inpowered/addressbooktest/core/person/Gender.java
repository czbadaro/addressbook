package com.inpowered.addressbooktest.core.person;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @JsonValue
    String getDescription() {
        return this.description;
    }

}
