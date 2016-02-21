package org.kaleta.lolstats.backend.service;

/**
 * Created by Stanislav Kaleta on 14.01.2015.
 */
public class ServiceFailureException extends RuntimeException {

    public ServiceFailureException() {
        super();
    }

    public ServiceFailureException(String msg) {
        super(msg);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
