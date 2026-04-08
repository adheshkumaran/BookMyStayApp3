import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (Thread-Safe)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    // Critical Section
    public synchronized boolean bookRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " booked " + roomType);

            inventory.put(roomType, available - 1);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + roomType);
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// Shared Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll(); // removes safely
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // synchronized queue access
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break; // no more requests
            }

            // simulate processing delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // critical section (inventory)
            inventory.bookRoom(request.roomType);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple booking requests
        queue.addRequest(new BookingRequest("A", "Deluxe"));
        queue.addRequest(new BookingRequest("B", "Deluxe"));
        queue.addRequest(new BookingRequest("C", "Deluxe")); // extra (should fail)

        queue.addRequest(new BookingRequest("D", "Standard"));
        queue.addRequest(new BookingRequest("E", "Standard"));
        queue.addRequest(new BookingRequest("F", "Standard")); // extra (should fail)

        // Multiple threads (guests)
        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);
        BookingProcessor t3 = new BookingProcessor("Thread-3", queue, inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();
    }
}