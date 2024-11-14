
package org.example;

import java.sql.*;
import java.util.Scanner;

public class AppointmentService {
    private Connection connection;

    public AppointmentService() {
        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcaresystem", "root", "root");
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // Check if Doctor ID is valid
    public boolean isValidDoctorId(int doctorId) {
        String query = "SELECT * FROM doctors WHERE doctor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking Doctor ID: " + e.getMessage());
            return false;
        }
    }

    // Check if Patient ID is valid
    public boolean isValidPatientId(int patientId) {
        String query = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking Patient ID: " + e.getMessage());
            return false;
        }
    }

    // Add new doctor to the database
    public void addNewDoctor(int doctorId, String name, String specialization, String availableHours) {
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

    // Add new patient to the database
    public void addNewPatient(int patientId, String name, String email, String phone) {
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

    // Method to book an appointment
    public void bookAppointment(int doctorId, int patientId, String appointmentDate, String appointmentType) {
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

    // Method to reschedule an appointment
    public void rescheduleAppointment(int appointmentId, String newDate) {
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

    // Method to cancel an appointment
    public void cancelAppointment(int appointmentId) {
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

    // Method to view appointment schedules for a specific doctor or patient
    public void viewAppointments(int doctorId, int patientId) {
        String query = "SELECT appointment_id, doctor_id, patient_id, appointment_date, appointment_type, status FROM appointments WHERE doctor_id = ? OR patient_id = ? ORDER BY appointment_date";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            ResultSet rs = statement.executeQuery();
            System.out.println("Appointment Schedules:");
            while (rs.next()) {
                System.out.println("Appointment ID: " + rs.getInt("appointment_id"));
                System.out.println("Doctor ID: " + rs.getInt("doctor_id"));
                System.out.println("Patient ID: " + rs.getInt("patient_id"));
                System.out.println("Date: " + rs.getString("appointment_date"));
                System.out.println("Type: " + rs.getString("appointment_type"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }
    }

    public void viewAllAppointments() {
        String query = "SELECT * FROM appointments";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            System.out.println("\nAll Appointments:");
            System.out.println("Appointment ID | Doctor ID | Patient ID | Appointment Date | Appointment Type | Status");

            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                int doctorId = rs.getInt("doctor_id");
                int patientId = rs.getInt("patient_id");
                String appointmentDate = rs.getString("appointment_date");
                String appointmentType = rs.getString("appointment_type");
                String status = rs.getString("status");

                System.out.println(appointmentId + " | " + doctorId + " | " + patientId + " | " + appointmentDate + " | " + appointmentType + " | " + status);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
        }
    }

}
