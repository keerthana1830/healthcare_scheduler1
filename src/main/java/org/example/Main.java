package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppointmentService appointmentService = new AppointmentService();
        FileHandler fileHandler = new FileHandler();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHealthcare Appointment System");
            System.out.println("1. Book Appointment");
            System.out.println("2. Reschedule Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View Appointments");
            System.out.println("5. View All Appointments");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Book Appointment
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    if (!appointmentService.isValidDoctorId(doctorId)) {
                        System.out.println("Doctor ID not found. Adding a new doctor.");
                        System.out.print("Enter Doctor Name: ");
                        String doctorName = scanner.nextLine();
                        System.out.print("Enter Doctor Specialization: ");
                        String specialization = scanner.nextLine();
                        System.out.print("Enter Available Hours: ");
                        String availableHours = scanner.nextLine();

                        appointmentService.addNewDoctor(doctorId, doctorName, specialization, availableHours);
                    }

                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    if (!appointmentService.isValidPatientId(patientId)) {
                        System.out.println("Patient ID not found. Adding a new patient.");
                        System.out.print("Enter Patient Name: ");
                        String patientName = scanner.nextLine();
                        System.out.print("Enter Patient Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Patient Phone: ");
                        String phone = scanner.nextLine();

                        appointmentService.addNewPatient(patientId, patientName, email, phone);
                    }

                    System.out.print("Enter Appointment Date (YYYY-MM-DD HH:MM): ");
                    String appointmentDate = scanner.nextLine();

                    System.out.print("Enter Appointment Type: ");
                    String appointmentType = scanner.nextLine();

                    // Book the appointment
                    appointmentService.bookAppointment(doctorId, patientId, appointmentDate, appointmentType);

                    // Save appointment details to file
                    fileHandler.saveAppointmentToFile(doctorId, patientId, appointmentDate, appointmentType);
                    break;

                case 2:
                    // Reschedule Appointment
                    System.out.print("Enter Appointment ID to reschedule: ");
                    int appointmentIdToReschedule = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    System.out.print("Enter New Appointment Date (YYYY-MM-DD HH:MM): ");
                    String newAppointmentDate = scanner.nextLine();

                    // Reschedule the appointment
                    appointmentService.rescheduleAppointment(appointmentIdToReschedule, newAppointmentDate);
                    break;

                case 3:
                    // Cancel Appointment
                    System.out.print("Enter Appointment ID to cancel: ");
                    int appointmentIdToCancel = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    // Cancel the appointment
                    appointmentService.cancelAppointment(appointmentIdToCancel);
                    break;

                case 4:
                    // View Appointments
                    System.out.println("View appointments for (1) Doctor or (2) Patient?");
                    int viewChoice = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer

                    if (viewChoice == 1) {
                        System.out.print("Enter Doctor ID: ");
                        int doctorIdForView = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                        appointmentService.viewAppointments(doctorIdForView, 0);  // 0 to view all for the doctor
                    } else if (viewChoice == 2) {
                        System.out.print("Enter Patient ID: ");
                        int patientIdForView = scanner.nextInt();
                        scanner.nextLine(); // Clear the buffer
                        appointmentService.viewAppointments(0, patientIdForView);  // 0 to view all for the patient
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                case 5:
                    // View All Appointments
                    appointmentService.viewAllAppointments();
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
