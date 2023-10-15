package Classes;

import java.util.ArrayList;

public class Turist  {
    private ArrayList<String> tourList;
    private ArrayList<String> favoriteList;

    public ArrayList<String> getTourList() {
        return tourList;
    }

    public void setTourList(ArrayList<String> tourList) {
        this.tourList = tourList;
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
}
