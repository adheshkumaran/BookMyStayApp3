import java.util.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryService() {
        // Initial room availability
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
        }
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> requestQueue;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocations;
    private InventoryService inventoryService;
    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventoryService) {
        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + roomCounter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    // Process booking requests
    public void processBookings() {
        System.out.println("\n--- Processing Booking Requests ---");

        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing: " + reservation);

            if (!inventoryService.isAvailable(roomType)) {
                System.out.println("❌ No rooms available for " + roomType);
                continue;
            }

            // Generate unique ID
            String roomId = generateRoomId(roomType);

            // Ensure uniqueness
            allocatedRoomIds.add(roomId);

            // Map allocation
            roomAllocations.putIfAbsent(roomType, new HashSet<>());
            roomAllocations.get(roomType).add(roomId);

            // Update inventory
            inventoryService.decrementRoom(roomType);

            // Confirm booking
            System.out.println("✅ Booking Confirmed!");
            System.out.println("Assigned Room ID: " + roomId);
        }
    }

    public void displayAllocations() {
        System.out.println("\n--- Room Allocations ---");
        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Step 1: Create queue (FIFO)
        Queue<Reservation> requestQueue = new LinkedList<>();

        requestQueue.offer(new Reservation("Alice", "Single"));
        requestQueue.offer(new Reservation("Bob", "Double"));
        requestQueue.offer(new Reservation("Charlie", "Suite"));
        requestQueue.offer(new Reservation("David", "Single"));
        requestQueue.offer(new Reservation("Eve", "Single")); // Should fail (limited inventory)

        // Step 2: Initialize services
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(requestQueue, inventoryService);

        // Step 3: Display inventory before allocation
        inventoryService.displayInventory();

        // Step 4: Process bookings
        bookingService.processBookings();

        // Step 5: Show results
        bookingService.displayAllocations();
        inventoryService.displayInventory();
    }
}