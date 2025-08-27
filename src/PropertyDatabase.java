import java.util.*;

public class PropertyDatabase implements Searchable<Property> {
// Add necessary attributes
private List<Property> properties;
private Property property;

// Constructor
   public PropertyDatabase() {
        this.properties = new ArrayList<>();
        this.property = new Property();
   }

// necessary methods

public void addProperty(Property property) {
        properties.add(property);
    }


public List<Property> search(String keyword) {
    // Search properties by location or rating
    List<Property> results = new ArrayList<>();
    for (Property property : properties) {
        if (property.getLocation().toLowerCase().contains(keyword.toLowerCase()) ||
            String.valueOf(property.getRating()).toLowerCase().contains(keyword.toLowerCase()) ||
            property.getType().toLowerCase().contains(keyword.toLowerCase())) {
            results.add(property);
        }
    }
    return results;
}


}
