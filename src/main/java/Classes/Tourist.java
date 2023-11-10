package Classes;


public class Tourist  {
    private String touristId;
    private String name;
    private String contactInformation;

    public Tourist(String touristId, String name, String contactInformation) {
        this.touristId = touristId;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    public String getTouristId() {
        return touristId;
    }

    public void setTouristId(String touristId) {
        this.touristId = touristId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
