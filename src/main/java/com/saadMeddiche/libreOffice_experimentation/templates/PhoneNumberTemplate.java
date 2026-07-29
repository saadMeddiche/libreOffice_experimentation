
package com.saadMeddiche.libreOffice_experimentation.templates;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneNumberTemplate implements Template {

    @Override
    public String getName() {
        return "phone_number";
    }

    @Override
    public List<TextBox> getTextBoxes() {
        return List.of(
                new TextBox("fullName", "Meddiche Saâd"),
                new TextBox("phoneNumber", "+212 652344600")
        );
    }

}
