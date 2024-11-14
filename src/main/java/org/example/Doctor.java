package org.example;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String availableHours;

    public Doctor(String name, int doctorId, String specialization, String availableHours) {
        this.name = name;
        this.doctorId = doctorId;
        this.specialization = specialization;
        this.availableHours = availableHours;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }
}
