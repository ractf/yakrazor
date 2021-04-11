package uk.co.ractf.yakrazor.persistence.model;

import uk.co.ractf.yakrazor.persistence.converter.HashMapConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "services")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

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

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(final Map<String, Object> variables) {
        this.variables = variables;
    }
}
