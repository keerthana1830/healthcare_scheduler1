package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static final String FILE_PATH = "appointments.txt";

    // Method to save appointment details to a file
    public void saveAppointmentToFile(int doctorId, int patientId, String appointmentDate, String appointmentType) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) { // Append mode
            writer.write("Doctor ID: " + doctorId + ", Patient ID: " + patientId +
                    ", Date: " + appointmentDate + ", Type: " + appointmentType + "\n");
            System.out.println("Appointment details saved to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
