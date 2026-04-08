import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double amount;

    public Reservation(String reservationId, String guestName, String roomType, double amount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.amount = amount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Amount: ₹" + amount;
    }
}

// Booking History class
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getAmount();
        }

        System.out.println("\n--- Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Adhesh", "Deluxe", 2500);
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite", 4000);
        Reservation r3 = new Reservation("RES103", "Priya", "Standard", 1800);

        // Add to history (in order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views data
        List<Reservation> allReservations = history.getAllReservations();

        reportService.displayAllBookings(allReservations);
        reportService.generateSummary(allReservations);
    }
}