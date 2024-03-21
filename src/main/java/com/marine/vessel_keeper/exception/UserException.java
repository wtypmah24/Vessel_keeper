package com.marine.vessel_keeper.exception;

public class UserException extends Exception{
    public UserException(String message, Object ...args) {
        super(String.format(message, args));
    }
}