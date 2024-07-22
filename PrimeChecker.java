import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class PrimeChecker {
    public static void main(String[] args) {
        // Initialise variables for use later
        boolean invalid = true; // To check if input is valid
        int sequence = 0; // To store sequence entered by user
        ArrayList<Integer> numbers = new ArrayList<Integer>(); // To store numbers from sequence
        ArrayList<Integer> prime_numbers = new ArrayList<Integer>(); // To store prime numbers from sequence
        LinkedHashSet<Integer> cache = new LinkedHashSet<Integer>(); // To store unique prime numbers from sequence and from text file
        Scanner sc = new Scanner(System.in); // Scanner object for reading input
        
        // Read text file and fill cache if text file is found
        File file = new File("PrimeNumbers.txt");
        Scanner sc_file = new Scanner(System.in);
        try {
            sc_file = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("No existing prime numbers file found");
        }
        while (sc_file.hasNextLine())
            cache.add(Integer.valueOf(sc_file.nextLine())); // Add text file data to cache
            sc_file.close();
        
        // Get user to enter username and number sequence
        System.out.println("Welcome to Prime Checker!");
        System.out.println(" ");
        System.out.println("Enter a username");
        String username = sc.nextLine(); // Get user to enter username as string
        System.out.println(" ");
        System.out.println("Hello " + username + ", please enter a number");
        while (invalid) { // While sequence is not a valid number
            String input = sc.nextLine(); // Get user to enter sequence as string
            try {
                sequence = Integer.valueOf(input); // Convert sequence from string to integer
                invalid = false;
            } catch (NumberFormatException e){ // Catch error if letters/spaces/special characters included in sequence
                System.out.println("Number is invalid");
                System.out.println("Ensure the number contains no letters, spaces or special characters");
            }
        }
        sc.close();
        System.out.println("Number is valid");

        // Split sequence into all possible numbers
        System.out.println(" ");
        System.out.println("Printing all possible numbers in sequence");
        String number = String.valueOf(sequence); // Convert sequence to string
        // Check if the number can be split or if it is just one number
        if (number.length() == 1) {
            int digit = Integer.valueOf(number);
            numbers.add(digit);
        } else {
            // Seperate sequence and store into an arraylist
        for(int i = 1; i < number.length(); i++) {
            for (int j = 0 ; j < number.length(); j++) {
                if (j + i <= number.length()) {
                    String digit_str = number.substring(j, j + i); // Seperate sequence into all possible numbers
                    int digit_int = Integer.valueOf(digit_str); // Convert numbers into integer
                    numbers.add(digit_int);
                    System.out.println(digit_int);
                }
            }
        }
        }
        // Print and store prime numbers from sequence
        System.out.println(" ");
        System.out.println("Printing all prime numbers in sequence");
        for (int i = 0 ; i < numbers.size(); i++) {
            // Checks first if number is in cache then checks if number is prime
            if (cache.contains(numbers.get(i))) {
                System.out.println(numbers.get(i) + " (already in cache)"); // Print prime numbers
            } else if (isPrime(numbers.get(i))) {
                System.out.println(numbers.get(i)); // Print prime numbers
                prime_numbers.add(numbers.get(i)); // Add prime numbers to cache
            }
        }
        System.out.println("Saving prime numbers to cache");
        for (int i = 0 ; i < prime_numbers.size(); i++) {
            cache.add(prime_numbers.get(i));
        }
        System.out.println("Saving cache to text file");
        writeToFile(cache); // Write cache to file
    }

    // Function to check if the given number is prime or not, will return either true or false
    static boolean isPrime(int num) {
        if (num <= 1) { // Prime can't be less than or equal to 1
            return false; 
        }
        for (int j = 2; j <= num / 2; j++)
        {
            if ((num % j) == 0) { // Prime can't have remainders
                return false;
            }
        }
        return true;
    }

    private static void writeToFile(LinkedHashSet<Integer> cache) {
        File file = new File("PrimeNumbers.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            System.out.println("Printing cache and saving to text file");
            Object[] numbers = cache.toArray();
            for (int i = 0 ; i < cache.size(); i++) {
                String number = String.valueOf(numbers[i]);
                fr.write(number + "\n"); // Write number to file
            }
        } catch (IOException e) { // Catch any errors
            System.out.println("Issue writing to file");
        } finally {
            //close file
            try {
                fr.close();
            } catch (IOException e) { // Catch any errors
            }
        }
    }
}