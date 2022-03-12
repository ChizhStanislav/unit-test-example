package com.example.unittestexample.util;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void shouldReturnLocalDateTime_whenParse_givenStringDateTime() {
        // arrange

        // act
        var actual = DateUtil.parse("2022-02-10");

        // assert
        assertEquals(10, actual.getDayOfMonth());
    }

    @Test
    void shouldThrowException_whenParse_givenStringDateTimeIncorrect() {
        // arrange

        // act

        // assert
        var actual = assertThrows(DateTimeParseException.class, () -> DateUtil.parse("2022-0210"));
        assertEquals("Text '2022-0210' could not be parsed at index 7", actual.getMessage());
    }
}