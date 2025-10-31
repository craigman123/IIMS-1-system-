
package Main;

import Config.Validations;
import static Main.Main.*;
import static Main.Manage_User.ManageUsers;
import java.util.Scanner;

public class Ho {
    
    public void Higher(){
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {

            System.out.println("\n--- HIGHER MENU ---");
            System.out.println("1: Add Inmate");
            System.out.println("2: Update Inmate");
            System.out.println("3: Delete Inmate");
            System.out.println("4: View Record");
            System.out.println("5: Manage Users");
            System.out.println("6: Log Out");
            System.out.print("Enter Choice: ");

            int choice = Validations.ChoiceValidation(1,6);

            switch (choice) {
                case 1: AddInmate(); break;
                case 2: UpdateInmate(); break;
                case 3: deleteInmateRecord(); break;
                case 4: 
                    
                    System.out.println("\nDo you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("\nChoice: ");
                    int ans2 = Validations.ChoiceValidation(1,2);
                    
                    viewInmateInformation(); 
                 
                    if(ans2 == 1){
                        
                        System.out.println("Enter Inmate ID: ");
                        int id = Validations.IntegerValidation();
                        viewInmateRecord(id);
                        
                    }

                    break;
                case 5:
                    ManageUsers();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    Session.endSession();
                    run = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
    }
}
