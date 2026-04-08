import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String roomType;
    String roomId;
    boolean isCancelled;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.reservationId, r);
    }

    public Reservation getReservation(String id) {
        return bookings.get(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        if (r.isCancelled) {
            System.out.println("Cancellation Failed: Already cancelled");
            return;
        }

        // Step 1: Push roomId to stack (rollback tracking)
        rollbackStack.push(r.roomId);

        // Step 2: Restore inventory
        inventory.incrementRoom(r.roomType);

        // Step 3: Mark cancelled
        r.isCancelled = true;

        System.out.println("Cancellation successful for ID: " + reservationId);
        System.out.println("Rolled back Room ID: " + rollbackStack.peek());
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES101", "Deluxe", "D1");
        Reservation r2 = new Reservation("RES102", "Standard", "S1");

        history.addReservation(r1);
        history.addReservation(r2);

        System.out.println("Before Cancellation:");
        inventory.displayInventory();

        // Perform cancellation
        cancelService.cancelBooking("RES101", history, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        // Try invalid cancellation
        cancelService.cancelBooking("RES999", history, inventory);
        cancelService.cancelBooking("RES101", history, inventory);
    }
}