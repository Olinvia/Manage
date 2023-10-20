package com.example.demo.common;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String msg;
    private Long total;
    private Object data;

    public static Result fail(){
        return result(400,"fail",0L,null);
    }

    public static Result success(){
        return result(200,"success",0L,null);
    }

    public static Result success(Long total, Object data){
        return result(200,"success",total,data);
    }

    public static Result result(int code, String msg, Long total, Object data){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }
}
