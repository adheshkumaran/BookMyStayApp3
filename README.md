import java.util.LinkedList;
import java.util.Queue;

// Represents a booking request
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

// Queue manager (FIFO)
class BookingRequestQueue {
private Queue<Reservation> queue = new LinkedList<>();

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Added -> " + reservation);
    }

    // Show all requests
    public void displayRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        System.out.println("\n--- Booking Queue (FIFO) ---");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    // View next request
    public void peekRequest() {
        System.out.println("\nNext Request: " + queue.peek());
    }
}

public class UseCase5BookingRequestQueue {
public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating incoming requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));

        // Display queue
        bookingQueue.displayRequests();

        // Peek next request
        bookingQueue.peekRequest();
    }
}