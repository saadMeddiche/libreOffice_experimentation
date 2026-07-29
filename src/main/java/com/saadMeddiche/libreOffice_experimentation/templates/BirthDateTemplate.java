package com.saadMeddiche.libreOffice_experimentation.templates;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class BirthDateTemplate implements Template {

    @Override
    public String getName() {
        return "birthdate";
    }

    @Override
    public List<TextBox> getTextBoxes() {
        return List.of(
                new TextBox("fullName", "Meddiche Saâd"),
                new TextBox("birthdate", LocalDate.of(2000, 1, 1).toString()),
                new TextBox("birthtime", LocalTime.of(9, 30, 0).toString())
        );
    }

}
