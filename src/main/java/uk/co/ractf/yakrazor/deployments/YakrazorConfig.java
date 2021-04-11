package uk.co.ractf.yakrazor.deployments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YakrazorConfig {

    private String name;
    private String engine;

    public YakrazorConfig() {
    }

    public YakrazorConfig(final String name, final String engine) {
        this.name = name;
        this.engine = engine;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(final String engine) {
        this.engine = engine;
    }
}
