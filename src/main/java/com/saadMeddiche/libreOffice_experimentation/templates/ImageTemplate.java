package com.saadMeddiche.libreOffice_experimentation.templates;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageTemplate implements Template {

    @Override
    public String getName() {
        return "image";
    }

    @Override
    public List<TextBox> getTextBoxes() {
        return List.of(
                new TextBox("fullName", "Meddiche Saâd")
        );
    }

    @Override
    public List<ImageField> getImageFields() {
        return List.of(
                new ImageField("image", Constants.ASSETS_SOURCE.resolve("tree.png"))
        );
    }

}