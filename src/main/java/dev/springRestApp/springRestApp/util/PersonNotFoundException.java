package dev.springRestApp.springRestApp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonNotFoundException extends RuntimeException {

    private int personId;

}
