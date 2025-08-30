
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Melbnb {

    //Attributes
    private final PropertyDatabase propertyDatabase;
    
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
                    case 1 -> {
                        // Search by location
                        System.out.print("Please provide a location: ");
                        String location = input.nextLine();
                        bookingCreation(location);
                    }

                    case 2 -> browsePlace();

                    case 3 -> {
                        //Filter by rating
                        System.out.print("Please provide a minimum rating: ");
                        double minRating = input.nextDouble();
                        bookingCreation(Double.toString(minRating));
                    }

                    case 4 -> active = false;

                    default -> System.out.println("Invalid option. Please try again.");
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
                    case 1 -> {
                        bookingCreation("Private room");
                        browsing = false;
                    }
                    case 2 -> {
                        bookingCreation("Entire place");
                        browsing = false;
                    }
                    case 3 -> {
                        bookingCreation("Shared room");
                        browsing = false;
                    }
                    case 4 -> browsing = false; // Return to main menu
                    default -> System.out.println("Invalid option. Please try again.");
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
                LocalDate today = LocalDate.now();
                LocalDate checkInDate;
                LocalDate checkOutDate;
                int totalDays;

                // Get valid check-in date
                System.out.println("------------------------");
                System.out.println("Provide Dates");
                System.out.println("------------------------");
                while (true) {
                    System.out.print("Enter check-in date (DD-MM-YYYY): ");
                    String checkInStr = choice.next();
                    try {
                        checkInDate = LocalDate.parse(checkInStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        if (!checkInDate.isAfter(today)) {
                            System.out.println("Check-in date must be after today.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                }

                // Get valid check-out date
                while (true) {
                    System.out.print("Enter check-out date (DD-MM-YYYY): ");
                    String checkOutStr = choice.next();
                    try {
                        checkOutDate = LocalDate.parse(checkOutStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        if (!checkOutDate.isAfter(checkInDate)) {
                            System.out.println("Check-out date must be after check-in date.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                }

                totalDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                double price = selectedProperty.getPricePerNight() * totalDays;
                double serviceFee = selectedProperty.getServiceFee() * totalDays;
                double totalPrice;
                System.out.println("------------------------");
                System.out.println("Show Property Details");
                System.out.println("------------------------");
                System.out.println("Property: " + selectedProperty.getProperty());
                System.out.println("Type of place: " + selectedProperty.getType());
                System.out.println("Location: " + selectedProperty.getLocation());
                System.out.printf("Rating: %.2f%n", selectedProperty.getRating());
                System.out.println("Description: " + selectedProperty.getDescription());
                System.out.println("Max number of guests: " + selectedProperty.getMaxGuests());
                System.out.printf("Price: $%.2f ($%.2f * %d night(s))%n", price, selectedProperty.getPricePerNight(), totalDays);
                if (totalDays >= 7) {
                    double discountPrice = selectedProperty.getPricePerNight() * (100 - selectedProperty.getWeeklyDiscount()) / 100; // Apply discount
                    double totalDiscountPrice = discountPrice * totalDays;
                    totalPrice = totalDiscountPrice + serviceFee + selectedProperty.getCleaningFee();
                    System.out.printf("Discounted Price: $%.2f%n ($%.2f * %d night(s))%n" , totalDiscountPrice, discountPrice, totalDays);
                    System.out.printf("Service Fee: $%.2f ($%.2f * %d night(s))%n", serviceFee, selectedProperty.getServiceFee(), totalDays);
                    System.out.printf("Cleaning Fee: $%.2f%n", selectedProperty.getCleaningFee());
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                }
                else{
                    totalPrice = price + serviceFee + selectedProperty.getCleaningFee();
                    System.out.printf("Service Fee: $%.2f ($%.2f * %d night(s))%n", serviceFee, selectedProperty.getServiceFee(), totalDays);
                    System.out.printf("Cleaning Fee: $%.2f%n", selectedProperty.getCleaningFee());
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                }

                String reserveChoice;
                String paymentChoice;
                while (true) {
                    System.out.print("Would you like to reserve this property? (Y/N): ");
                    reserveChoice = choice.next();
                    if (reserveChoice.equalsIgnoreCase("Y") || reserveChoice.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    }
                }
                if (reserveChoice.equalsIgnoreCase("Y")) {
                    // Reserve the property
                    System.out.println("------------------------");
                    System.out.println("Provide Personal Details");
                    System.out.println("------------------------");
                    System.out.print("Please provide your given name: ");
                    String fname = choice.next();
                    System.out.print("Please provide your last name: ");
                    String lname = choice.next();
                    System.out.print("Please provide your email: ");
                    String email = choice.next();
                    int numGuests;
                    while (true) {
                        System.out.print("Please provide number of guests: ");
                        try {
                            numGuests = choice.nextInt();
                            if (numGuests > 0 && numGuests <= selectedProperty.getMaxGuests()) {
                                break;
                            } else {
                                System.out.println("The max amount of guests for the property is " + selectedProperty.getMaxGuests() + ". Please choose vaild number of guests.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            choice.nextLine(); // Clear invalid input
                        }
                    }
                    while (true) {
                    System.out.print("Confirm Payment (Y/N): ");
                    paymentChoice = choice.next();
                    if (paymentChoice.equalsIgnoreCase("Y") || paymentChoice.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    }
                }
                if (paymentChoice.equalsIgnoreCase("Y")) {
                    System.out.println(
                    """
                    --------------------------------------------------------------------------------
                        Congratulations! Your trip is booked. A receipt has been sent to your email.
                        Details of your trip are shown below.
                        Your host will contact you before your trip. Enjoy your stay!  
                    -------------------------------------------------------------------------------- 
                            """);
                    System.out.println("Name: " + fname + " " + lname);
                    System.out.println("Email: " + email);
                    System.out.println("Your stay: " + selectedProperty.getProperty());
                    System.out.println("Who's coming: " + numGuests + " guest(s)");
                    System.out.println("Check-in date: " + checkInDate);
                    System.out.println("Check-out date: " + checkOutDate);
                    System.out.printf("Total Payment: $%.2f%n", totalPrice);
                    System.out.println("Thank you for choosing Melbnb!");
                    System.out.println("Please press <Enter> to continue...");
                    choice.nextLine(); // Consume newline
                    choice.nextLine(); // Wait for user to press Enter
                } else {
                    System.out.println("Payment cancelled, returning to Main Menu.");
                }
                } else {
                    System.out.println("Reservation cancelled, returning to Main Menu.");
                }

                selecting = false;
            } 
        }
    }
    }
}



