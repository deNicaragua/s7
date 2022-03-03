package com.example.s7.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator  implements ConstraintValidator<ValidLogin, String> {

    private static final String LOGIN_PATTERN = "^([_A-Za-z0-9-+](.[_A-Za-z0-9-]+))@([A-Za-z\\d-]+\\.)[A-Za-z]{2,}$";
//            "^[_A-Za-z0-9-+] + (.[_A-Za-z0-9-]+)*@"
//            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)* (.[A-Za-z]{2,})$";
    @Override
    public void initialize(ValidLogin constraintAnnotation) {
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateLogin(email));
    }

    private boolean validateLogin(String email) {
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
