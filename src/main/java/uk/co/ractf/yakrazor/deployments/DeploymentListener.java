package uk.co.ractf.yakrazor.deployments;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.util.FileSystemUtils;
import uk.co.ractf.yakrazor.deployments.engine.DockerComposeDeploymentEngine;
import uk.co.ractf.yakrazor.persistence.model.Deployment;

import javax.persistence.*;
import java.io.File;
import java.util.Collections;

public class DeploymentListener {

    @PrePersist
    public void prePersist(final Deployment deployment) {
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
            new DockerComposeDeploymentEngine().createDeployment(workingDir, deployment);


        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @PreRemove
    void preRemove(final Object object) {
    }
}
