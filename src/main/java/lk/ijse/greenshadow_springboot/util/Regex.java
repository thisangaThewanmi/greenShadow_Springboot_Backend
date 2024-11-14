package lk.ijse.greenshadow_springboot.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Regex {

    // Enum to define pattern types
    public enum PatternType {
        STAFF,
        CROP,
        EQUIPMENT
    }

    // Define regex patterns as static constants
    public static final String STAFF_PATTERN = "^S-\\d{3}$";
    public static final String CROP_PATTERN = "^C-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String EQUIPMENT_PATTERN = "^E-\\d{3}$";
    private Pattern pattern;

    // Constructor selects a pattern based on the PatternType enum
    public Regex(PatternType patternType) {
        switch (patternType) {
            case STAFF:
                this.pattern = Pattern.compile(STAFF_PATTERN);
                break;
            case CROP:
                this.pattern = Pattern.compile(CROP_PATTERN);
                break;
            case EQUIPMENT:
                this.pattern = Pattern.compile(EQUIPMENT_PATTERN);
                break;
            default:
                throw new IllegalArgumentException("Unknown pattern type");
        }
    }

    // Method to validate an input string
    public boolean matches(String input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
