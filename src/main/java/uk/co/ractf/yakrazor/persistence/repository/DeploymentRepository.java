package uk.co.ractf.yakrazor.persistence.repository;

import uk.co.ractf.yakrazor.persistence.model.Deployment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeploymentRepository extends CrudRepository<Deployment, Long> {

    Optional<Deployment> findByName(String name);

}
