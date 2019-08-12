package com.spaking.boot.starter.core.exception;

/**
 * Function:  自定义异常基础类. <br/>
 * Date:      2015-11-16
 * @author   XIEZHONG
 * @version  3.0.0
 * @since    JDK 1.7.0_65 64-Bit
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 4290784890876524392L;

    private String errorDesc;
    private String errorCode;

    public String getErrorDesc(){
        return this.errorDesc;
    }

    public  String getErrorCode(){
        return this.errorCode;
    }

    public BaseException(){
        super();
    }

    public BaseException(String message){
        super(message);
        this.errorDesc = message;
    }

    public BaseException(String message, Throwable cause){
        super(message,cause);
        this.errorDesc = message;
    }
    
    /**
     * 
     * @param message
     * @param errorCode
     */
    public BaseException(String message, String errorCode){
    	super(message);
    	this.errorDesc = message;
    	this.errorCode = errorCode;
    }
    
    public BaseException(String message, String errorCode, Throwable cause){
    	super(message,cause);
    	this.errorDesc = message;
    	this.errorCode = errorCode;
    }
}
