package com.spaking.boot.starter.core.model;

import lombok.Data;

@Data
public class PageModel {
    //当前页（第一页：1）
    private Long current;

    //一页显示多少条
    private Long size;

    //总记录数
    private Long total;

    //排序字段
    private String field;

    //排序方向
    private String order;
}
