package org.legion.unity.common.ex;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {}

    public PermissionDeniedException(String msg) {
        super(msg);
    }
}
