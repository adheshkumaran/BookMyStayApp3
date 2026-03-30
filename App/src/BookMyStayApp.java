// Version 4.1 (Refactored)

import java.util.*;

// Abstract Room Class
abstract class Room {
    private String roomType;
    private int beds;
    private double size;
    private double price;

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

// Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 150.0, 2000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250.0, 3500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500.0, 8000.0);
    }
}

// Inventory (Same as Use Case 3)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example: unavailable
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// 🔥 New Search Service (READ-ONLY)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== AVAILABLE ROOMS =====\n");

        boolean found = false;

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check → show only available rooms
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }
}

// Main Class
 class UC4RoomSearch {

    public static void main(String[] args) {

        // Room objects (Domain)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory (State)
        RoomInventory inventory = new RoomInventory();

        // Search Service (Read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Guest performs search
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch Completed. System state unchanged.");
    }
}