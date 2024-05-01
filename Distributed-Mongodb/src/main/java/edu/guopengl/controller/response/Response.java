package edu.guopengl.controller.response;

import java.io.Serializable;

public class Response implements Serializable {

    private boolean err;
    private String message;
    private Object data;

    public Response() {
        this.err = false;
        this.message = null;
        this.data = null;
    }

    public Response(boolean err, String message, Object data) {
        this.err = err;
        this.message = message;
        this.data = data;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
