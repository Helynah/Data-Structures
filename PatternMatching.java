
//Hellen M Wanyana
/* *(Pattern matching) Write a program that prompts the user to enter 
*two strings and tests whether the second string is a substring of the first
*string. (Don’t use the indexOf method in the String class.) Analyze the time 
*complexity of your algorithm.*/
import java.util.Scanner;

public class PatternMatching {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

     
            //Input string 1
            System.out.println("\nEnter a string s1: ");
            String s1 = input.nextLine();

            //Input string 2
            System.out.println("Enter a string s2: ");
            String s2 = input.nextLine();

            int index = isSubstring(s1, s2);

            if (index != -1) {
                System.out.println("Matched at index " + index + ".");
            } else {
                System.out.println("There is no match");
            }

       input.close();

    }

    // Function to check if string 2 is a substring of string 1
    private static int isSubstring(String string1, String string2) {
        int string1Length = string1.length();
        int string2Length = string2.length();

        for (int i = 0; i <= string1Length - string2Length; i++) {
            int j;
            for (j = 0; j < string2Length; j++) {
                if (string1.charAt(i + j) != string2.charAt(j)) {
                    break;
                }
            }
            //return the index at which the match is found
            if (j == string2Length) {
                return i; 
            }
        }
        return -1; // return -1 when no match  is found
    }

}
