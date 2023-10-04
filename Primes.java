package Exam1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/*
 *  Patrick Gould
 *  CSC-242-01
 *  Exam One Part One
 * 
 *  This program uses the sieve of Eratosthenes to calculate prime numbers up to 10,000,000,000
 *  and saves the primes found to a binary data file called Primes.dat
 *  
 *  If the program is interrupted it will read in the Primes.dat file and
 *  continue finding primes starting from the largest prime previously found.
 */


public class Primes {
    
    public static final long NUMS_TO_SEARCH = 20000; // Search space
    public static long primes_found = 0;              // Number of primes found so far

    public static void main(String[] args) throws IOException {

        // Num of lines to skip
        long nums_to_skip = 0;

        // List of primes
        ArrayList<Long> primes = readData(nums_to_skip);

        // Starting number
        long current_candidate;

        // If no .dat file was found create a new arraylist and start from 2
        if (primes == null) {
            primes = new ArrayList<Long>();
            current_candidate = 2;
        }
        // Otherwise start searching from the largest prime found so far
        else {
            long[] file_info = getLargestPrime();
            current_candidate = file_info[0] + 1;
            primes_found = file_info[1];
        }

        // Notify user of starting position and number of primes found so far
        System.out.println("Starting search at " + current_candidate);
        System.out.println("Primes found so far: " + primes_found);
        
        while (current_candidate <= NUMS_TO_SEARCH) {
            // Reset skip counter
            nums_to_skip = 0;
            // Update prime list
            if (primes.size() > 0) {
                primes = readData(nums_to_skip);
            }
            // Notify user when the magnitude of search changes
            if (current_candidate % 1000 == 0) {
                System.out.println("Search has reached " + current_candidate);
            }
            // Prime flag
            boolean isPrime = true;
            if (!primes.isEmpty()) {
                // Loop through current list of primes
                for (int j = 0; primes.get(j) <= Math.sqrt(current_candidate); j++) {
                    // Test for primeness
                    if (current_candidate % primes.get(j) == 0) {
                        isPrime = false;
                        break;
                    }
                }
                // If the square root of the current candidate is greater than
                // the largest number in the primes arraylist then read in
                // 10,000 more numbers from the file

                if (Math.sqrt(current_candidate) > primes.get(primes.size() - 1)) {
                    while (Math.sqrt(current_candidate) > primes.get(primes.size() - 1)) {
                        // Notify user that condition was met
                        System.out.println("Reading in 10,000 more primes");
                        // Increment skip counter
                        nums_to_skip += 10_000;
                        // Read in 10,000 more primes
                        primes = readData(nums_to_skip);
                        
                        for (int j = 0; primes.get(j) <= Math.sqrt(current_candidate); j++) {
                            // Test for primeness
                            if (current_candidate % primes.get(j) == 0) {
                                isPrime = false;
                                break;
                            }
                        }
    
                    }
                }
               
            }
            if (isPrime) {
                // Add prime to file
                writeData(current_candidate);
                if (primes.size() == 0)
                    primes.add(current_candidate);
            }
            // Increment the current_candidate
            current_candidate++;
        }
        System.out.println("Search ended.");
        // Update number of primes found
        primes_found = getLargestPrime()[1];
        System.out.println("Num of primes found = " + primes_found);
    }

    /**
     * Appends a long value to the Primes.dat file
     * 
     * @param l value to be appended to the file
     */
    public static void writeData(long l) throws IOException {
        try {
            // Open binary data file
            DataOutputStream data_out = new DataOutputStream(new BufferedOutputStream (new FileOutputStream("Primes.dat", true)));
            // Write prime number to file
            data_out.writeLong(l);
            // Close OutputStream
            data_out.close();
            
            return;
        }
        catch(FileNotFoundException e) {
            System.out.println("Could not write to file.");
            return;
        }
    }

    /**
     * Reads values from the Primes.dat file
     * 
     * @param skip select a specific starting position to read from
     */
    public static ArrayList<Long> readData(long skip) throws IOException {
        try {
            // Open binary data file
            DataInputStream data_in = new DataInputStream(new FileInputStream("Primes.dat"));
            // Create arraylist to store primes
            ArrayList<Long> primes = new ArrayList<Long>();
            // Skip to the specified location
            if (skip > 0) {
                for (long i = 0; i < skip; i++) {
                    data_in.readLong();
                }
            }
            long nums_read = 0;
            // Read numbers from file
            while (data_in.available() > 0 && nums_read <= 10_000) { 
                long current_prime = data_in.readLong();
                primes.add(current_prime);
                nums_read++;
                //System.out.println("Prime \"" + current_prime + "\" read from file.");
            }
            data_in.close();

            return primes;


        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find Primes.dat");
            return null;
        }
        
    }

    /**
     * Gets the largest prime found so far as well as the number of primes found
     */
    public static long[] getLargestPrime() throws IOException {
        try {
            // Open binary data file
            DataInputStream data_in = new DataInputStream(new FileInputStream("Primes.dat"));
            // Current prime
            long current_prime = 0;
            // Number of primes
            long num_of_primes = 0;
            // Read numbers from file
            while (data_in.available() > 0) { 
                current_prime = data_in.readLong();
                num_of_primes++;
            }
            // Return the largest prime and current number of primes found
            long[] result = {current_prime, num_of_primes};
            data_in.close();
            return result;

        }
        catch(FileNotFoundException e) {
            System.out.println("Could not find Primes.dat");
            long[] result = {-1,-1};
            return result;
        }
    }
}
