
package Main;

import Config.Validations;
import static Main.Main.*;
import java.util.Scanner;
import static Main.UniversalDataDisplay.*;
import Config.conf;

public class La {
    public int Authorities(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        boolean run = true;
        while (run) {

            System.out.println("\n--- AUTHORITIES MENU ---");
            System.out.println("1: View Record");
            System.out.println("2: Search");
            System.out.println("3: Log Out");
            System.out.print("Enter Choice: ");

            int choice = Validations.ChoiceValidation(1, 3);
            
            switch (choice) {
                case 1: 
                    
                    System.out.println("Do you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("Choice: ");
                    int ans2 = Validations.ChoiceValidation(1,2);
                    
                    ShowInmateData(); 
                    
                    System.out.println("Enter Inmate ID: ");
                    int id = Validations.IntegerValidation();
                    
                    while(true){
                        if(Validations.ExitTrigger(id)){
                            return 0;
                        }

                    String qry = "SELECT * FROM record WHERE r_id = ?";
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
                case 2:
                    Search.SearchPanel(3);
                    break;
                    
                case 3:
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
