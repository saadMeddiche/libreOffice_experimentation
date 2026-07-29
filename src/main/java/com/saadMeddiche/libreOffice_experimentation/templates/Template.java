package com.saadMeddiche.libreOffice_experimentation.templates;

import java.util.List;

public interface Template {

    String getName();

    List<TextBox> getTextBoxes();

    record TextBox(
            String name,
            String value
    ) {}

}