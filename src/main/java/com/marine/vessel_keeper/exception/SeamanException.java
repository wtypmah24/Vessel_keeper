package com.marine.vessel_keeper.exception;

public class SeamanException extends Exception{
    public SeamanException(String message, Object ...args) {
        super(String.format(message, args));
    }
}
