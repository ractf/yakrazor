package uk.co.ractf.yakrazor.deployments.engine;

import uk.co.ractf.yakrazor.deployments.YakrazorConfig;
import uk.co.ractf.yakrazor.persistence.model.Deployment;

import java.io.File;

public interface DeploymentEngine {

    void startDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config);

    void stopDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config);

    void updateDeployment(final File workingDir, final Deployment deployment, final YakrazorConfig config);

    String getType();

}
