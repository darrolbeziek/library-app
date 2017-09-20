package com.library.app.common.exception;




public class FieldNotValidException extends RuntimeException {

    private final String fieldName;

    public FieldNotValidException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }


}
