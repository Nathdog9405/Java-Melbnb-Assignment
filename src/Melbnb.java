import java.io.*;
import java.util.*;

public class Melbnb {

    //Attributes
    private FileInputStream fis;
    private ObjectInputStream ois;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private List<Property> properties;
    private PropertyDatabase propertyDatabase;

    public Melbnb() {
        // Creating array list for properties
        properties = new ArrayList<Property>();
        propertyDatabase = new PropertyDatabase();
    }

    public void populateDatabase(){
        // Dummy data for now
        Property [] properties = new Property[]{
            new Property("Beach House", "Miami", "A beautiful beach house.", 
            "Entire place", "John Doe", 6, 4.5, 200.0, 20.0, 50.0, 10.0),
            new Property("City Apartment", "New York", "A cozy apartment in the city center.", 
            "Private room", "Jane Smith", 2, 4.8, 150.0, 15.0, 30.0, 5.0)
        };

        for (Property property : properties) {
            propertyDatabase.addProperty(property);
        }

    }


    //Runs the application
    public void run() {
        boolean active = true;
        Scanner input = new Scanner(System.in);
        while (active) {
            System.out.println("""
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

                        try{
                            propertyDatabase.search(location);
                            
                        } catch (Exception e) {
                            System.out.println("Error occurred while searching: " + e.getMessage());
                        }
                        break;

                    case 2:
                        // Browse by type of place
                        browsePlace();
                        break;

                    case 3:
                        //Filter by rating
                        System.out.print("Please provide a minimum rating: ");
                        double minRating = input.nextDouble();
                        // Implement filter logic here
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
                        // Private room
                        System.out.println("You selected a Private room.");
                        break;

                    case 2:
                        // Entire place
                        System.out.println("You selected an Entire place.");
                        break;

                    case 3:
                        // Shared room
                        System.out.println("You selected a Shared room.");
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

}