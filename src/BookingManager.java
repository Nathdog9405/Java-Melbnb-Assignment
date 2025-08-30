import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingManager {
    private final PropertyDatabase propertyDatabase;

    public BookingManager(PropertyDatabase propertyDatabase) {
        this.propertyDatabase = propertyDatabase;
    }
    // Lists available properties from user's prompt
    public void propertySelection(String prompt) {
        List<Property> searchResults = new ArrayList<>();
        Scanner choice = new Scanner(System.in);
        boolean selecting = true;
        // Add search results to the list
        searchResults.addAll(propertyDatabase.search(prompt));
        while (selecting) {
            if (searchResults.isEmpty()) {
                System.out.println("No properties found matching the criteria.");
                selecting = false;
            } else {
                System.out.println("""
                ------------------------
                Select from list:
                ------------------------
                """);
                for (int i = 0; i < searchResults.size(); i++) {
                    System.out.println((i + 1) + ": " + searchResults.get(i).getProperty());
                }
                System.out.println((searchResults.size() + 1) + ": Go to main menu");
                int selection;

                while (true) {
                    System.out.print("Choose an option: ");
                    if (choice.hasNextInt()) {
                        selection = choice.nextInt();
                        if (selection >= 1 && selection <= searchResults.size() + 1) {
                            break;
                        } else {
                            System.out.println("Invalid option. Please select a valid number from the list.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        choice.next(); // Clear invalid input
                    }
                }
                if (selection == searchResults.size() + 1) {
                    selecting = false;
                } else if (selection > 0 && selection <= searchResults.size()) {
                    // Gets the property selected
                    Property selectedProperty = searchResults.get(selection - 1);
                    bookingProcess(selectedProperty);
                    selecting = false;
                }
            }
        }
    }
    // Starts the booking process
    public void bookingProcess(Property selectedProperty) {
        Scanner choice = new Scanner(System.in);
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
        // Calculate total price
        totalDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double price = selectedProperty.getPricePerNight() * totalDays;
        double serviceFee = selectedProperty.getServiceFee() * totalDays;
        double totalPrice;
        // Show Property Details
        System.out.println("------------------------------------------------------------");
        System.out.println("Show Property Details");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-22s %s%n", "Property:", selectedProperty.getProperty());
        System.out.printf("%-22s %s%n", "", "hosted by " + selectedProperty.getHost());
        System.out.printf("%-22s %s%n", "Type of place:", selectedProperty.getType());
        System.out.printf("%-22s %s%n", "Location:", selectedProperty.getLocation());
        System.out.printf("%-22s %.2f%n", "Rating:", selectedProperty.getRating());
        System.out.printf("%-22s %s%n", "Description:", selectedProperty.getDescription());
        System.out.printf("%-22s %d%n", "Max number of guests:", 1);
        System.out.printf("%-22s $%.2f ($%.2f * %d night(s))%n", "Price:", price, selectedProperty.getPricePerNight(), totalDays);

        if (totalDays >= 7) {
            double discountPrice = selectedProperty.getPricePerNight() * (100 - selectedProperty.getWeeklyDiscount()) / 100.0;
            double totalDiscountPrice = discountPrice * totalDays;
            totalPrice = totalDiscountPrice + serviceFee + selectedProperty.getCleaningFee();
            System.out.printf("%-22s $%.2f ($%.2f * %d night(s))%n", "Discounted Price:", totalDiscountPrice, discountPrice, totalDays);
            System.out.printf("%-22s $%.2f ($%.2f * %d night(s))%n", "Service Fee:", serviceFee, selectedProperty.getServiceFee(), totalDays);
            System.out.printf("%-22s $%.2f%n", "Cleaning Fee:", selectedProperty.getCleaningFee());
            System.out.printf("%-22s $%.2f%n", "Total Price:", totalPrice);
        } else {
            totalPrice = price + serviceFee + selectedProperty.getCleaningFee();
            System.out.printf("%-22s $%.2f ($%.2f * %d night(s))%n", "Service Fee:", serviceFee, selectedProperty.getServiceFee(), totalDays);
            System.out.printf("%-22s $%.2f%n", "Cleaning Fee:", selectedProperty.getCleaningFee());
            System.out.printf("%-22s $%.2f%n", "Total Price:", totalPrice);
        }
        processPayment(totalPrice, selectedProperty, checkInDate, checkOutDate);
    }
    // Processes the user payment
    public void processPayment(double totalPrice, Property selectedProperty, LocalDate checkInDate, LocalDate checkOutDate) {
        Scanner choice = new Scanner(System.in);
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
        //Gets customer details
        if (reserveChoice.equalsIgnoreCase("Y")) {

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
                    // Makes sure there are no more guests than the property allows or at least 1 guest
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
            // Show confirmation message
            if (paymentChoice.equalsIgnoreCase("Y")) {
                System.out.println(
                        """
                --------------------------------------------------------------------------------
                    Congratulations! Your trip is booked. A receipt has been sent to your email.
                    Details of your trip are shown below.
                    Your host will contact you before your trip. Enjoy your stay!  
                -------------------------------------------------------------------------------- 
                        """);
                System.out.printf("%-22s %s %s%n", "Name", fname, lname);
                System.out.printf("%-22s %s%n", "Email:", email);
                System.out.printf("%-22s %s%n", "Your stay:", selectedProperty.getProperty());
                System.out.printf("%-22s %d guest(s)%n", "Who's coming:", numGuests);
                System.out.printf("%-22s %s%n", "Check-in date:", checkInDate);
                System.out.printf("%-22s %s%n", "Check-out date:", checkOutDate);
                System.out.printf("%-22s $%.2f%n", "Total Payment:", totalPrice);
                System.out.println("Thank you for choosing Melbnb!");
                System.out.println("Please press Enter to continue...");
                choice.nextLine();
                choice.nextLine(); // Wait for user to press Enter
            } else {
                System.out.println("Payment cancelled, returning to Main Menu.");
            }
        } else {
            System.out.println("Reservation cancelled, returning to Main Menu.");
        }
    }
}
