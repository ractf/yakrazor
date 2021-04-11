package uk.co.ractf.yakrazor.deployments.templating;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import uk.co.ractf.yakrazor.exception.MissingDeploymentVariablesException;

import java.util.Map;

@Service
public class ThymeleafTemplateProcessor implements TemplateProcessor {

    @SafeVarargs
    @Override
    public final String processTemplate(final String pathToTemplate, final Map<String, Object>... variables) {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        for (final Map<String, Object> variableMap : variables) {
            if (variableMap == null) {
                continue;
            }
            for (final Map.Entry<String, Object> variable : variableMap.entrySet()) {
                context.setVariable(variable.getKey(), variable.getValue());
            }
        }

        try {
            return templateEngine.process(pathToTemplate, context);
        } catch (TemplateProcessingException e) {
            throw new MissingDeploymentVariablesException(e.getMessage());
        }
    }
}
