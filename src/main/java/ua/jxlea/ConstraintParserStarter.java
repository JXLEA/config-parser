package ua.jxlea;

import ua.jxlea.exception.ConstraintParseException;
import ua.jxlea.parser.ConstraintYamlParser;

import java.io.IOException;

/**
    * This class is used to start the application.
    * The first file is the source of constraints.
    * The second file is the target of constraints.
    * The third file is the result of merging.
    * The fourth argument is the name of the field that will be copied from the first file to the second.
    * The field must be present in the Constraint class.
 * **/
public class ConstraintParserStarter {
    public static void main(String[] args) {
        var parser = new ConstraintYamlParser();
        try {
            var merged = parser.mergeFiles(args[0], args[1], args[3]);
            parser.writeToYaml(args[2], merged);
        } catch (IOException e) {
            throw new ConstraintParseException("Error while parsing constraints", e);
        }
    }
}