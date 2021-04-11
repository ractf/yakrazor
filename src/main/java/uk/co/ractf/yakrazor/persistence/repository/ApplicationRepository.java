package uk.co.ractf.yakrazor.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import uk.co.ractf.yakrazor.persistence.model.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findByName(String name);

}
