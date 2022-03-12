package com.example.unittestexample.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    public static LocalDate parse(String date) {
        return LocalDate.parse(date);
    }
}
