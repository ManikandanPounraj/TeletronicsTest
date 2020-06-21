package com.teletronics.ms.exception;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private String code;

    private String message;

    private String id;

    private String error;

}