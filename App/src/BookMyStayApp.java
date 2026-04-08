import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator Class
class BookingValidator {

    private static final List<String> VALID_ROOM_TYPES =
            Arrays.asList("Standard", "Deluxe", "Suite");

    public static void validate(String roomType, int roomsRequested, int availableRooms)
            throws InvalidBookingException {

        // Validate room type
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate room count
        if (roomsRequested <= 0) {
            throw new InvalidBookingException("Rooms requested must be greater than 0");
        }

        // Check availability
        if (roomsRequested > availableRooms) {
            throw new InvalidBookingException("Not enough rooms available");
        }
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 5);
        inventory.put("Deluxe", 3);
        inventory.put("Suite", 2);
    }

    public int getAvailableRooms(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType, int count) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - count);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Test inputs (you can change these)
        String roomType = "Deluxe";
        int roomsRequested = 2;

        try {
            System.out.println("Attempting booking...");

            int available = inventory.getAvailableRooms(roomType);

            // Validation (Fail-Fast)
            BookingValidator.validate(roomType, roomsRequested, available);

            // Only executed if valid
            inventory.bookRoom(roomType, roomsRequested);

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // System continues safely
        inventory.displayInventory();
    }
}