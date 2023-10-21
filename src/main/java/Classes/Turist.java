package Classes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

import java.util.ArrayList;

public class Turist  {
    private String touristId;
    private String name;
    private String contactInformation;
    private List<Booking> bookings;
    private ArrayList<String> favoriteList;

    public String getTouristId() {
        return touristId;
    }

    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public List<Booking> getBookings() {
        return bookings;   /* _????????????????????*/
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public ArrayList<String> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(ArrayList<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public static String searchTour(){
        return "Hei";
    }
    public void saveToJson(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Turist>>() {}.getType();
            gson.toJson(this, listType, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Turist loadFromJson(String filename) {
        try {
            Gson gson = new Gson();
            Turist turist = gson.fromJson(new FileReader(filename), Turist.class);
            return turist;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
