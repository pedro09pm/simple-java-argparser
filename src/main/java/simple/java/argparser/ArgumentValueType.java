package simple.java.argparser;

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
    };

    public abstract Object parseString(String value) throws ArgumentException;
}