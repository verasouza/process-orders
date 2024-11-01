package com.vsouza.processorders.controllers.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {


    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime time;
    private String uri;

    @Getter
    private final List<String> errors = new ArrayList<>();

    public void addError(final String message) {
        errors.add(message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String errorMessage() {
        return errors.toString();
    }
}