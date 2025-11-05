
package Main;

import Config.Validations;
import Config.conf;
import java.util.Scanner;

public class UniversalDataDisplay {
    public static int ShowUserSession(){
        System.out.println("\n--- SHOW USER SESSION ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT s_id, u_id, s_badge, login, logout FROM session_logs";
        
        String[] headers = {"Session ID", "User ID", "User Badge", "Login Time", "Logout Time"};
        
        String[] cols = {"s_id", "u_id", "s_badge", "login", "logout"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static String ShowAllInmateType(String type){
        System.out.println("\n--- SHOW INMATE TYPE ---");
        conf config = new conf();
        
        String sqlQuery;
            if (type.equalsIgnoreCase("All")) {
                sqlQuery = "SELECT t_id, i_id, t_name, t_level FROM type";
            } else {
                sqlQuery = "SELECT t_id, i_id, t_name, t_level FROM type WHERE t_level = '" + type + "'";
            }
            
        String[] headers = {"Type ID", "Inmate ID", "Inmate Name", "Security Level"};
        
        String[] cols = {"t_id", "i_id", "t_name", "t_level"};
        
        config.viewInmate(sqlQuery, headers, cols);
        
        return null;

    }
    
    public static int ShowallUser(){
        System.out.println("\n--- USER DISPLAY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT u_id, u_name, 'HASHED' AS u_pass, u_position, u_badge, u_approval, date_added FROM users";
        
        String[] headers = {"User ID", "User Name", "User Pass", "User Position", "Unique Badge", "System Access", "Date Added"};
        
        String[] cols = {"u_id", "u_name", "u_pass", "u_position", "u_badge", "u_approval", "date_added"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static int ShowLogs(){
        System.out.println("\n--- VIEWING HISTORY INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT h_id, i_id, u_id, h_name, h_context, h_date FROM logs";
        
        String[] headers = {"Log ID", "Inmate ID", "User ID", "Inmate Name", "Log Action", "Log Date"};
        
        String[] cols = {"h_id", "i_id", "u_id", "h_name", "h_context", "h_date"};
        
        config.viewInmate(sqlQuery, headers, cols);
        return 0;
    }
    
    public static int ShowInmateData() {
    System.out.println("\n--- SHOW INMATE ---");
    conf config = new conf();
    String active = "Active";
    
    String sqlQuery = String.format(
        "SELECT i_id, u_id, i_name, i_type, i_infoStatus " +
        "FROM inmate " +
        "WHERE i_infoStatus = '%s'",
        active
    );
    
    String[] headers = {"Inmate ID", "User ID", "Inmate Name", "Inmate Type", "Data Status"};
    String[] cols = {"i_id", "u_id", "i_name", "i_type", "i_infoStatus"};
    
    config.viewInmate(sqlQuery, headers, cols);
    
    return 0;
}
    
    public static int SearchInmateData() {
    System.out.println("\n--- SEARCH INMATE INFO ---");
    conf config = new conf();
    Scanner sc = new Scanner(System.in);
    String searchValue = "";

    System.out.println("1: Name\n2: Age\n3: Gender\n4: Nationality\n5: Inmate Status\n6: Type\n7: Date Apprehended\n8: Date Registered\n9: Data Status\n10: Cancel");
    System.out.print("Enter choice: ");
    int choice = Validations.ChoiceValidation(1, 10);

    String column = "";
    switch (choice) {
        case 1: column = "i_name"; break;
        case 2: column = "i_age"; break;
        case 3: column = "i_gender"; break;
        case 4: column = "i_nationality"; break;
        case 5: column = "i_status"; break;
        case 6: column = "i_type"; break;
        case 7: column = "i_dateApprehended"; break;
        case 8: column = "i_dateRegistered"; break;
        case 9: column = "i_infoStatus"; break;
        case 10: 
            System.out.println("Search cancelled."); 
            return 0;
        default: 
            System.out.println("Invalid choice."); 
            return 0;
    }

    if(choice == 3){
        System.out.println("\n ----- Select Gender: ----- ");
        System.out.println("1: Male");
        System.out.println("2: Female");
        System.out.println("3: Other");
        System.out.print("Choice: ");
        int sex = Validations.ChoiceValidation(1, 3);
        
        switch(sex){
            case 1: searchValue = "Male"; break;
            case 2: searchValue = "Female"; break;
            case 3: searchValue = "Other"; break;
        }
        
    }else if(choice == 9){
        System.out.println("\n ----- Select Data Status: ----- ");
        System.out.println("1: Active ");
        System.out.println("2: Unactive");
        System.out.print("Choice: ");
        int ans = Validations.ChoiceValidation(1, 2);
        
        switch(ans){
            case 1: searchValue = "Active"; break;
            case 2: searchValue = "Unactive"; break;
        }
        
    }else{
        System.out.print("Enter search value: ");
        searchValue = sc.nextLine().trim();
    }

    String sqlQuery = "SELECT i_id, u_id, i_name, i_type, i_infoStatus FROM inmate WHERE " 
                      + column + " LIKE '%" + searchValue + "%'";

    String[] headers = {"Inmate ID", "User ID", "Inmate Name", "Inmate Type", "Data Status"};
    String[] cols = {"i_id", "u_id", "i_name", "i_type", "i_infoStatus"};

    config.viewInmate(sqlQuery, headers, cols);

    return 0;
}
    
    
    
    public static int ShowRecordData(int id) {
    System.out.println("\n--- VIEWING INMATE RECORDS ---");
    conf config = new conf();
    String active = "Guilty";

    String sqlQuery = String.format(
        "SELECT r.r_id, r.i_id, r.r_name, r.r_stat, r.r_recordStatus " +
        "FROM record r " +
        "WHERE r.i_id = %d AND r.r_recordStatus = '%s'",
        id, active
    );

    String[] headers = {"Record ID", "Inmate ID", "Record Name", "Record Status", "Case Status"};
    String[] cols = {"r_id", "i_id", "r_name", "r_stat", "r_recordStatus"};

    config.viewRecords(sqlQuery, headers, cols);
    
    return 0;
}
    
    
    public static void viewInmateInformation(){
        System.out.println("\n--- VIEWING INMATE INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT i_id, u_id, i_name, i_age, i_gender, i_nationality, i_impri, i_stat,"
                + " i_record_quan, i_type, i_apprehended, i_date_register, i_infoStatus FROM inmate";
        
        String[] headers = {"ID", "User ID", "Inmate Name", "Age", "Gender", "Nationaility", "Imprison yrs",
            "Status", "Record Quant.", "Type", "Date Apprehend", "Date Regist.", "Information Status"};
        
        String[] cols = {"i_id", "u_id", "i_name", "i_age", "i_gender", "i_nationality", "i_impri", "i_stat", 
            "i_record_quan", "i_type", "i_apprehended", "i_date_register", "i_infoStatus"};
        
        config.viewInmate(sqlQuery, headers, cols);
        
    }
    
    public static void viewInmateRecord(int id) {
    System.out.println("\n--- VIEWING INMATE RECORDS ---");
    conf config = new conf();
    
    String sqlQuery = "SELECT r.r_id, r.i_id, r.r_name, r.r_stat, r.r_conviction_time, r.r_date_commited, r.r_recordStatus " +
                      "FROM record r WHERE r.i_id = " + id;
    
    String[] headers = {"Record ID", "Inmate ID", "Record Name", "Record Status", "Time Sentence", "Date Commited", "Case Status"};
    String[] cols = {"r_id", "i_id", "r_name", "r_stat", "r_conviction_time", "r_date_commited", "r_recordStatus"};
    
    config.viewRecords(sqlQuery, headers, cols);
    
    }
}
