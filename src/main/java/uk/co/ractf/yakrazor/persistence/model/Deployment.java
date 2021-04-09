package uk.co.ractf.yakrazor.persistence.model;

import uk.co.ractf.yakrazor.deployments.DeploymentListener;
import uk.co.ractf.yakrazor.persistence.converter.HashMapConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
@EntityListeners(DeploymentListener.class)
@Table(name = "deployments")
public class Deployment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String repository;

    @Column(nullable = false)
    private String branch;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> variables;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(final String repository) {
        this.repository = repository;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(final String branch) {
        this.branch = branch;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(final Map<String, Object> variables) {
        this.variables = variables;
    }
}
