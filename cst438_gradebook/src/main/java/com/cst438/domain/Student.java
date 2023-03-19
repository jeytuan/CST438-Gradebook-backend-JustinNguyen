package com.cst438.domain;

public class Student {

    public int student_id;
    public String name;
    public String email;
    public String password;
    public String role;
    public int course_id;
    
    public Student() {
        super();
    }

    public Student(int student_id, String name, String email, String password, String role, int course_id) {
        super();
        this.student_id = student_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.course_id = course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "Student [student_id=" + student_id + ", name=" + name + ", email=" + email + ", password=" + password
                + ", role=" + role + ", course_id=" + course_id + "]";
    }
    
}
