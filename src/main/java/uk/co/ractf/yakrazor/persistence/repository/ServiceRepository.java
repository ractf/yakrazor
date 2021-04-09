package uk.co.ractf.yakrazor.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.ractf.yakrazor.persistence.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long> {
}
