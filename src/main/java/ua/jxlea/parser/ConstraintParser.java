package ua.jxlea.parser;

import lombok.NonNull;
import ua.jxlea.constraint.ConstraintsList;

import java.io.IOException;

public interface ConstraintParser {
    ConstraintsList parseFile(@NonNull String fileName) throws IOException;

    ConstraintsList mergeFiles(@NonNull String fromFile,
                               @NonNull String toFile,
                               String fieldName) throws IOException;
}
