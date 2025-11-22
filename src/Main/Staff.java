
package Main;

import Config.Validations;
import static Main.Main.*;
import java.util.Scanner;
import static Main.UniversalDataDisplay.*;
import Config.conf;

public class Staff {
    
    public int Staff(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        
        boolean run = true;
        while (run) {
            System.out.println("\n--- STAFF MENU ---");
            System.out.println("1: Add Inmate");
            System.out.println("2: Update Inmate");
            System.out.println("3: View Record");
            System.out.println("4: Search");
            System.out.println("5: Log Out");
            System.out.print("Enter Choice: ");

            int choice = Validations.ChoiceValidation(1, 5);
            while(choice < 1 || choice > 5){
                System.out.print("Invalid Choice: ");
                choice = Validations.ChoiceValidation(1,5);
            }

            switch (choice) {
                case 1: AddInmate(); break;
                case 2: UpdateInmate(); break;
                case 3: 
                    
                    System.out.println("Do you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("Choice: ");
                    int ans2 = Validations.ChoiceValidation(1, 2);
                    
                    while(ans2 == 0){
                        System.out.print("Exit not allowed:");
                        ans2 = Validations.ChoiceValidation(1, 2);
                }
                    
                    viewInmateInformation(); 
                    
                    System.out.println("Enter Inmate ID: ");
                    int id = Validations.IntegerValidation();
                    
                    while(true){
                        if(Validations.ExitTrigger(id)){
                            return 0;
                        }

                    String qry = "SELECT * FROM inmate WHERE i_id = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, id);

                    if (!result.isEmpty()) {
                            System.out.println("\n ----- ID NOT FOUND ----- ");
                            System.out.print("Enter Again: ");
                            id = Validations.IntegerValidation();

                    }else{
                        break;
                        }
                    } 
                    
                    if(ans2 == 1){
                        viewInmateRecord(id);
                    }  
                    break;
                case 4:
                    Search.SearchPanel(4);
                    break;
                case 5:
                    System.out.println("Logging out. . . ");
                    Session.endSession();
                    run = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
        return 0;
    }
}
