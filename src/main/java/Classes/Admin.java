package Classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;


public class Admin {
    private String adminId;
    private String name;
    private String contactInformation;

    public Admin(String adminId, String name, String contactInformation) {
        this.adminId = adminId;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void saveToJson(String filename) {  /* ?????????????*/
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Admin>>() {}.getType();
            gson.toJson(this, listType, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  public static Admin loadFromJson(String filename) {
        try {
            Gson gson = new Gson();
            Admin admin = gson.fromJson(new FileReader(filename), Admin.class);
            return admin;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
