package com.saadMeddiche.libreOffice_experimentation.templates.processes.sub;

import com.saadMeddiche.libreOffice_experimentation.templates.Constants;
import com.saadMeddiche.libreOffice_experimentation.templates.Template;
import com.saadMeddiche.libreOffice_experimentation.templates.utils.NamingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

                this.injectTextBoxes(template, acroForm);

                this.injectImages(template, acroForm, document);

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

    private void injectTextBoxes(Template template, PDAcroForm pdAcroForm) throws IOException {

        for (var textBox : template.getTextBoxes()) {

            PDField field = pdAcroForm.getField(textBox.name());

            if (field == null) {
                log.error(AnsiOutput.toString(AnsiColor.YELLOW, " - WARN: template {} has no field named: {}"), template.getName(), textBox.name());
                continue;
            }

            field.setValue(textBox.value());

        }

    }

    private void injectImages(Template template, PDAcroForm pdAcroForm, PDDocument document) throws IOException {

        for (var imageField : template.getImageFields()) {

            PDField field = pdAcroForm.getField(imageField.fieldName());

            if (field == null) {
                log.error(AnsiOutput.toString(AnsiColor.YELLOW, " - WARN: template {} has no image field named: {}"), template.getName(), imageField.fieldName());
                continue;
            }

            if (!Files.exists(imageField.imagePath())) {
                log.error(AnsiOutput.toString(AnsiColor.YELLOW, " - WARN: image file does not exist at: {}"), imageField.imagePath());
                continue;
            }

            if (field.getWidgets().isEmpty()) {
                continue;
            }

            PDAnnotationWidget widget = field.getWidgets().getFirst();

            PDPage page = widget.getPage();

            if (page != null) {

                PDRectangle rect = widget.getRectangle();
                PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imageField.imagePath().toFile(), document);

                try (PDPageContentStream contentStream = new PDPageContentStream(
                        document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {

                    contentStream.drawImage(pdImage, rect.getLowerLeftX(), rect.getLowerLeftY(), rect.getWidth(), rect.getHeight());

                }

                field.setValue("");

            }

        }

    }

}