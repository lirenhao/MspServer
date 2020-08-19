package com.yada.ssp.msp.auth.model;

public class Res {

    public Res(String uri, ResOps[] ops) {
        this.uri = uri;
        this.ops = ops;
    }

    private String uri;

    private ResOps[] ops;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ResOps[] getOps() {
        return ops;
    }

    public void setOps(ResOps[] ops) {
        this.ops = ops;
    }
}

