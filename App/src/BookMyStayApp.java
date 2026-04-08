import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State (Inventory + Booking History)
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting fresh.");
        }
        return null;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Try loading previous state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Standard", 5);
            inventory.put("Deluxe", 3);
            inventory.put("Suite", 2);

            bookings = new ArrayList<>();

            System.out.println("Initialized new system state.");
        } else {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Recovered previous system state.");
        }

        // Simulate new booking
        Reservation r = new Reservation("RES" + (bookings.size() + 1),
                "Guest" + (bookings.size() + 1),
                "Deluxe");

        bookings.add(r);
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        System.out.println("\nNew Booking Added: " + r);

        // Display current state
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }

        System.out.println("\nBooking History:");
        for (Reservation res : bookings) {
            System.out.println(res);
        }

        // Save state before exit
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.save(newState);
    }
}