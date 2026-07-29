package com.saadMeddiche.libreOffice_experimentation.templates.utils;

import com.saadMeddiche.libreOffice_experimentation.templates.Constants;
import com.saadMeddiche.libreOffice_experimentation.templates.Template;

public class NamingUtil {

    public static String templateName(Template template) {
        return template.getName() + Constants.TEMPLATE_SUFFIX + Constants.TEMPLATE_EXTENSION;
    }

    public static String injectedTemplateName(Template template) {
        return template.getName() + Constants.INJECTED_TEMPLATE_SUFFIX + Constants.TEMPLATE_EXTENSION;
    }

}