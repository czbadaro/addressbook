package com.inpowered.addressbooktest.infrastructure.config;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;

@Configuration
public class CsvMapperConfigurer {

    private static final String DATE_TIME_FORMAT = "dd/MM/yy";

    @Bean("CsvMapper")
    public CsvMapper getCsvMapper() {
        CsvMapper csvMapper = new CsvMapper();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        csvMapper.setDateFormat(dateFormat);

        csvMapper.registerModule(new JavaTimeModule());
        csvMapper.registerModule(new BlackbirdModule());
        csvMapper.registerModule(new Jdk8Module());

        csvMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        csvMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        csvMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        csvMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        return csvMapper;
    }
}
