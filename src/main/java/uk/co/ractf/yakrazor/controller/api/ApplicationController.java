package uk.co.ractf.yakrazor.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.ractf.yakrazor.exception.ApplicationNotFoundException;
import uk.co.ractf.yakrazor.persistence.model.Application;
import uk.co.ractf.yakrazor.persistence.repository.ApplicationRepository;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationController(final ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public Iterable<Application> findAll() {
        return applicationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Application findById(@PathVariable final Long id) {
        return applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Application create(@RequestBody final Application application) {
        return applicationRepository.save(application);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        applicationRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody final Application application, @PathVariable final Long id) {
        applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        applicationRepository.save(application);
    }
    
}
