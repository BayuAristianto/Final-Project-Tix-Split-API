package utils;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utils {

    public static String generateRandomPhoneNumber() {
        return "08" + (long) (Math.random() * 1000000000L);
    }

    public static String generateRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    // Generate random title tanpa prefix
    public static String generateRandomTitle() {
        // Ambil 8 karakter pertama dari UUID
        return "Title-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateRandomCategory() {
        // Ambil 8 karakter pertama dari UUID
        return "Category-" + UUID.randomUUID().toString().substring(0, 8);
    }
    public static String generateRandomDescription() {
        // Ambil 8 karakter pertama dari UUID
        return "Description-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static int generateRandomId() {
        // Ambil random id
        java.util.Random random = new java.util.Random();
        return 100 + random.nextInt(999);
    }

    public static String generateInvalidRandomTitle() {
        // Ambil 8 karakter pertama dari UUID
        return "Title-" + RandomStringUtils.randomAlphanumeric(550);
    }

    public static String getDateAfterSevenDays() {

        // Get today's date
        LocalDate currentDate = LocalDate.now();

        // Add 7 days to today's date
        LocalDate dateAfter7Days = currentDate.plusDays(7);

        // Define the output format (yyyy-MM-dd)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert the date to a String with the chosen format
        return dateAfter7Days.format(dateFormatter);
    }

    public static String getDateAfterFourDays() {

        // Get today's date
        LocalDate currentDate = LocalDate.now();

        // Add 7 days to today's date
        LocalDate dateAfter7Days = currentDate.plusDays(4);

        // Define the output format (yyyy-MM-dd)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert the date to a String with the chosen format
        return dateAfter7Days.format(dateFormatter);
    }

}
