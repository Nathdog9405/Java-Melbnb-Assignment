import java.io.*;

public class Property implements Serializable {

    // Attributes
    private String property;
    private String location;
    private String description;
    private String type;
    private String host;
    private int maxGuests;
    private double rating;
    private double pricePerNight;
    private double serviceFee;
    private double cleaningFee;
    private double weeklyDiscount;

    // Constructor
    public Property() {}

    public Property(
        String property,
        String location,
        String description,
        String type,
        String host,
        int maxGuests,
        double rating,
        double pricePerNight,
        double serviceFee,
        double cleaningFee,
        double weeklyDiscount
    ) {
        this.property = property;
        this.location = location;
        this.description = description;
        this.type = type;
        this.host = host;
        this.maxGuests = maxGuests;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.serviceFee = serviceFee;
        this.cleaningFee = cleaningFee;
        this.weeklyDiscount = weeklyDiscount;
    }

    // Getters
    public String getProperty() {
        return property;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public double getRating() {
        return rating;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public double getWeeklyDiscount() {
        return weeklyDiscount;
    }
}
