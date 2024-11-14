package org.example;

public class Patient {
    private int patientId;
    private String name;
    private String email;
    private String phone;

    public Patient(int patientId, String name, String email, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}