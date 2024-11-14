package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppointmentService appointmentService = new AppointmentService();
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

            int choice = getValidIntInput(scanner);

            switch (choice) {
                case 1:
                    handleBookingAppointment(scanner, appointmentService);
                    break;
                case 2:
                    handleRescheduling(scanner, appointmentService);
                    break;
                case 3:
                    handleCancellation(scanner, appointmentService);
                    break;
                case 4:
                    handleViewingAppointments(scanner, appointmentService);
                    break;
                case 5:
                    appointmentService.viewAllAppointments();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static int getValidIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static void handleBookingAppointment(Scanner scanner, AppointmentService service) {
        System.out.print("Enter Doctor ID: ");
        int doctorId = getValidIntInput(scanner);
        System.out.print("Enter Patient ID: ");
        int patientId = getValidIntInput(scanner);
        System.out.print("Enter Appointment Date (YYYY-MM-DD HH:MM): ");
        String appointmentDate = scanner.nextLine();
        System.out.print("Enter Appointment Type: ");
        String appointmentType = scanner.nextLine();
        service.bookAppointment(doctorId, patientId, appointmentDate, appointmentType);
    }

    private static void handleRescheduling(Scanner scanner, AppointmentService service) {
        System.out.print("Enter Appointment ID to reschedule: ");
        int appointmentId = getValidIntInput(scanner);
        System.out.print("Enter New Appointment Date (YYYY-MM-DD HH:MM): ");
        String newDate = scanner.nextLine();
        service.rescheduleAppointment(appointmentId, newDate);
    }

    private static void handleCancellation(Scanner scanner, AppointmentService service) {
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentId = getValidIntInput(scanner);
        service.cancelAppointment(appointmentId);
    }

    private static void handleViewingAppointments(Scanner scanner, AppointmentService service) {
        System.out.print("Enter Doctor ID to view appointments (or 0 to skip): ");
        int doctorId = getValidIntInput(scanner);
        System.out.print("Enter Patient ID to view appointments (or 0 to skip): ");
        int patientId = getValidIntInput(scanner);
        service.viewAppointments(doctorId == 0 ? -1 : doctorId, patientId == 0 ? -1 : patientId);
    }
}
