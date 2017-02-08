package com.toliga.ganjabots.core;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    private List<ValidationRule> validationRules = new ArrayList<>();

    public void addValidation(ValidationRule validationRule) {
        validationRules.add(validationRule);
    }

    public void validate() {
        for (ValidationRule rule : validationRules) {
            rule.validate();
        }
    }
}
