package uz.alifservice.util;

import java.util.regex.Pattern;

public class EmailUtil {
    public static boolean isEmail(String value){
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(emailRegex, value);
    }
}
