package com.ll.medium_mission.global.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ValidateUtil {

    public boolean hasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    public ResponseEntity getErrors(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : errors) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errorMap);
    }

}
