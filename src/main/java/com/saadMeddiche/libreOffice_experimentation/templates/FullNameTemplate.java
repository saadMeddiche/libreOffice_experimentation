package com.saadMeddiche.libreOffice_experimentation.templates;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FullNameTemplate implements Template {

    @Override
    public String getName() {
        return "full_name";
    }

    @Override
    public List<TextBox> getTextBoxes() {
        return List.of(
                new TextBox("firstName", "Saâd"),
                new TextBox("lastName", "Meddiche")
        );
    }

}
