package uk.co.ractf.yakrazor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.ractf.yakrazor.exception.ServiceNotFoundException;
import uk.co.ractf.yakrazor.persistence.model.Service;
import uk.co.ractf.yakrazor.persistence.repository.ServiceRepository;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceController(final ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public Iterable<Service> findAll() {
        return serviceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Service findById(@PathVariable final Long id) {
        return serviceRepository.findById(id).orElseThrow(ServiceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Service create(@RequestBody final Service service) {
        return serviceRepository.save(service);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        serviceRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody final Service service, @PathVariable final Long id) {
        serviceRepository.findById(id).orElseThrow(ServiceNotFoundException::new);
        serviceRepository.save(service);
    }
    
}
