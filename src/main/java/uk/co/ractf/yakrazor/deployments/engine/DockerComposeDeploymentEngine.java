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

    @Override
    public void startDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config) {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        Application application = applicationRepository.findByName(config.getName());
        if (application != null) {
            context.setVariables(application.getVariables());
        }
        for (Map.Entry<String, Object> variable : deployment.getVariables().entrySet()) {
            context.setVariable(variable.getKey(), variable.getValue());
        }

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
}
