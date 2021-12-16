package net.ssimmie.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

public class ChecklistResourceRepresentation
        extends RepresentationModel<ChecklistResourceRepresentation> {
    private String name;

    @JsonProperty("_links")
    public void setLinks(final Map<String, Link> links) {
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
