package com.spaking.boot.starter.core.model;


import com.spaking.boot.starter.core.config.AppConfig;
import com.spaking.boot.starter.core.constant.ErrorCode;

import java.io.Serializable;

/**
 * ClassName: TransactionStatus <br/>
 * Function: 交易状态. <br/>
 * Reason: <br/>
 * Date: 2015-11-16 下午2:26:34 <br/>
 * 
 * @author xiezhong
 * @version v-1.0.0
 * @since JDK 1.7.0_65 64-Bit
 * @see
 */
public class TransactionStatus implements Serializable {

	private static final long serialVersionUID = -6734950643452265353L;
	
	/**
	 * 错误代码
	 */
	private String errorCode;

	/**
	 * 响应代码
	 */
	private String replyCode;

	/**
	 * 响应文本
	 */
	private String replyText;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 耗时
	 */
	private long duration;

	/**
	 * 构造函数
	 */
	public TransactionStatus() {
		this.errorCode = ErrorCode.SUCCESS;
		this.appName = AppConfig.APPLICATION_NAME;
	}

	public TransactionStatus(String replyCode, String replyText) {
	    this.errorCode = ErrorCode.EXCEPTION;
	    this.replyCode = replyCode;
        this.replyText = replyText;
        this.appName = AppConfig.APPLICATION_NAME;
	}
	
	/**
	 * 
	 * @param errorCode
	 * @param replyCode
	 * @param replyText
	 */
	public TransactionStatus(String errorCode, String replyCode, String replyText) {
		this.errorCode = errorCode;
		this.replyCode = replyCode;
		this.replyText = replyText;
		this.appName = AppConfig.APPLICATION_NAME;
	}

	/**
	 * 设置执行状态错误信息
	 * errorCode会默认设置为1, errorCode = 1代表自定义错误.
	 * @param replyCode
	 * @param replyText
	 */
	public void setError(String replyText, String replyCode){
		this.errorCode = ErrorCode.FAILURE;
		this.replyCode = replyCode;
		this.replyText = replyText;
		this.appName = AppConfig.APPLICATION_NAME;
	}
	
	public void setError(String replyText, String replyCode, String errorCode) {
	    this.errorCode = errorCode;
        this.replyCode = replyCode;
        this.replyText = replyText;
        this.appName = AppConfig.APPLICATION_NAME;
	}


	/**
	 * @return 错误代码
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            错误代码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return 响应代码
	 */
	public String getReplyCode() {
		return replyCode;
	}

	/**
	 * @param replyCode
	 *            响应代码
	 */
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}

	/**
	 * @return 响应文本
	 */
	public String getReplyText() {
		return replyText;
	}

	/**
	 * @param replyText
	 *            响应文本
	 */
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	/**
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 应用名称 - 用于在抛出错误时指明是哪个系统
	 * 
	 * @return
	 */
	public String getAppName() {
		return appName;
	}


	public Boolean isSuccess() {
		return this.errorCode.equals(ErrorCode.SUCCESS) ? true : false;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "TransactionStatus{" + "errorCode='" + errorCode + '\''
				+ ", replyCode='" + replyCode + '\'' + ", replyText='"
				+ replyText + '\'' + ", memo='" + memo + '\'' + ", appName='"
				+ appName + '\'' + '}';
	}
}
