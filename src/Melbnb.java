
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