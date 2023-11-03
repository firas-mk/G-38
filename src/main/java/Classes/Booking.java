package Classes;
/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.io.FileReader;



public class Booking {
    private String bookingId;
    private String touristName;
    private String guideName;
    private String tourId;
    private boolean approved;

    public Booking(String bookingId, String touristName, String guideName, String tourId) {
        this.bookingId = bookingId;
        this.touristName = touristName;
        this.guideName = guideName;
        this.tourId = tourId;
        this.approved = false; // En bestilling er som standard ikke godkjent
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getTouristName() {
        return touristName;
    }

    public String getGuideName() {
        return guideName;
    }

    public String getTourId() {
        return tourId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void approve() {
        approved = true;
    }

    public void reject() {
        approved = false;
    }

  /*  // Metode for å lagre Booking-data til JSON-fil
    public void saveToJson(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Booking>>() {}.getType();
            gson.toJson(this, listType, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode for å lese Booking-data fra JSON-fil
    public static Booking loadFromJson(String filename) {
        try {
            Gson gson = new Gson();
            Booking booking = gson.fromJson(new FileReader(filename), Booking.class);
            return booking;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
