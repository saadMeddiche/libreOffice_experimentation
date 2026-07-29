package com.saadMeddiche.libreOffice_experimentation.templates.processes.sub;

import com.saadMeddiche.libreOffice_experimentation.templates.Template;

import java.util.List;

public interface SubProcess {

    String name();

    void execute(List<Template> templates);

}