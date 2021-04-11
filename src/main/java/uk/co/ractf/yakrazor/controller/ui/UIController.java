package uk.co.ractf.yakrazor.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.ractf.yakrazor.exception.DeploymentNotFoundException;
import uk.co.ractf.yakrazor.persistence.repository.ApplicationRepository;
import uk.co.ractf.yakrazor.persistence.repository.DeploymentRepository;

@Controller
@RequestMapping("/")
public class UIController {

    private final DeploymentRepository deploymentRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public UIController(final DeploymentRepository deploymentRepository,
                        final ApplicationRepository applicationRepository) {
        this.deploymentRepository = deploymentRepository;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("deployments", deploymentRepository.findAll());
        model.addAttribute("applications", applicationRepository.findAll());
        return "index";
    }

    @GetMapping("/deployment/{id}")
    public String viewDeployment(@PathVariable final Long id, final Model model) {
        model.addAttribute("deployment", deploymentRepository.findById(id).orElseThrow(DeploymentNotFoundException::new));
        return "deployment";
    }

}
