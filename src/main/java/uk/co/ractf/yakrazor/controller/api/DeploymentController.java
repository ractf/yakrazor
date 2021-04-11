package uk.co.ractf.yakrazor.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import uk.co.ractf.yakrazor.deployments.YakrazorConfig;
import uk.co.ractf.yakrazor.deployments.engine.DeploymentEngineProvider;
import uk.co.ractf.yakrazor.exception.DeploymentNotFoundException;
import uk.co.ractf.yakrazor.persistence.model.Deployment;
import uk.co.ractf.yakrazor.persistence.repository.DeploymentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequestMapping("/api/deployments")
public class DeploymentController {

    private final DeploymentRepository deploymentRepository;
    private final DeploymentEngineProvider deploymentEngineProvider;

    @Autowired
    public DeploymentController(final DeploymentRepository deploymentRepository,
                                final DeploymentEngineProvider deploymentEngineProvider) {
        this.deploymentRepository = deploymentRepository;
        this.deploymentEngineProvider = deploymentEngineProvider;
    }

    @GetMapping
    public Iterable<Deployment> findAll() {
        return deploymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Deployment findById(@PathVariable final Long id) {
        return deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Deployment create(@RequestBody final Deployment deployment) {
        try {
            File workingDir = new File("/opt/ractf/yakrazor/" + deployment.getName());
            FileSystemUtils.deleteRecursively(workingDir);
            if (!workingDir.exists() && !workingDir.mkdirs()) {
                throw new IllegalStateException("Cannot create working dir");
            }
            Git.cloneRepository()
                    .setURI(deployment.getRepository())
                    .setDirectory(workingDir)
                    .setBranchesToClone(Collections.singleton(deployment.getBranch()))
                    .setBranch(deployment.getBranch()).call();
            YakrazorConfig yakrazorConfig = getYakrazorConfig(workingDir);

            deploymentEngineProvider.getDeploymentEngine(yakrazorConfig.getEngine()).startDeployment(workingDir, deployment, yakrazorConfig);
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        return deploymentRepository.save(deployment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        Deployment deployment = deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new);
        File workingDir = new File("/opt/ractf/yakrazor/" + deployment.getName());
        YakrazorConfig yakrazorConfig = getYakrazorConfig(workingDir);

        deploymentEngineProvider.getDeploymentEngine(yakrazorConfig.getEngine()).stopDeployment(workingDir, deployment, yakrazorConfig);
        deploymentRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody final Deployment deployment, @PathVariable final Long id) {
        deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new);
        deploymentRepository.save(deployment);
    }

    @PostMapping("/update/{id}")
    public void updateDeployment(@PathVariable final Long id) {
        Deployment deployment = deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new);
        File workingDir = new File("/opt/ractf/yakrazor/" + deployment.getName());
        YakrazorConfig yakrazorConfig = getYakrazorConfig(workingDir);
        deploymentEngineProvider.getDeploymentEngine(yakrazorConfig.getEngine()).updateDeployment(workingDir, deployment, yakrazorConfig);
    }

    private YakrazorConfig getYakrazorConfig(final File workingDir) {
        try {
            String configString = Files.readString(Paths.get(workingDir.getAbsolutePath(), ".yakrazor", "yakrazor.json"));
            return new ObjectMapper().readValue(configString, YakrazorConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
