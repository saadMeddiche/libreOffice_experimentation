package com.saadMeddiche.libreOffice_experimentation.templates;

import java.nio.file.Path;

public interface Constants {

    String TEMPLATE_SUFFIX = "_template";

    String TEMPLATE_EXTENSION = ".pdf";

    Path TEMPLATES_SOURCE = Path.of("templates");

    String INJECTED_TEMPLATE_SUFFIX = TEMPLATE_SUFFIX + "_injected";

    Path INJECTED_TEMPLATES_DESTINATION = TEMPLATES_SOURCE.resolve(Path.of("results"));

}