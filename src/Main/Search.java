
package Main;

import Config.Validations;
import Config.conf;
import static Main.UniversalDataDisplay.ShowRecordData;

public class Search {
    
    static String[] Only = {
        "Search Inmate",       // index 0 
        "Search User Session", // index 1
        "Show History Logs",   // index 2
        "Show Type",           // index 3
        "Show Records",        // index 4
        "Show Users",          // index 5
        "Exit"                 // index 6
    };
    
    public static int SearchPanel(int show) {
    conf config = new conf();
    boolean run = true;
    boolean swift = true;

    int choice;

    switch (show) {
        case 1:
            while(swift){
            System.out.println("\n----- Search -----");
            System.out.println("1: " + Only[0]);
            System.out.println("2: " + Only[3]);
            System.out.println("3: " + Only[6]);
            System.out.println("=====================");
            System.out.print("Select: ");
            choice = Validations.ChoiceValidation(1, 3);

            switch (choice) {
                case 1:
                    System.out.println("→ " + Only[0]);
                    UniversalDataDisplay.SearchInmateData();
                    break;
                case 2:
                    System.out.println("2: " + Only[3]);
                    SearchType();
                    break;  
                case 3:
                    System.out.println("→ " + Only[6]);
                    System.out.println("Exiting . . .");
                    swift = false;
                    return 0;
                }
            }
            break;
        case 2:
            
            while(swift){
            System.out.println("\n----- Search -----");
            for (int i = 0; i < Only.length; i++) {
                System.out.println((i + 1) + ": " + Only[i]);
            }
            System.out.println("=====================");
            System.out.print("Select: ");
            choice = Validations.ChoiceValidation(1, Only.length);

            switch (choice) {
                case 1:
                    System.out.println("→ " + Only[0]);
                    UniversalDataDisplay.SearchInmateData();
                    break;
                case 2:
                    System.out.println("→ " + Only[1]);
                    UniversalDataDisplay.ShowUserSession();
                    break;
                case 3:
                    System.out.println("→ " + Only[2]);
                    UniversalDataDisplay.ShowLogs();
                    break;
                case 4:
                    System.out.println("→ " + Only[3]);
                    SearchType();
                    break;
                case 5:
                    System.out.println("→ " + Only[4]);
                    System.out.println("Do you want to show Records(y-1||n-2):");
                    System.out.print("Choice: ");
                    int ans = Validations.ChoiceValidation(1, 2);
                    
                    if (ans == 1){
                        UniversalDataDisplay.ShowInmateData();
                    }
                    
                    System.out.println("Enter Inmate ID: ");
                    int in_id = Validations.IntegerValidation();
                    
                    while(run){

                    String qry = "SELECT * FROM inmate WHERE i_id = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, in_id);

                    if (result.isEmpty()) {
                            System.out.println("\n ----- ID NOT FOUND ----- ");
                            System.out.print("Enter Again: ");
                            in_id = Validations.IntegerValidation();

                    }else{
                        run = false;
                        }
                    }
                    
                    ShowRecordData(in_id);                    
                    break;
                case 6:
                    System.out.println("→ " + Only[5]);
                    UniversalDataDisplay.ShowallUser();
                    break;
                case 7:
                    System.out.println("→ " + Only[6]);
                    System.out.println("Exiting . . .");
                    swift = false;
                    return 0;
                default:
                    System.out.println("Option not available.");
                    break;
                }
            }
            break;
        case 3:
            System.out.println("\n----- Search -----");
            choice = 1;

            switch (choice) {
                case 1:
                    System.out.println("→ " + Only[0]);
                    UniversalDataDisplay.SearchInmateData();
                    break;
            }
            break;
        case 4:
            
            while(swift){
            System.out.println("\n----- Search -----");
            System.out.println("1: " + Only[0]);
            System.out.println("2: " + Only[2]);
            System.out.println("3: " + Only[3]);
            System.out.println("4: " + Only[4]);
            System.out.println("5: " + Only[6]);
            System.out.println("=====================");
            System.out.print("Select: ");
            choice = Validations.ChoiceValidation(1, 5);

            switch (choice) {
                case 1:
                    System.out.println("→ " + Only[0]);
                    UniversalDataDisplay.SearchInmateData();
                    break;
                case 2:
                    System.out.println("→ " + Only[2]);
                    UniversalDataDisplay.ShowLogs();
                    break;
                case 3:
                    System.out.println("→ " + Only[3]);
                    SearchType();
                    break;
                case 4:
                    System.out.println("→ " + Only[4]);
                    System.out.println("Do you want to show Records(y-1||n-2):");
                    System.out.print("Choice: ");
                    int ans = Validations.ChoiceValidation(1, 2);
                    
                    if (ans == 1){
                        UniversalDataDisplay.ShowInmateData();
                    }
                    
                    System.out.println("Enter Inmate ID: ");
                    int in_id = Validations.IntegerValidation();
                    
                    while(run){

                    String qry = "SELECT * FROM inmate WHERE i_id = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, in_id);

                    if (result.isEmpty()) {
                            System.out.println("\n ----- ID NOT FOUND ----- ");
                            System.out.print("Enter Again: ");
                            in_id = Validations.IntegerValidation();

                    }else{
                        run = false;
                        }
                    }
                    
                    ShowRecordData(in_id); 
                    break;
                case 5:
                    System.out.println("→ " + Only[6]);
                    System.out.println("Exting  . . .");
                    swift = false;
                    return 0;
                }
            }
            break;
        default:
            System.out.println("\nInvalid option. Please choose between 1–5.");
            break;
        }
        return 0;
    }
    
    public static int SearchType(){
        
        System.out.println("\n----- Search by Inmate Type -----");
        System.out.println("1: Low");
        System.out.println("2: Medium");
        System.out.println("3: High");
        System.out.println("4: Maximum");
        System.out.println("5: Death Row");
        System.out.println("6: All ");
        System.out.println("7: Exit");
        System.out.println("=====================");
        System.out.print("Select: ");
        int choice = Validations.ChoiceValidation(1, 7);

            switch (choice) {
                case 1:
                    System.out.println("→ Low");
                    UniversalDataDisplay.ShowAllInmateType("Low Security");
                    break;
                case 2:
                    System.out.println("→ Medium");
                    UniversalDataDisplay.ShowAllInmateType("Medium Security");
                    break;
                case 3:
                    System.out.println("→ High");
                    UniversalDataDisplay.ShowAllInmateType("High Security");
                    break;
                case 4:
                    System.out.println("→ Maximum");
                    UniversalDataDisplay.ShowAllInmateType("Maximum Security");
                    break;
                case 5:
                    System.out.println("→ Death Row");
                    UniversalDataDisplay.ShowAllInmateType("Death Row Inmate");
                    break;
                case 6:
                    System.out.println("→ All");
                    UniversalDataDisplay.ShowAllInmateType("All");
                    break;
                case 7: 
                    System.out.println("Exiting . . .");
                    return 0;
            }
        return 0;
    }
}
