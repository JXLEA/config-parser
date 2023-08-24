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

public class ConstraintYamlParser implements ConstraintParser {

    private final ObjectMapper mapper;

    public ConstraintYamlParser() {
        mapper = new ObjectMapper(YAMLFactory.builder()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .build());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    @Override
    public ConstraintsList parseFile(@NonNull String fileName) throws IOException {
        return mapper.readValue(new File(fileName), ConstraintsList.class);
    }

    @Override
    public ConstraintsList mergeFiles(@NonNull String sourceFile,
                                      @NonNull String targetFile,
                                      String fieldName) throws IOException {
        var sourceList = parseFile(sourceFile);
        var targetList = parseFile(targetFile);
        targetList.getConstraints().forEach(
                target -> setValue(sourceList.getById(target.getId()), target, fieldName));
        return targetList;
    }

    private void setValue(@NonNull Constraint source,
                          @NonNull Constraint target,
                          @NonNull String fieldName) {
        var field = ReflectionUtils.findField(Constraint.class, fieldName);
        ReflectionUtils.makeAccessible(field);
        var value = ReflectionUtils.getField(field, source);
        ReflectionUtils.setField(field, target, value);
    }

    public void writeToYaml(String pathToFile, ConstraintsList constraintsList) throws IOException {
        var yaml = mapper.writeValueAsString(constraintsList);
        try (var writer = new BufferedWriter(new FileWriter(pathToFile))) {
            writer.write(yaml);
        }
    }
}
