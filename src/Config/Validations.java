
package Config;

import java.util.Scanner;

public class Validations {
    
    public static int AgeValidations(int legal){
        Scanner sc = new Scanner(System.in);
        
        int age = sc.nextInt();
        
        while(age <= legal){
            System.out.println("Invalid age: the individual is a"
                    + " juvenile and does not meet the minimum legal requirement of 17 years.");
            System.out.print("Enter Again: ");
            age = sc.nextInt();
        }   
        return age;
    }
    
    public static int ChoiceValidation(int first, int last){
        Scanner sc = new Scanner(System.in);
        
        int ans = sc.nextInt();
        
        while(ans < first || ans > last){
            System.out.println("Invalid Choice: ");
            System.out.print("Enter Agaiin: ");
            ans = sc.nextInt();
        }
        return ans;
    }
}
