package com.jaylanz.common.validation.validator;

import com.jaylanz.common.validation.constraint.Username;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null)
            return true;
        if (StringUtils.hasText(username)) {
            String s1 = StringUtils.trimLeadingCharacter(username, ' ');
            String s2 = StringUtils.trimTrailingWhitespace(username);
            if (s1.equals(username) && s2.equals(username))
                return isAlphanumeric(username) && username.length() >= 6 && username.length() <= 30;
        }
        return false;
    }

    private boolean isAlphanumeric(String s) {
        char[] charArray = s.toCharArray();
        for (char c : charArray)
            if (!Character.isLetterOrDigit(c))
                return false;
        return true;
    }
}
