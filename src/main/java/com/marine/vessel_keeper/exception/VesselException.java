package com.marine.vessel_keeper.exception;

public class VesselException extends Exception{
    public VesselException(String message, Object ...args) {
        super(String.format(message, args));
    }

}
