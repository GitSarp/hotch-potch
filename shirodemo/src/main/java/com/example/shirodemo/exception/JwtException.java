package com.example.shirodemo.exception;

import com.example.shirodemo.vo.JwtResultMap;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/12/21 2:00 PM
 */
@RestControllerAdvice
public class JwtException {
    @Autowired
    private JwtResultMap resultMap;

    /**捕获与Shiro相关的异常*/
    @ExceptionHandler(ShiroException.class)
    public JwtResultMap handle401(){
        return resultMap.fail().code(401).message("您没有权限访问!");
    }

    /**捕获其他异常*/
    @ExceptionHandler(Exception.class)
    public JwtResultMap globalException(HttpServletRequest request, Throwable e){
        e.printStackTrace();
        return resultMap
                .fail()
                .code(getStatus(request).value())
                .message("访问出错,无法访问："+e.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("java.servlet.error.status_code");
        if (null == statusCode){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
