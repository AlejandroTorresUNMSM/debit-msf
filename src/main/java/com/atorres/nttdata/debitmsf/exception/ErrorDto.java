package com.atorres.nttdata.debitmsf.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
    /**.
     * HttpStatus error
     */
    private HttpStatus httpStatus;
    /**.
     * Mensaje de error
     */
    private String message;
}
