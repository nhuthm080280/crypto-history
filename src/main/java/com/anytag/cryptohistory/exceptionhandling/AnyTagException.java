package com.anytag.cryptohistory.exceptionhandling;

public class AnyTagException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AnyTagException(String msg) {
        super(msg);
    }
}
