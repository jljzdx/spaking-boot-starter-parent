package com.spaking.boot.starter.core.advice;

import com.spaking.boot.starter.core.constant.ErrorCode;
import com.spaking.boot.starter.core.dto.GenericResponseDTO;
import com.spaking.boot.starter.core.exception.BaseException;
import com.spaking.boot.starter.core.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Runtime异常拦处理，自定义异常会返回自定义错误回复代码以及错误描述，其它异常会返回通知管理员。
 * 自定义异常的时候返回的错误码为1，其它异常返回的错误码为9。
 */
@ControllerAdvice
@Order(-998)
@Slf4j
public class ExceptionAdvice {

    /**
     * 异常拦截
     *
     * @param e Any RuntimeException
     * @return ResponseEntity<TransactionStatus></>
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponseDTO> handleBaseException(Exception e) {
        return fetchGnericResponse(e);
    }

    /**
     * 异常处理
     *
     * @param e Any RuntimeException
     * @return TransactionStatus
     */
    private ResponseEntity<GenericResponseDTO> fetchGnericResponse(Exception e) {
        GenericResponseDTO response = new GenericResponseDTO();
        TransactionStatus transactionStatus = new TransactionStatus();

        if (e instanceof BaseException) {
            return getBaseException((BaseException) e, response, transactionStatus);
        }

        return getRuntimeException(e, response, transactionStatus);
    }

    /**
     * 获取运行时异常的错误信息
     *
     * @param e                 Exception
     * @param response          GenericResponseDTO
     * @param transactionStatus TransactionStatus
     * @return GenericResponseDTO
     */
    private ResponseEntity<GenericResponseDTO> getRuntimeException(Exception e, GenericResponseDTO response, TransactionStatus transactionStatus) {
        transactionStatus.setError(ErrorCode.ERROR_MSG_999999, ErrorCode.ERROR_CODE_999999, ErrorCode.EXCEPTION);
        transactionStatus.setMemo(e.getMessage());
        response.setTransactionStatus(transactionStatus);
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 获取自定义异常的错误信息
     *
     * @param e                 Exception
     * @param response          GenericResponseDTO
     * @param transactionStatus TransactionStatus
     * @return GenericResponseDTO
     */
    private ResponseEntity<GenericResponseDTO> getBaseException(BaseException e, GenericResponseDTO response, TransactionStatus transactionStatus) {
        transactionStatus.setError(e.getErrorDesc(), e.getErrorCode());
        response.setTransactionStatus(transactionStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
