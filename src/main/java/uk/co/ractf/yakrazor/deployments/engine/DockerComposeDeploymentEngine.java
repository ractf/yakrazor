package uk.co.ractf.yakrazor.deployments.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import uk.co.ractf.yakrazor.deployments.YakrazorConfig;
import uk.co.ractf.yakrazor.deployments.templating.TemplateProcessor;
import uk.co.ractf.yakrazor.persistence.model.Application;
import uk.co.ractf.yakrazor.persistence.model.Deployment;
import uk.co.ractf.yakrazor.persistence.repository.ApplicationRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class DockerComposeDeploymentEngine implements DeploymentEngine {

    private ApplicationRepository applicationRepository;
    private TemplateProcessor templateProcessor;

    @Override
    @SuppressWarnings("unchecked")
    public void startDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config) {
        Application application = applicationRepository.findByName(config.getName());

        Map<String, String> deploymentVariables = new HashMap<>();
        deploymentVariables.put("name", deployment.getName());
        deploymentVariables.put("repository", deployment.getRepository());
        deploymentVariables.put("branch", deployment.getBranch());
        Map<String, Object> deploymentVariablesNested = new HashMap<>();
        deploymentVariablesNested.put("deployments", deploymentVariables);

        String composeTemplatePath = workingDir.getAbsolutePath() + "/.yakrazor/docker-compose.yml";
        String compose = templateProcessor.processTemplate(composeTemplatePath,
                application != null ? application.getVariables() : null,
                deployment.getVariables(), deploymentVariablesNested);
        try {
            Files.writeString(Paths.get(workingDir.getAbsolutePath(), "docker-compose.yml"), compose);
            new ProcessBuilder().directory(workingDir).command("docker-compose", "up", "-d").start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config) {
        try {
            new ProcessBuilder().directory(workingDir).command("docker-compose", "rm", "-sf").inheritIO().start().waitFor();
            FileSystemUtils.deleteRecursively(workingDir);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config) {
        try {
            new ProcessBuilder().directory(workingDir).command("git", "pull").start();
            new ProcessBuilder().directory(workingDir).command("docker-compose", "rm", "-sf").start();
            new ProcessBuilder().directory(workingDir).command("docker-compose", "up", "-d").start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getType() {
        return "docker-compose";
    }

    @Autowired
    public void setApplicationRepository(final ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Autowired
    public void setTemplateProcessor(final TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }
}
