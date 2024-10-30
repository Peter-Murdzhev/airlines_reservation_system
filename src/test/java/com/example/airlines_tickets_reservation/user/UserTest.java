package com.example.airlines_tickets_reservation.user;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void doesEmailPatternWork(){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher("judex71@abv.bg");

        assertTrue(matcher.matches());
    }

    @Test
    void doesEmailPatternFail(){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Matcher matcher = pattern.matcher("go6o.ivanov.gmail.com");

        assertFalse(matcher.matches());
    }

}