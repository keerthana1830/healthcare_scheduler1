package org.example;

public interface AppointmentManager {
    void bookAppointment();
    void rescheduleAppointment(int appointmentId);
    void cancelAppointment(int appointmentId);
}

