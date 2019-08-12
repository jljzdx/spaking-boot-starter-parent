package com.spaking.boot.starter.cas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SsoUser implements Serializable {
    private static final long serialVersionUID = 42L;

    private Integer userId;

    private String userName;

    private String realName;

    private String version;

    private int expireMinite;

    private long expireFreshTime;

}
