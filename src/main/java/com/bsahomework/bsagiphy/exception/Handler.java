package com.bsahomework.bsagiphy.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.MalformedURLException;
import java.util.NoSuchElementException;

@Log4j2
@ControllerAdvice
public class Handler {

    @ExceptionHandler(DownloadFileException.class)
    public ResponseEntity<Object> handleDownloadFileException(DownloadFileException exception) {
        log.error(exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<Object> handleMalformedURLException(MalformedURLException exception) {
        log.error(exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        log.error(exception);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FileOperationsException.class)
    public ResponseEntity<Object> handleFileOperationsException(FileOperationsException exception) {
        log.error(exception);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {
        log.error(exception);
        return ResponseEntity.notFound().build();
    }

}
