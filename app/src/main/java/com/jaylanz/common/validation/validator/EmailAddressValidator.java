package com.jaylanz.common.validation.validator;

import com.jaylanz.common.validation.constraint.EmailAddress;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailAddressValidator implements ConstraintValidator<EmailAddress, String> {
    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext constraintValidatorContext) {
        if (emailAddress == null)
            return true;
        if (StringUtils.hasText(emailAddress)) {
            String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return match(emailAddress, pattern) && emailAddress.length() <= 40;
        }
        return false;
    }

    private boolean match(String s, String regex) {
        return Pattern.compile(regex).matcher(s).matches();
    }
}
