import java.util.*;

// Add-On Service class
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// Manager class
class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double getTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> list = serviceMap.get(reservationId);

        if (list != null) {
            for (AddOnService s : list) {
                total += s.getCost();
            }
        }
        return total;
    }
}

// Main class (IMPORTANT: file name must match this)
public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();
        String reservationId = "RES101";

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService food = new AddOnService("Breakfast", 300);
        AddOnService cab = new AddOnService("Cab Pickup", 500);

        // Add services
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, food);
        manager.addService(reservationId, cab);


        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Selected Services:");

        for (AddOnService s : manager.getServices(reservationId)) {
            System.out.println("- " + s);
        }

        // Total cost
        System.out.println("Total Add-On Cost: ₹" + manager.getTotalCost(reservationId));
    }
}