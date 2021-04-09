package uk.co.ractf.yakrazor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.ractf.yakrazor.exception.DeploymentNotFoundException;
import uk.co.ractf.yakrazor.persistence.model.Deployment;
import uk.co.ractf.yakrazor.persistence.repository.DeploymentRepository;

@RestController
@RequestMapping("/api/deployments")
public class DeploymentsController {

    private final DeploymentRepository deploymentRepository;

    @Autowired
    public DeploymentsController(final DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
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
        return deploymentRepository.save(deployment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        deploymentRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody final Deployment deployment, @PathVariable final Long id) {
        deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new);
        deploymentRepository.save(deployment);
    }

}
