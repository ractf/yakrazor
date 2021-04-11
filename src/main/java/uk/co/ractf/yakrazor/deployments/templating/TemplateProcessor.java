package uk.co.ractf.yakrazor.deployments.templating;

import java.util.Map;

public interface TemplateProcessor {

    String processTemplate(String pathToTemplate, Map<String, Object>... variables);

}
