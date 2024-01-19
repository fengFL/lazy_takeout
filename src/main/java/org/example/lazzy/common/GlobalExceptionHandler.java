package org.example.lazzy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

// the class that uses RestController annotation will be managed by this class
@ControllerAdvice(annotations = {RestController.class}) // the same as @RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  exception handler for SQLIntegrityConstraintViolationException
     * @param ex
     * @return
     */

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> sqlExceptionHandler(SQLIntegrityConstraintViolationException ex){

        String errorMsg = ex.getMessage();
        log.error(errorMsg);
        if(errorMsg.contains("Duplicate entry")){
            String[] tokens = errorMsg.split("\\s"); // split by space
            return Result.error(tokens[2] + " already exists");
        }
        return  Result.error("unknown error");
    }

    /**
     *  exception handler for CustomException
     * @param ex
     * @return
     */

    @ExceptionHandler(CustomException.class)
    public Result<String> customExceptionHandler(CustomException ex){
        String errorMsg = ex.getMessage();
        log.error(errorMsg);
        return  Result.error(errorMsg);
    }

    /**
     * deal with all other Exceptions
     *
     */
//    public Result<String> handleExceptions(Exception ex){
//        String message = ex.getMessage();
//        log.error(message);
//        return Result.error(message);
//    }
}
