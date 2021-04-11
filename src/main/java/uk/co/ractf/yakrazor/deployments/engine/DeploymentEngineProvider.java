package uk.co.ractf.yakrazor.deployments.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeploymentEngineProvider {

    private final List<DeploymentEngine> deploymentEngines;

    @Autowired
    public DeploymentEngineProvider(final List<DeploymentEngine> deploymentEngines) {
        this.deploymentEngines = deploymentEngines;
    }

    public DeploymentEngine getDeploymentEngine(final String type) {
        for (final DeploymentEngine deploymentEngine : deploymentEngines) {
            if (deploymentEngine.getType().equalsIgnoreCase(type)) {
                return deploymentEngine;
            }
        }

        return null;
    }

}
