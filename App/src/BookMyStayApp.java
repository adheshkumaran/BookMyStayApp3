// Version 2.1 (Refactored)

// Abstract Class
abstract class Room {
    private String roomType;
    private int beds;
    private double size;
    private double price;

    // Constructor
    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Getters
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

    // Display Method
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 150.0, 2000.0);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250.0, 3500.0);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500.0, 8000.0);
    }
}

// Main Class (IMPORTANT: File name must match this)
class UseCase2RoomInitialization {

    public static void main(String[] args) {

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("===== HOTEL ROOM DETAILS =====\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailability);
        System.out.println("----------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailability);
        System.out.println("----------------------------");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailability);
        System.out.println("----------------------------");

        System.out.println("\nApplication Terminated.");
    }
}