package org.kaleta.lolstats.backend.manager;

/**
 * Created by Stanislav Kaleta on 11.02.2016.
 */
public class ManagerException extends Exception {

    public ManagerException() {
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }
}
