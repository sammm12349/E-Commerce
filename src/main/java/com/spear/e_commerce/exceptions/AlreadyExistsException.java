package com.spear.e_commerce.exceptions;



public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String s) {
        super(s);
    }
}
