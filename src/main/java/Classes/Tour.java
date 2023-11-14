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
    private String tourNr;
    private String location;
    private Date dateTime;
    private double price;
    private String status;


    public Tour(String tourNr, String location, Date dateTime, double price, String status) {
        this.tourNr = tourNr;
        this.location = location;
        this.dateTime = dateTime;
        this.price = price;
        this.status = status;

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




    public void updateLocation(String newLocation) {
        this.location = newLocation;
    }

    public void updateDateTime(Date newDateTime) {
        this.dateTime = newDateTime;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }


}
