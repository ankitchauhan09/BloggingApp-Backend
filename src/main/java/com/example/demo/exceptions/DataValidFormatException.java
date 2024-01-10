package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataValidFormatException {

    String field;
    String errorMessage;

    public DataValidFormatException(String field, String errorMessage){
        this.field = field;
        this.errorMessage = errorMessage;
    }

}
