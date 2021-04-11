package uk.co.ractf.yakrazor.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.ractf.yakrazor.persistence.repository.DeploymentRepository;

@Controller
@RequestMapping("/")
public class UIController {

    private final DeploymentRepository deploymentRepository;

    @Autowired
    public UIController(final DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("deployments", deploymentRepository.findAll());
        return "index";
    }

}
