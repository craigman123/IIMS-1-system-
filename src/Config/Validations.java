
package Config;

import java.util.Scanner;
import java.time.LocalDate;

public class Validations {
    
    public static int AgeValidations(int legal) {
        Scanner sc = new Scanner(System.in);
        int age = 0;
        boolean valid = false;

        while (!valid) {
            String input = sc.next();
            
            try {
                age = Integer.parseInt(input);
                
                if (age > legal) {
                    valid = true;
                } else {
                    System.out.println("Invalid age: the individual is a juvenile and does not meet the minimum legal requirement of " + legal + " years.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer for age: ");
            }
        }
        return age;
    }
    
    public static int ChoiceValidation(int first, int last) {
        Scanner sc = new Scanner(System.in);
        int ans = 0;
        boolean valid = false;
        
        while (!valid) {

            String input = sc.next();
            
            try {
                ans = Integer.parseInt(input);
                if (ans >= first && ans <= last) {
                    valid = true;
                } else {
                    System.out.print("Invalid Choice: Enter Again (between " + first + " and " + last + "): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("That's not a valid integer! Please Enter Again: ");
            }
        }
        return ans;
    }
    
    public static String Date(){
        Object cd = LocalDate.now();
        String date = cd.toString();
        
        return date;
    }
    
    public static int IntegerValidation() {
        Scanner sc = new Scanner(System.in);
        
        int userInput = 0;
        boolean valid = false;
        
        while (!valid) {
            String input = sc.next();
            
            try {
                userInput = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.print("That's not a valid integer! Please try again: ");
            }
        }
        return userInput;
    }
    
    public static String StringValidation() {
        Scanner sc = new Scanner(System.in);
        
        String userInput = "";
        boolean valid = false;
        
        while (!valid) {
            userInput = sc.next();
            
            try {
                if (userInput.equals("0") || userInput.matches("[a-zA-Z ]+")) {
                    valid = true;
                }else{
                    throw new IllegalArgumentException("Input contains invalid characters. Only letters and spaces are allowed.");
                }
                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                
                System.out.print("Enter Again: ");
            }
        }
        return userInput;
    }
}
