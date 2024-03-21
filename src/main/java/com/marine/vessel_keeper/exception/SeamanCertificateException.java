package com.marine.vessel_keeper.exception;

public class SeamanCertificateException extends Exception{
    public SeamanCertificateException(String message, Object ...args) {
        super(String.format(message, args));
    }

}
