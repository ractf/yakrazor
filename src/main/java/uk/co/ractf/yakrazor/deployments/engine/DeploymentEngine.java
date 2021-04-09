package uk.co.ractf.yakrazor.deployments.engine;

import uk.co.ractf.yakrazor.persistence.model.Deployment;

import java.io.File;

public interface DeploymentEngine {

    void createDeployment(final File workingDir, final Deployment deployment);

    void removeDeployment(final File workingDir, final Deployment deployment);

    void updateDeployment(final File workingDir, final Deployment deployment);

}
