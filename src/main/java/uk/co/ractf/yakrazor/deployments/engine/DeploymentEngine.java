package uk.co.ractf.yakrazor.deployments.engine;

import uk.co.ractf.yakrazor.persistence.model.Deployment;

import java.io.File;

public interface DeploymentEngine {

    void deploy(final File workingDir, final Deployment deployment);

}
