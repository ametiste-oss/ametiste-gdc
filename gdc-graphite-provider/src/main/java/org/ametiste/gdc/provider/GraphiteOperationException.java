package org.ametiste.gdc.provider;

/**
 * Signals that during operation with Graphite some errors occurred.
 */
public class GraphiteOperationException extends RuntimeException {
    public GraphiteOperationException(String message) {
        super(message);
    }

    public GraphiteOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
