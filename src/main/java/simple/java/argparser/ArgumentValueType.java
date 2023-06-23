package simple.java.argparser;

import java.io.File;

/**
 * The ArgumentValueType enum represents the possible types for argument values.
 *
 * @author Pedro Marín Sanchis
 */
public enum ArgumentValueType {
    INTEGER {
        @Override
        public Object parseString(String value) throws ArgumentException {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ArgumentException("Argument value is not an Integer.");
            }
        }
    },
    FLOAT {
        @Override
        public Object parseString(String value) throws ArgumentException {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                throw new ArgumentException("Argument value is not a Float.");
            }
        }
    },
    DOUBLE {
        @Override
        public Object parseString(String value) throws ArgumentException {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                throw new ArgumentException("Argument value is not a Double.");
            }
        }
    },
    CHARACTER {
        @Override
        public Object parseString(String value) throws ArgumentException {
            if (value.length() != 1) {
                throw new ArgumentException("Argument value is not a Character.");
            }
            return value.charAt(0);
        }
    },
    STRING {
        @Override
        public Object parseString(String value) throws ArgumentException {
            return value;
        }
    },
    BOOLEAN {
        @Override
        public Object parseString(String value) throws ArgumentException {
            if (value.equalsIgnoreCase("true")) {
                return true;
            } else if (value.equalsIgnoreCase("false")) {
                return false;
            } else {
                throw new ArgumentException("Argument value is not a boolean.");
            }
        }
    },
    DIRECTORY {
        @Override
        public Object parseString(String value) throws ArgumentException {
            try {
                File directory = new File(value);
                if (directory.isDirectory()) {
                    return directory;
                }
            } catch (Exception ignored){}
            throw new ArgumentException("Argument value is not a directory.");
        }
    },
    FILE {
        @Override
        public Object parseString(String value) throws ArgumentException {
            try {
                File file = new File(value);
                if (file.isFile()) {
                    return file;
                }
            } catch (Exception ignored){}
            throw new ArgumentException("Argument value is not a file.");
        }
    };

    /**
     * Parses the string value into the corresponding object based on the argument value type.
     *
     * @param value The string value to be parsed.
     * @return The parsed object.
     * @throws ArgumentException If an error occurs during parsing.
     */
    public abstract Object parseString(String value) throws ArgumentException;
}
