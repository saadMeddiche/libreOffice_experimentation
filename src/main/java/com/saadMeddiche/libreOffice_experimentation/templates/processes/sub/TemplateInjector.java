package com.saadMeddiche.libreOffice_experimentation.templates.processes.sub;

import com.saadMeddiche.libreOffice_experimentation.templates.Constants;
import com.saadMeddiche.libreOffice_experimentation.templates.Template;
import com.saadMeddiche.libreOffice_experimentation.templates.utils.NamingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateInjector implements SubProcess {

    @Override
    public String name() {
        return "template_injector";
    }

    @Override
    public void execute(List<Template> templates) {

        for(var template : templates) {

            Path templatePath = Constants.TEMPLATES_SOURCE.resolve(NamingUtil.templateName(template));

            try(PDDocument document = Loader.loadPDF(templatePath.toFile())) {

                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                if(acroForm == null) {
                    log.error(AnsiOutput.toString(AnsiColor.YELLOW, " - WARN: template {} has no acro form"), template.getName());
                    return;
                }

                for (var textBox : template.getTextBoxes()) {

                    PDField field = acroForm.getField(textBox.name());

                    if (field == null) {
                        log.error(AnsiOutput.toString(AnsiColor.YELLOW, " - WARN: template {} has no field named: {}"), template.getName(), textBox.name());
                        continue;
                    }

                    field.setValue(textBox.value());

                }

                acroForm.flatten();

                Path resultPath = Constants.INJECTED_TEMPLATES_DESTINATION.resolve(NamingUtil.injectedTemplateName(template));

                document.save(resultPath.toFile());

                log.info(AnsiOutput.toString(AnsiColor.GREEN, " - Data injected successfully in template {}"), template.getName());

            }
            catch (Exception e) {
                log.error(AnsiOutput.toString(AnsiColor.RED, " - Error: Failed to inject data into template {}, cause: {}"), template.getName(), e.getMessage());
            }

        }

    }

}