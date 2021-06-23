package com.bsahomework.bsagiphy.exception;

public class DownloadFileException extends RuntimeException {

    public DownloadFileException(String query, Throwable cause) {
        super("Could not download file by external query: " + query, cause);
    }
}
