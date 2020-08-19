package com.yada.ssp.msp.auth.model;

public enum ResOps {
    READ("GET"),
    CREATE("POST"),
    UPDATE("PUT"),
    DELETE("DELETE");

    private String method;

    ResOps(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static ResOps get(String value) {
        switch (value) {
            case "GET":
                return ResOps.READ;
            case "POST":
                return ResOps.CREATE;
            case "PUT":
                return ResOps.UPDATE;
            case "DELETE":
                return ResOps.DELETE;
            default:
                return null;
        }
    }
}
