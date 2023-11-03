package Classes;


/*import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;*/
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.FileReader;



public class Guide {
    private String guideId;
    private String name;
    private String contactInformation;
    private List<Tour> tours;

    public Guide(String guideId, String name, String contactInformation) {
        this.guideId = guideId;
        this.name = name;
        this.contactInformation = contactInformation;
        this.tours = new ArrayList<>();
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public void updateTour(Tour tour, String newLocation, Date newDateTime, double newPrice) {
        for (Tour t : tours) {
            if (t.equals(tour)) {
                t.updateLocation(newLocation);
                t.updateDateTime(newDateTime);
                t.updatePrice(newPrice);
                // Annen oppdateringslogikk om nødvendig
            }
        }
    }

    public List<Tour> getTours() {
        return tours;
    }

    public List<Booking> viewBookings() {
        List<Booking> guideBookings = new ArrayList<>();
        for (Tour t : tours) {
            guideBookings.addAll(t.getBookings());
        }
        return guideBookings;
    }

    public void approveBooking(Tour tour, Booking booking) {
        for (Tour t : tours) {
            if (t.equals(tour)) {
                t.approveBooking(booking);
            }
        }
    }

 /*   public void rejectBooking(Tour tour, Booking booking) {
        for (Tour t : tours) {
            if (t.equals(tour)) {
                t.rejectBooking(booking);
            }
        }
    }
    public void saveToJson(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Guide>>() {}.getType();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Guide loadFromJson(String filename) {
        try {
            Gson gson = new Gson();
            Guide guide = gson.fromJson(new FileReader(filename), Guide.class);
            return guide;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
