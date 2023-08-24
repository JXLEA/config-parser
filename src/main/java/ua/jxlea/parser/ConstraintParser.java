package ua.jxlea.parser;

import ua.jxlea.constraint.ConstraintsList;

import java.io.IOException;

public interface ConstraintParser {
    ConstraintsList mergeFiles(String fromFile, String toFile) throws IOException;

    void writeToYaml(String pathToFile, ConstraintsList constraintsList) throws IOException;
}
