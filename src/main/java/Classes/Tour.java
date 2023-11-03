package Classes;


import java.util.Date;
/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;


public class Tour {
    private String tourId;
    private String location;
    private Date dateTime;
    private double price;
    /* private int availableSeats;*/
    private List<Booking> bookings;

    public Tour(String tourId, String location, Date dateTime, double price, int status) {
        this.tourId = tourId;
        this.location = location;
        this.dateTime = dateTime;
        this.price = price;
        /*this.availableSeats = availableSeats;*/
        this.bookings = new ArrayList<>();
    }

    public String getLocation() {
        return location;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

   /* public int getAvailableSeats() {
        return availableSeats;
    }*/

    public List<Booking> getBookings() {
        return bookings;
    }

    public void updateLocation(String newLocation) {
        this.location = newLocation;
    }

    public void updateDateTime(Date newDateTime) {
        this.dateTime = newDateTime;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

   /* public void updateAvailableSeats(int amount) {
        this.availableSeats = amount;
    }*/

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void approveBooking(Booking booking) {
    }

 /*   public void rejectBooking(Booking booking) {
    }
    // Metode for å lagre Tour-data til JSON-fil
    public void saveToJson(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Tour>>() {}.getType();
            gson.toJson(this, listType, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode for å lese Tour-data fra JSON
    public static Tour loadFromJson(String filename) {
        try {
            Gson gson = new Gson();
            Tour tour = gson.fromJson(new FileReader(filename), Tour.class);
            return tour;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
