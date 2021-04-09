package uk.co.ractf.yakrazor.deployments.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import uk.co.ractf.yakrazor.persistence.model.Deployment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DockerComposeDeploymentEngine implements DeploymentEngine {

    @Override
    public void deploy(final File workingDir, final Deployment deployment) {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariables(deployment.getVariables());
        Map<String, String> deploymentVariables = new HashMap<>();
        deploymentVariables.put("name", deployment.getName());
        deploymentVariables.put("repository", deployment.getRepository());
        deploymentVariables.put("branch", deployment.getBranch());
        context.setVariable("deployment", deploymentVariables);


        String composeTemplatePath = workingDir.getAbsolutePath() + "/.yakrazor/docker-compose.yml";
        String compose = templateEngine.process(composeTemplatePath, context);
        try {
            Files.writeString(Paths.get(workingDir.getAbsolutePath(), "docker-compose.yml"), compose);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            new ProcessBuilder().directory(workingDir).command("docker-compose", "up", "-d").inheritIO().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
