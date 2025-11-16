
package Main;

import Config.Validations;
import Config.conf;
import static Main.Main.*;
import java.util.Scanner;
import static Main.UniversalDataDisplay.*;

public class Bh {
        
    public int Behavioral(){
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        conf config = new conf();
        while (run) {

            System.out.println("\n--- BEHAVIORAL MENU ---");
            System.out.println("1: Update Inmate");
            System.out.println("2: View Records");
            System.out.println("3: Search");
            System.out.println("4: Log Out");
            System.out.print("Enter Choice: ");

            int choice = Validations.ChoiceValidation(1, 4);
            
            switch (choice) {
                case 1: UpdateInmate(); break;
                case 2:
                    
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

                    String qry = "SELECT * FROM record WHERE i_id = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, id);

                    if (result.isEmpty()) {
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
                case 3:
                    Search.SearchPanel(1);
                    break;
                case 4:
                    
                    System.out.println("Logging Out. . .  ");
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
