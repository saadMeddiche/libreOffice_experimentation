package com.saadMeddiche.libreOffice_experimentation.templates;

import java.nio.file.Path;
import java.util.List;

public interface Template {

    String getName();

    List<TextBox> getTextBoxes();

    default List<ImageField> getImageFields() {
        return List.of();
    }

    record TextBox(
            String name,
            String value
    ) {}

    record ImageField(
            String fieldName,
            Path imagePath
    ) {}

}