package ua.jxlea.constraint;

import lombok.Data;

import java.util.List;

@Data
public class ConstraintsList {

    private List<Constraint> constraints;

    public Constraint getById(String id) {
        return constraints.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
