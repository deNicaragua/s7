package com.example.s7.exception;

public class UserAlreadyExistException extends  Exception {
    public  UserAlreadyExistException(String massage) {
        super(massage);
    }
}
