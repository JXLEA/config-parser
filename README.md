# This is a simple constraints util.

Current implementation supports:
- `yaml` file format processing

and such operations:
- `merge` files by specified field

## Usage

```java
java -jar constraint-parser.jar 
        'fromFile' 
        'mergeWith' 
        'toFile' 
        'fieldName'
```

or
set all required parameters to the

```java
ConstraintParserStarter()
```

### Command line arguments:

1. `fromFile` - file from which to read
1. `mergeWith` - file to merge with
1. `toFile` - file that will contain the result
1. `fieldName` - field name to merge by
