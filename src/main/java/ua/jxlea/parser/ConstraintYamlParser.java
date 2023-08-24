package ua.jxlea.parser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.NonNull;
import org.springframework.util.ReflectionUtils;
import ua.jxlea.constraint.Constraint;
import ua.jxlea.constraint.ConstraintsList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConstraintYamlParser {

    private final ObjectMapper mapper;

    public ConstraintYamlParser() {
        var yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        yamlFactory.configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true);
        mapper = new ObjectMapper(yamlFactory);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    private ConstraintsList parseYaml(String fileName) throws IOException {
        return mapper.readValue(new File(fileName), ConstraintsList.class);
    }

    public ConstraintsList mergeFiles(@NonNull String fromFile,
                                      @NonNull String toFile,
                                      String fieldName) throws IOException {
        var from = parseYaml(fromFile);
        var to = parseYaml(toFile);
        to.getConstraints().forEach(
                toConstraint -> {
                    var fromConstraint = from.getById(toConstraint.getId());
                    setValue(fromConstraint, toConstraint, fieldName);
                });
        return to;
    }

    private void setValue(@NonNull Constraint fromConstraint,
                          @NonNull Constraint toConstraint,
                          String fieldName) {
        var field = ReflectionUtils.findField(Constraint.class, fieldName);
        ReflectionUtils.makeAccessible(field);
        var value = ReflectionUtils.getField(field, fromConstraint);
        ReflectionUtils.setField( field, toConstraint, value);
    }


    public void writeToYaml(String pathToFile, ConstraintsList constraintsList) throws IOException {
        var yaml = mapper.writeValueAsString(constraintsList);
        try (var writer = new BufferedWriter(new FileWriter(new File(pathToFile)))) {
            writer.write(yaml);
        }
    }
}
