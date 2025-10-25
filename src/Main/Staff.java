
package Main;

import Config.Validations;
import static Main.Main.AddInmate;
import static Main.Main.UpdateInmate;
import static Main.Main.viewInmateInformation;
import static Main.Main.viewInmateRecord;
import java.util.Scanner;

public class Staff {
    
    public void Staff(){
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("\n--- STAFF MENU ---");
            System.out.println("1: Add Inmate");
            System.out.println("2: Update Inmate");
            System.out.println("3: View Record");
            System.out.println("4: Log Out");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            while(choice < 1 || choice > 4){
                System.out.print("Invalid Choice: ");
                choice = Validations.ChoiceValidation(1,4);
            }

            switch (choice) {
                case 1: AddInmate(); break;
                case 2: UpdateInmate(); break;
                case 3: 
                    
                    System.out.println("Do you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("Choice: ");
                    int ans2 = Validations.ChoiceValidation(1,2);
                    
                    viewInmateInformation(); 
                    
                    System.out.println("Enter Inmate ID: ");
                    int id = sc.nextInt();
                    
                    if(ans2 == 1){
                        viewInmateRecord(id);
                    }  
                    break;
                case 4:
                    System.out.println("Logging out...");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
    }
}
