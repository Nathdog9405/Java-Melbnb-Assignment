import java.util.*;

public class PropertyDatabase implements Searchable<Property> {
// Add necessary attributes
private final List<Property> properties;

// Constructor
   public PropertyDatabase() {
        this.properties = new ArrayList<>();
   }

// necessary methods

public void addProperty(Property property) {
        properties.add(property);
    }


@Override
public List<Property> search(String keyword) {
    // Search properties by location or rating
    List<Property> results = new ArrayList<>();
     
    for (Property property : properties) {
        boolean matches = property.getLocation().toLowerCase().contains(keyword.toLowerCase())
                || property.getType().toLowerCase().contains(keyword.toLowerCase());
        // Check if keyword is a number for rating comparison
        try {
            double keywordAsDouble = Double.parseDouble(keyword);
            if (property.getRating() > keywordAsDouble) {
                matches = true;
            }
        } catch (NumberFormatException e) {
            // Ignore, keyword is not a number
        }
        if (matches) {
            results.add(property);
        }
    }
    return results;
}


}
