package com.saadMeddiche.libreOffice_experimentation.templates.processes;

import com.saadMeddiche.libreOffice_experimentation.templates.Template;
import com.saadMeddiche.libreOffice_experimentation.templates.processes.sub.TemplateExistence;
import com.saadMeddiche.libreOffice_experimentation.templates.processes.sub.SubProcess;
import com.saadMeddiche.libreOffice_experimentation.templates.processes.sub.TemplateInjector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class MainProcess implements CommandLineRunner {

    private final List<Template> templates;

    @Override
    public void run(String... args) {

        this.startSubProcess(new TemplateExistence());

        this.startSubProcess(new TemplateInjector());

    }

    private void startSubProcess(SubProcess subProcess) {

        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "Starting sub-process [{}]"), subProcess.name());
        subProcess.execute(templates);
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "Sub-process [{}] is finished"), subProcess.name());

    }

}