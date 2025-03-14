package org.puclab.exceptions;

/**
 * Exceção para representar erros de regras de negócio da aplicação.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}