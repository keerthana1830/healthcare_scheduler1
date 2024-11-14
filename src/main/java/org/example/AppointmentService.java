package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentService {
    private Connection connection;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public AppointmentService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcaresystem", "root", "root");
            loadDoctors();
            loadPatients();
            loadAppointments();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private void loadDoctors() {
        String query = "SELECT * FROM doctors";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String availableHours = rs.getString("available_hours");
                doctors.add(new Doctor(name, doctorId, specialization, availableHours));
            }
        } catch (SQLException e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        }
    }

    private void loadPatients() {
        String query = "SELECT * FROM patients";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                patients.add(new Patient(patientId, name, email, phone));
            }
        } catch (SQLException e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
    }

    private void loadAppointments() {
        String query = "SELECT * FROM appointments";
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int doctorId = rs.getInt("doctor_id");
                int patientId = rs.getInt("patient_id");
                String appointmentType = rs.getString("appointment_type");
                String status = rs.getString("status");
                appointments.add(new Appointment(appointmentId, doctorId, patientId, appointmentType, status));
            }
        } catch (SQLException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
    }

    public boolean isValidDoctorId(int doctorId) {
        return doctors.stream().anyMatch(d -> d.getDoctorId() == doctorId);
    }

    public boolean isValidPatientId(int patientId) {
        return patients.stream().anyMatch(p -> p.getPatientId() == patientId);
    }

    public void addNewDoctor(int doctorId, String name, String specialization, String availableHours) {
        Doctor newDoctor = new Doctor(name, doctorId, specialization, availableHours);
        doctors.add(newDoctor);
        String insertQuery = "INSERT INTO doctors (doctor_id, name, specialization, available_hours) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, doctorId);
            statement.setString(2, name);
            statement.setString(3, specialization);
            statement.setString(4, availableHours);
            statement.executeUpdate();
            System.out.println("New doctor added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding new doctor: " + e.getMessage());
        }
    }

    public void addNewPatient(int patientId, String name, String email, String phone) {
        Patient newPatient = new Patient(patientId, name, email, phone);
        patients.add(newPatient);
        String insertQuery = "INSERT INTO patients (patient_id, name, email, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, patientId);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.executeUpdate();
            System.out.println("New patient added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding new patient: " + e.getMessage());
        }
    }

    public void bookAppointment(int doctorId, int patientId, String appointmentDate, String appointmentType) {
        Appointment newAppointment = new Appointment(appointments.size() + 1, doctorId, patientId, appointmentType, "Scheduled");
        appointments.add(newAppointment);
        String insertQuery = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, appointment_type, status) VALUES (?, ?, ?, ?, 'Scheduled')";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            statement.setString(3, appointmentDate);
            statement.setString(4, appointmentType);
            statement.executeUpdate();
            System.out.println("Appointment booked successfully.");
        } catch (SQLException e) {
            System.out.println("Error booking appointment: " + e.getMessage());
        }
    }

    public void rescheduleAppointment(int appointmentId, String newDate) {
        appointments.stream()
                .filter(a -> a.getAppointmentId() == appointmentId)
                .findFirst()
                .ifPresent(a -> a.setStatus("Rescheduled"));
        String updateQuery = "UPDATE appointments SET appointment_date = ?, status = 'Rescheduled' WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, newDate);
            statement.setInt(2, appointmentId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Appointment rescheduled successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error rescheduling appointment: " + e.getMessage());
        }
    }

    public void cancelAppointment(int appointmentId) {
        appointments.removeIf(a -> a.getAppointmentId() == appointmentId);
        String deleteQuery = "DELETE FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, appointmentId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Appointment canceled successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error canceling appointment: " + e.getMessage());
        }
    }

    public void viewAppointments(int doctorId, int patientId) {
        appointments.stream()
                .filter(a -> a.getDoctorId() == doctorId || a.getPatientId() == patientId)
                .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentId() +
                        ", Doctor ID: " + a.getDoctorId() +
                        ", Patient ID: " + a.getPatientId() +
                        ", Type: " + a.getAppointmentType() +
                        ", Status: " + a.getStatus()));
    }

    public void viewAllAppointments() {
        appointments.forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentId() +
                ", Doctor ID: " + a.getDoctorId() +
                ", Patient ID: " + a.getPatientId() +
                ", Type: " + a.getAppointmentType() +
                ", Status: " + a.getStatus()));
    }

}
