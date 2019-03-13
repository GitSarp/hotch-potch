package com.example.nettydemo.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 12:07 PM 2019/3/13
 * @ Description：自定义消息
 * @ Modified By：
 */
@Data
public class CustomProtocol implements Serializable {

    private static final long serialVersionUID = 4671171056588401542L;
    private long id ;
    private String content ;

    public CustomProtocol(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public CustomProtocol() {
    }
}
