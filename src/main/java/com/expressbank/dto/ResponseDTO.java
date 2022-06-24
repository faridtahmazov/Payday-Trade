package com.expressbank.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseDTO {

    private HttpStatus httpStatus;
    private String msg;
    private Object obj;

    private ResponseDTO(){}

    public static ResponseDTO of(Object obj){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setObj(obj);

        return responseDTO;
    }

    public static ResponseDTO of(HttpStatus httpStatus, String msg){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setHttpStatus(httpStatus);
        responseDTO.setMsg(msg);

        return responseDTO;
    }

    public static ResponseDTO of(HttpStatus httpStatus, Object obj, String msg){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setHttpStatus(httpStatus);
        responseDTO.setMsg(msg);
        responseDTO.setObj(obj);

        return responseDTO;
    }

    public static ResponseDTO of(HttpStatus httpStatus, Object obj){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setHttpStatus(httpStatus);
        responseDTO.setObj(obj);

        return responseDTO;
    }
}