//Hellen Wanyana
///Exam 1 CSC-242-01
// (All prime numbers up to 10,000,000,000) Write a program that finds all prime numbers
// up to 10,000,000,000. There are approximately 455,052,511 such prime numbers. Your program
// should meet the following requirements:
// ■ Your program should store the prime numbers in a binary data file, named PrimeNumbers.dat.
// When a new prime number is found, the number is appended to the file.
// ■ To find whether a new number is prime, your program should load the prime numbers from
// the file to an array of the long type of size 10000. If no number in the array is a divisor
// for the new number, continue to read the next 10000 prime numbers from the data file,
// until a divisor is found or all numbers in the file are read. If no divisor is found,
// the new number is prime.
// ■ Since this program takes a long time to finish, you should run it as a batch job from
// a UNIX machine. If the machine is shut down and rebooted, your program should resume by
// using the prime numbers stored in the binary data file rather than start over
// from scratch.

import java.io.*;

public class PrimeNumbers {
    public static void main(String[] args) {
        new PrimeNumbers().run();
    }

    // Name of the binary data file
    private final static String BINARY_DATA_FILE_NAME = "PrimeNumbers.dat";

    /**
     * constant size of the prime number array.
     */
    private final static int PRIME_NUMBER_ARRAY_SIZE = 10000;

    /**
     * Max number of prime.
     */
    private final static long MAX_NUMBER = 10_000_000L;

    /**
     * An array to store prime numbers.
     */
    private final long[] primeNumberArray = new long[PRIME_NUMBER_ARRAY_SIZE];

    /**
     * Current/initial number.
     */
    private long current_number = 2;

    public PrimeNumbers() {
        final File file = new File(BINARY_DATA_FILE_NAME);
        System.out.println("The absolute path of the binary data file: " + file.getAbsolutePath());

        if (!file.exists()) {
            // Create a binary data file if it does not exist
            this.appendPrimeNumberToBinaryFile(current_number);
        } else {
            // Read the binary data file, and update the current number
            try (
                    final FileInputStream fileInputStream = new FileInputStream(BINARY_DATA_FILE_NAME);
                    final DataInputStream dataInputStream = new DataInputStream(fileInputStream)
            ) {
                // Read long numbers from the binary file
                while (dataInputStream.available() >= Long.BYTES) {
                    current_number = dataInputStream.readLong();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        current_number++;
    }

    /**
     * Runs the program. Continues to check numbers one by one; if a number is a prime number,
     * appends it to the binary file.
     */
    public void run() {
        while (current_number <= MAX_NUMBER) {
            if (isPrimeNumber(current_number)) {
                appendPrimeNumberToBinaryFile(current_number);
                System.out.println(current_number);
            }

            current_number++;
        }
    }

    /**
     * Checks if a number is a prime number.
     * @param number Number to check.
     */
    public boolean isPrimeNumber(long number) {
        try (
                final FileInputStream fileInputStream = new FileInputStream(BINARY_DATA_FILE_NAME);
                final DataInputStream dataInputStream = new DataInputStream(fileInputStream)
        ) {
            while (dataInputStream.available() >= Long.BYTES) {
                // Read (at most) 10000 long numbers
                int numberRead = -1;
                for (int i = 0; i < PRIME_NUMBER_ARRAY_SIZE; i++) {
                    if (dataInputStream.available() < Long.BYTES) {
                        numberRead = i;
                        break;
                    }
                    primeNumberArray[i] = dataInputStream.readLong();
                    //System.out.println(NumberRead);
                }
                if (numberRead == -1){
                    numberRead = PRIME_NUMBER_ARRAY_SIZE;
                }

                // Check if one of these 10000 numbers can divide the number
                for (int i = 0; i < numberRead; i++) {
                    if (number % primeNumberArray[i] == 0) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Appends a prime number to a binary file.
     * @param primeNumber Prime number to append.
     */
    public void appendPrimeNumberToBinaryFile(long primeNumber) {
        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(BINARY_DATA_FILE_NAME, true);
                final DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)
        ) {
            dataOutputStream.writeLong(primeNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
