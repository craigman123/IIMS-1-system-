
package Main;

import Config.Validations;
import static Main.Main.*;
import java.util.Scanner;

public class La {
    public void Authorities(){
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {

            System.out.println("\n--- AUTHORITIES MENU ---");
            System.out.println("1: View Record");
            System.out.println("2: Log Out");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            while(choice < 1 || choice > 2){
                System.out.println("Invalid Choice: ");
                choice = Validations.ChoiceValidation(1,2);
            }
            
            switch (choice) {
                case 1: 
                    
                    System.out.println("Do you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("Choice: ");
                    int ans2 = Validations.ChoiceValidation(1,2);
                    
                    viewInmateInformation(); 
                    
                    System.out.println("Enter Inmate ID: ");
                    int id = Validations.IntegerValidation();
                    
                    if(ans2 == 1){
                        viewInmateRecord(id);
                    } 
                    
                    break;
                case 2:
                    System.out.println("Logging Out. . .  ");
                    Session.endSession();
                    run = false;
                   break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
    }
}
