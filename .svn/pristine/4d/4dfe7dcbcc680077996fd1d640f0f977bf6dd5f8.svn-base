package com.cmcc.seed.mall_cp;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String code;

    public ServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ServiceException [code=" + code + ", " + super.toString() + "]";
    }

}
