package com.amos.p1.backend.from_backend_to_provider;

public class Data {

    private int id;
    private String employee_name;
    private int employee_salary;
    private int employee_age;
    private String profile_image;

    public int getId() {
        return id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public int getEmployee_salary() {
        return employee_salary;
    }

    public int getEmployee_age() {
        return employee_age;
    }

    public String getProfile_image() {
        return profile_image;
    }


    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", employee_name='" + employee_name + '\'' +
                ", employee_salary=" + employee_salary +
                ", employee_age=" + employee_age +
                ", profile_image='" + profile_image + '\'' +
                '}';
    }
}
