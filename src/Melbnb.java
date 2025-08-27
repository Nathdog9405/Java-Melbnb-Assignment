
import java.io.*;
import java.util.*;
public class Melbnb {

    //Attributes
    private PropertyDatabase propertyDatabase;
    

    public Melbnb() {
        // Creating array list for properties
        propertyDatabase = new PropertyDatabase();

    }


    // Populate the database with properties from the CSV file
    public void populateDatabase(){
        // Path to CSV file
        String csvFile = "src/Melbnb.csv";

        // Storing properties
        List<List<String>> data = new ArrayList<>();
        
        // Using scanner to read csv file
        try (Scanner scanner = new Scanner(new File(csvFile))) {

            // Removes the header line
            if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Split the line into values
                String[] values = line.split(",");
                List<String> lineData = Arrays.asList(values);

                // Add line data to main list
                data.add(lineData);

            }

            //Add properties into propertyDatabase
            for (int i = 0; i < data.size(); i++) {
                List<String> row = data.get(i);
                //Creating the properties
                Property property = new Property(
                    row.get(0), 
                    row.get(1), 
                    row.get(2), 
                    row.get(3),
                    row.get(4),
                    Integer.parseInt(row.get(5)), 
                    Double.parseDouble(row.get(6)),
                    Double.parseDouble(row.get(7)), 
                    Double.parseDouble(row.get(8)),
                    Double.parseDouble(row.get(9)), 
                    Double.parseDouble(row.get(10))
                );
                propertyDatabase.addProperty(property);
            }




        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
        }


    }


    //Runs the application
    public void run() {
        boolean active = true;
        Scanner input = new Scanner(System.in);
        while (active) {
            System.out.println("""

                    ------------------------
                    Welcome to Melbnb!
                    ------------------------
                    1: Search by location
                    2: Browse by type of place
                    3: Filter by rating
                    4: Quit
                    """);
            System.out.print("Choose an option: ");

            try {
                int option = input.nextInt();
                input.nextLine(); 
                switch (option) {
                    case 1:
                        // Search by location
                        System.out.print("Please provide a location: ");
                        String location = input.nextLine();

                        bookingCreation(location);
                            
                        break;

                    case 2:
                        // Browse by type of place
                        browsePlace();
                        break;

                    case 3:
                        //Filter by rating
                        System.out.print("Please provide a minimum rating: ");
                        double minRating = input.nextDouble();
                        bookingCreation(Double.toString(minRating));
                        break;

                    case 4:
                        active = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // Clears the invalid input
            }
        }
        input.close(); 
    }

    public void browsePlace() {
        boolean browsing = true;
        Scanner input = new Scanner(System.in);
        while (browsing) {
            System.out.println("""
                    Browse by type of place
                    ------------------------
                    1) Private room
                    2) Entire place
                    3) Shared room
                    4) Go to main menu
                    """);
            System.out.print("Choose an option: ");

            try {
                int type = input.nextInt();
                switch (type) {
                    case 1:
                        bookingCreation("Private room");
                       
                        break;

                    case 2:
                        bookingCreation("Entire place");
                        
                        break;

                    case 3:
                        bookingCreation("Shared room");
                        
                        break;

                    case 4:
                        // Return to main menu
                        browsing = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // Clear the invalid input
            }
        }
    }

    // Searches the property database based on the keyword
    public void bookingCreation(String prompt){
        List<Property> searchResults = new ArrayList<>();
        Scanner choice = new Scanner(System.in);
        boolean selecting = true;
        // Add search results to the list
        searchResults.addAll(propertyDatabase.search(prompt));
        while(selecting){
        if (searchResults.isEmpty()) {
            System.out.println("No properties found matching the criteria.");
            selecting = false;
        } else {
            
            System.out.println("""
                ------------------------
                Select from list:
                ------------------------
                """);
            for (Property property : searchResults) {
                System.out.println(searchResults.indexOf(property) + 1 + ": " + property.getProperty());
            }
            System.out.println(searchResults.size() + 1 + ": Go to main menu");
            System.out.print("Choose an option: ");
            int selection = choice.nextInt();
            if (selection == searchResults.size() + 1) {
                selecting = false;
            } else if (selection > 0 && selection <= searchResults.size()) {
                Property selectedProperty = searchResults.get(selection - 1);
                System.out.println("You have selected: " + selectedProperty.getProperty());
                System.out.println("Booking confirmed! Enjoy your stay at " + selectedProperty.getProperty() + ".");
                selecting = false;
            } else {
                System.out.println("Invalid option. Please try again.");


        }
    }
    }

}
}