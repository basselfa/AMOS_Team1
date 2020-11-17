package com.amos.p1.backend.from_backend_to_provider;

public class Employee {

    private String status;
    private Data data;
    private String message;

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
