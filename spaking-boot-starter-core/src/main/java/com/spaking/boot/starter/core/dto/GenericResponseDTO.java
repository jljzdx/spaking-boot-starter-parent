package com.spaking.boot.starter.core.dto;

import com.spaking.boot.starter.core.model.TransactionStatus;

import java.io.Serializable;

public class GenericResponseDTO implements Serializable {

    private static final long serialVersionUID = -8254804917556647284L;

    private TransactionStatus transactionStatus;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


}
