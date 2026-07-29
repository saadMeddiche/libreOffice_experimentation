package com.saadMeddiche.libreOffice_experimentation.templates.processes.sub;

import com.saadMeddiche.libreOffice_experimentation.templates.Constants;
import com.saadMeddiche.libreOffice_experimentation.templates.Template;
import com.saadMeddiche.libreOffice_experimentation.templates.utils.NamingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class TemplateExistence implements SubProcess {

    @Override
    public String name() {
        return "template_existence";
    }

    @Override
    public void execute(List<Template> templates) {

        for(var template : templates) {

            Path filePath = Constants.TEMPLATES_SOURCE.resolve(NamingUtil.templateName(template));

            if(Files.exists(filePath)) {
                log.info(" - Template [{}] existence: {}", template.getName(), AnsiOutput.toString(AnsiColor.GREEN, true));
            }
            else {
                log.info(" - Template [{}] existence: {}", template.getName(), AnsiOutput.toString(AnsiColor.RED, false));
            }

        }

    }

}