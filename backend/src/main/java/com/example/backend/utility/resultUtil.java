package com.example.backend.utility;

public class resultUtil {
    public static <T> baseResult<T> success(T data) {
        return commonResult(1, 200, "success", data);
    }

    public static <T> baseResult<T> success(T data, String successMsg) {
        return commonResult(1, 200, successMsg , data);
    }
    
    public static <T> baseResult<T> error(String errorMsg) {
        return error(200, errorMsg);
    }
    
    public static <T> baseResult<T> error(Integer code, String errorMsg) {
        return commonResult(0, code, errorMsg, null);
    }

    private static <T> baseResult<T> commonResult(Integer status, Integer code, String errMsg, T data) {
        baseResult<T> result = new baseResult<>();
        result.setStatus(status);
        result.setCode(code);
        result.setMsg(errMsg);
        result.setData(data);
        return result;
    }
}
