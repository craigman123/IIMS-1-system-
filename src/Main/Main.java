
package Main;

import java.util.*;
import Config.conf;
import Config.Validations;

public class Main {
    public static void main(String[] args) {
    
        int sec_code = 123456;
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        Validations val = new Validations();
                
        while(true){
        System.out.println("Welcome to the System: ");
            int ans;
        do{
        System.out.println("Enter Choice: ");
        System.out.println("1: Register User: ");
        System.out.println("2: Log-In: ");
        System.out.println("3: Exit: ");
        
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,3);
        String log_role = "";
        int success = 0, logres, flag = 0;
        
        switch(choice){
            case 1:
                System.out.print("Enter User Name: ");
                String name = sc.next();
                
                while (true) {
                            
                    String qry = "SELECT * FROM users WHERE u_name = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, name);

                    if (result.isEmpty()) {
                        break;
                    } else {
                        System.out.print("User Name already exists, Enter Again: ");
                        name = sc.next();
                    }
                }
                
                System.out.print("Enter Password: ");
                String pass = sc.next();
                
                int pos;
                    System.out.println("Position: ");
                    System.out.println("1: Behavioral Staff's");
                    System.out.println("2: Higher Officials");
                    System.out.println("3: Staff");
                    System.out.println("4: Legal Authorities");

                    System.out.print("Choice: ");
                    pos = Validations.ChoiceValidation(1,4);

                    switch(pos){
                        case 1:
                            log_role = "Behavioral Staff's";
                            break;
                        case 2:
                            log_role = "Higher Officials";
                            break;
                        case 3:
                            log_role = "Staff";
                            break;
                        case 4:
                            log_role = "Legal Authorities";
                            break;

                        default:
                            System.out.println("Invalid choice: ");
                            System.out.println("Try Again: ");
                    }
            
                System.out.print("Badge ID: ");
                String badge = sc.next();
                
                int checked = 0;
                
                do{
                    System.out.print("Enter Security Log In Code: ");
                    int in_code = sc.nextInt();
                    
                    if(in_code == sec_code){
                        System.out.println("Registered Succesfull: ");
                        checked = 1;
                    }else{
                        System.out.println("Invalid Code: ");
                    }
                    
                }while(checked == 0);
                
                
                String sql = "INSERT INTO users(u_name, u_pass, u_position, u_badge, u_approval) VALUES (?,?,?,?,?)";
                
                config.addRecord(sql, name, pass, log_role, badge, "Pending");
                
                break;
            case 2:
                
                int pos2 = 0;
                
                do{
                    System.out.print("Enter User Name: ");
                    String log_name = sc.next();

                    System.out.print("Enter Password: ");
                    String log_pass = sc.next();

                        System.out.println("Position: ");
                        System.out.println("1: Behavioral Staff's");
                        System.out.println("2: Higher Officials");
                        System.out.println("3: Staff");
                        System.out.println("4: Legal Authorities");

                        System.out.print("Choice: ");
                        pos = Validations.ChoiceValidation(1,4);

                        switch(pos){
                            case 1: log_role = "Behavioral Staff's"; break;
                            case 2: log_role = "Higher Officials"; break;
                            case 3: log_role = "Staff"; break;
                            case 4: log_role = "Legal Authorities"; break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }   

                    while (true) {

                        String qry = "SELECT * FROM users WHERE u_name = ? AND u_pass = ? AND u_position = ?";
                        java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, log_name, log_pass, log_role);

                        if (result.isEmpty()) {
                            System.out.println("INVALID CREDENTIALS");
                            System.out.println("Enter Again: ");
                            flag = 1;
                            break;
                        } else {
                            java.util.Map<String, Object> user = result.get(0);
                            String stat = user.get("u_approval").toString();
                            String position = user.get("u_position").toString();
                            if(stat.equals("Pending")){
                                System.out.println("Account is Pending, Contact the Higher Officials!");
                                break;
                            }else if(stat.equals("Disabled")){
                                System.out.println("Account Disabled: Contact Admin");
                                break;
                            }else{
                                System.out.println("LOGIN SUCCESS!");
                                flag = 0;
                                
                            switch (position) {
                                case "Behavioral Staff's":
                                    Behavioral();
                                    break;
                                case "Higher Officials":
                                    Higher();
                                    break;
                                case "Staff":
                                    Staff();
                                    break;
                                case "Legal Authorities":
                                    Authorities();
                                    break;
                                }

                                break;
                            }
                        }
                    }
                }while(flag == 1);
                    
                break;
            case 3:
                
                System.out.println("GoodBye!");
                sc.close();
                System.exit(0);
                
                break;
            default:
                System.out.println("Invalid Choice: ");
                
                    
                }
                    System.out.println("Do you want to continue?");
                    System.out.print("(1 - yes | 2 - no): ");
                    ans = Validations.ChoiceValidation(1,2);
            }while(ans == 1);
        }
    }
    
    public static void viewUserRecord(){
        
        conf config = new conf();
        
        String sqlQuery = "SELECT u_id, u_name, u_pass, u_position, u_badge FROM users";
        String[] headers = {"ID", "Username", "Password", "Position", "Badge"};
        String[] cols = {"u_id", "u_name", "u_pass", "u_position", "u_badge"};
        config.viewRecords(sqlQuery, headers, cols);
        
    }
    
    public static void Behavioral(){
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {

            System.out.println("\n--- BEHAVIORAL MENU ---");
            System.out.println("1: Update Inmate");
            System.out.println("2: View Records");
            System.out.println("3: Log Out");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            while(choice < 1 || choice > 3){
                System.out.print("Invalid Choice: ");
                choice = Validations.ChoiceValidation(1,3);
            }
            
            switch (choice) {
                case 1: UpdateInmate(); break;
                case 2:
                    
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
                case 3:
                    
                    System.out.println("Logging Out: ");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
 
    }
    
    public static void Higher(){
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
                case 1:
                    System.out.print("Inmate Quantity: ");
                    int count = sc.nextInt();
                    AddInmate(count);
                    break;
                case 2: UpdateInmate(); break;
                case 3: deleteInmateRecord(); break;
                case 4: 
                    
                    System.out.println("Do you want to check Records?");
                    System.out.println("1: YES");
                    System.out.print("2: NO");
                    System.out.print("\nChoice: ");
                    int ans2 = Validations.ChoiceValidation(1,2);
                    
                    viewInmateInformation(); 
                 
                    if(ans2 == 1){
                        
                        System.out.println("Enter Inmate ID: ");
                        int id = sc.nextInt();
                        viewInmateRecord(id);
                        
                    }

                    break;
                case 5:
                    ManageUsers();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
    }
    
    public static void Staff(){
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
                case 1:
                    System.out.print("Inmate Quantity: ");
                    int count = sc.nextInt();
                    AddInmate(count);
                    break;
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
    
    public static void Authorities(){
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
                    int id = sc.nextInt();
                    
                    if(ans2 == 1){
                        viewInmateRecord(id);
                    } 
                    
                    break;
                case 2:
                    System.out.println("Logging Out: ");
                    run = false;
                   break;
                default:
                    System.out.println("Invalid Choice!");
                }
            }
    }
    
    public static void AddInmate(int count){
        
        int r;
        for(r = 0; r < count; r++){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        
        System.out.print("Enter Inmate Name: ");
        String name = sc.next();

        System.out.print("Enter Inmate Age: ");
        int age = Validations.AgeValidations(17);
        
        System.out.print("Enter Inmate Gender: ");
        String sex = sc.next();
        
        System.out.print("Enter Inmate Date Taken: ");
        String date = sc.next();
        
        System.out.println("Enter Inmate Nationality: ");
        String nation = sc.next();

        System.out.print("Enter Imprisonment Years: ");
        String impri = sc.next();
        
        String stat = InmateStatus();

        System.out.print("Enter Record Quantity: ");
        int rec_quan = sc.nextInt();
        
        System.out.println("1: Low Security Inmate");
        System.out.println("2: Medium Security Inmate");
        System.out.println("3: High Security Inmate");
        System.out.println("4: Maximum Security Inmate");
        System.out.println("5: Death Row Inmate");
        
        System.out.print("Enter Inmate Type: ");
        int type = Validations.ChoiceValidation(1,5);
        
        String in_type = "";
        
            switch(type){
                case 1: in_type = "Low Security"; break;
                case 2: in_type = "Medium Security"; break;
                case 3: in_type = "High Security"; break;
                case 4: in_type = "Maximum Security"; break; 
                case 5: in_type = "Death Row Inmate"; break;
                default:
                    System.out.println("Invalid Choice: ");
            }

        System.out.print("Enter Date Apprehended: ");
        String appre = sc.next();
        
        String sql;
        
        sql = "INSERT INTO inmate(i_name, i_age, i_gender, i_nationality, i_impri, i_stat, i_record_quan, i_type, i_apprehended,"
                + " i_date_register) VALUES (?,?,?,?,?,?,?,?,?,?)";

        int inmate = config.addRecordAndReturnId(sql, name, age, sex, nation, impri, stat, rec_quan, in_type, appre, 
                date);
       
        sql = "INSERT INTO history(h_stat, h_date, h_name, i_id) VALUES(?,?,?,?)";
        
        int history = config.addRecordAndReturnId(sql, stat, date, name, inmate);
        
        sql = "INSERT INTO type(t_name, t_level, i_id) VALUES (?,?,?)";

        type = config.addRecordAndReturnId(sql, name, in_type, inmate);

        
        
        sql = "INSERT INTO archive(a_name, i_id, a_age, a_gender, a_nationality, a_impri, a_stat, a_record_quan, a_type, a_apprehended,"
                + " a_date_register, t_id, h_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        int archive = config.addRecordAndReturnId(sql, name, inmate, age, sex, nation, impri, stat, rec_quan, in_type, appre, 
                date, type, history);
        
        System.out.println("Inmate Record: ");
            
            int x;
            
            for(x = 0; x < rec_quan; x++){
            System.out.print("Enter Record Name: ");
            String rec_name = sc.next();
            
            
            
            System.out.println("Record Status: ");
            String rec_stat = RecordStatus();
            
            System.out.print("Conviction Time: ");
            String convict = sc.next();
            
            System.out.print("Total Imprisonment: ");
            String total_imrpi = sc.next(); 
            
            sql = "INSERT INTO record(r_name, r_stat, r_conviction_time, r_total_impri, i_id) VALUES (?,?,?,?,?)";

            int record = config.addRecordAndReturnId(sql, rec_name, rec_stat, convict, total_imrpi, inmate);
            
                System.out.println("Appending Record: ");
                System.out.println("Inmate Recorded Succesfully: ");

            }
        }
    }
    
    public static String RecordStatus(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1: Under Investigation");
        System.out.println("2: Awaiting Trial");
        System.out.println("3: Convicted");
        System.out.println("4: Acquitted");
        System.out.println("5: Appeal Pending");
        System.out.println("6: On Probation");
        System.out.println("7: Case Closed");

        
        System.out.print("Enter Status: ");
        int ans = Validations.ChoiceValidation(1,7);
        
        String ans_stat = "";
        
        while(true){
            switch(ans){
                case 1: ans_stat = "Under Investigation"; break;
                case 2: ans_stat = "Awaiting Trial"; break;
                case 3: ans_stat = "Convicted"; break;
                case 4: ans_stat = "Acquitted"; break;
                case 5: ans_stat = "Appeal Pending"; break;
                case 6: ans_stat = "On Probation"; break;
                case 7: ans_stat = "Case Closed"; break;
                default:
                    System.out.println("Invalid Choice: ");
            }
            return ans_stat;
        }
    }
    
    public static String InmateStatus(){
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1: Under Investigation");
        System.out.println("2: Awaiting Trial");
        System.out.println("3: Convicted");
        System.out.println("4: Acquitted");
        System.out.println("5: Appeal Pending");
        System.out.println("6: Parole Granted");
        System.out.println("7: On Probation");
        System.out.println("8: Transferred");
        System.out.println("9: Case Closed");
        System.out.println("10: Deceased");
        
        System.out.print("Enter Status: ");
        int ans = Validations.ChoiceValidation(1,10);
        
        String ans_stat = "";
        
        while(true){
            switch(ans){
                case 1: ans_stat = "Under Investigation"; break;
                case 2: ans_stat = "Awaiting Trial"; break;
                case 3: ans_stat = "Convicted"; break;
                case 4: ans_stat = "Acquitted"; break;
                case 5: ans_stat = "Appeal Pending"; break;
                case 6: ans_stat = "Parole Granted"; break;
                case 7: ans_stat = "On Probation"; break;
                case 8: ans_stat = "Transferred"; break;
                case 9: ans_stat = "Case Closed"; break;
                case 10: ans_stat = "Deceased"; break;
                default:
                    System.out.println("Invalid Choice: ");
            }
            return ans_stat;
        }
    }
    
    public static void deleteInmateRecord(){
        
        System.out.println("\n--- DELETION MENU ---");
        Scanner sc = new Scanner(System.in);
        conf dbConfig = new conf();
        
        System.out.print("Do you want to view Inmate Records?(y-1/n-0): ");
        int ans = sc.nextInt();
        
        if(ans == 1){
            viewInmateInformation();
        }
        
        System.out.print("Delete Inmate Data: ");
        int InmateId = sc.nextInt();
        
        String deleteInmateInfo;
        deleteInmateInfo = "DELETE FROM inmate WHERE i_id = ?";
        
        dbConfig.deleteRecord(deleteInmateInfo, InmateId);
    }
    
    public static void viewInmateInformation(){
        System.out.println("\n--- VIEWING INMATE INFO ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT i_id, i_name, i_age, i_gender, i_nationality, i_impri, i_stat,"
                + " i_record_quan, i_type, i_apprehended, i_date_register FROM inmate";
        
        String[] headers = {"ID", "Inmate Name", "Age", "Gender", "Nationaility", "Imprison yrs",
            "Status", "Record Quant.", "Type", "Date Apprehend", "Date Regist."};
        
        String[] cols = {"i_id", "i_name", "i_age", "i_gender", "i_nationality", "i_impri", "i_stat", 
            "i_record_quan", "i_type", "i_apprehended", "i_date_register"};
        
        config.viewRecords(sqlQuery, headers, cols);
        
    }
    
    public static void viewInmateRecord(int id) {
    System.out.println("\n--- VIEWING INMATE RECORDS ---");
    conf config = new conf();
    
    String sqlQuery = "SELECT r.r_id, r.i_id, r.r_name, r.r_stat, r.r_conviction_time, r.r_total_impri " +
                      "FROM record r WHERE r.i_id = " + id;
    
    String[] headers = {"Record ID", "Inmate ID", "Record Name", "Record Status", "Time Sentence", "Total Time Sent."};
    String[] cols = {"r_id", "i_id", "r_name", "r_stat", "r_conviction_time", "r_total_impri"};
    
    config.viewRecords(sqlQuery, headers, cols);
    
    }
    
    public static void UpdateInmate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- UPDATE MENU ---");
        
        String name, gender, nation, status, appre, regis;
        int again = 0;
        
        System.out.println("What do you want to change?");
        System.out.println("1: Inmate Information");
        System.out.println("2: Inmate Record");
        System.out.print("Choice: ");
        int ans = Validations.ChoiceValidation(1,2);
        
        System.out.print("Do you want to see inmate List: (y-1/n-2): ");
        int choice = Validations.ChoiceValidation(1,2);
        
            if(choice == 1 && ans == 1){
                viewInmateInformation();
            }else if(choice == 1 && ans == 2){
                viewInmateInformation();
                
                System.out.print("Enter Inmate ID: ");
                int iid = sc.nextInt();
                
                viewInmateRecord(iid);
            }
        
        switch(ans){
            case 1:
                
        conf config = new conf();
                
        System.out.print("Enter inmate ID to change:");
        int id = sc.nextInt();
                
        System.out.print("Enter new Inmate Name: ");
        name = sc.next();
        
        System.out.print("Enter new Inmate Age: ");
        int age = sc.nextInt();
        
        while(age <= 17){
            System.out.println("Invalid: Juvenile not in Legal Age: ");
            System.out.print("Try Again: ");
            age = sc.nextInt();
        }
        
        System.out.print("Enter new Gender: ");
        gender = sc.next();
        
        System.out.print("Enter new Nationaility: ");
        nation = sc.next();
        
        System.out.print("Imprisonment Years: ");
        int impri = sc.nextInt();
        
        System.out.print("Enter new Status: \n");
        status = InmateStatus();
        
        System.out.println("1: Low Security Inmate");
        System.out.println("2: Medium Security Inmate");
        System.out.println("3: High Security Inmate");
        System.out.println("4: Maximum Security Inmate");
        System.out.println("5: Death Row Inmate");
        
        System.out.print("Enter Inmate Type: ");
        int type = Validations.ChoiceValidation(1,5);
        
        String in_type = "";
        
            switch(type){
                case 1: in_type = "Low Security"; break;
                case 2: in_type = "Medium Security"; break;
                case 3: in_type = "High Security"; break;
                case 4: in_type = "Maximum Security"; break; 
                case 5: in_type = "Death Row Inmate"; break;
                default:
                    System.out.println("Invalid Choice: ");
            }
        
        System.out.print("Enter new Date Apprehended: ");
        appre = sc.next();
        
        System.out.print("Enter new Date Register: ");
        regis = sc.next();
        
        String sqlUpdate = "UPDATE inmate SET i_name = ?, i_age = ?, i_gender = ?, i_nationality = ?, " +
                   "i_impri = ?, i_stat = ?, i_type = ?, i_apprehended = ?, i_date_register = ? " +
                   "WHERE i_id = ?";
        
        config.updateRecord(sqlUpdate, name, age, gender, nation, impri, status, in_type, appre, regis, id);
        
            break;
        case 2:

            System.out.print("Enter Record ID to change:");
            int rec_id = sc.nextInt();

            System.out.print("Enter new Record Name: ");
            name = sc.next();

            System.out.print("Enter new Record Status: \n");
            String stat1 = RecordStatus();

            System.out.print("Enter new Conviction Time: ");
            String conv = sc.next();

            System.out.print("Enter Total Imprisonment: ");
            String impri1 = sc.next();

            String sqlUpdateRecord = "UPDATE record SET r_name = ?, r_stat = ?, r_conviction_time = ?, "
                    + "r_total_impri = ? WHERE r_id = ?";
            
            conf conf = new conf();
            
            conf.updateRecord(sqlUpdateRecord, name, stat1, conv, impri1, rec_id);
                    break;
        }
    }
    
    public static void ManageUsers(){ //Only for Higher Officials and Developers
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        
        while(run){
        System.out.println("===== User Manager =====");
        System.out.println("1: Add User");
        System.out.println("2: Update User");
        System.out.println("3: Delete User");
        System.out.println("4: View User");
        System.out.println("5: User System Access");
        System.out.println("6: Log Out");
        System.out.print("Enter: ");
        
        int choice = Validations.ChoiceValidation(1,6);
        
        switch(choice){
            case 1: addUser();
                break;
            case 2: UpdateUser();
                break;
            case 3: DelUser();
                break;
            case 4: ViewUser();
                break;
            case 5: UserAccess();
                break;
            case 6:
                    System.out.println("Logging Out: ");
                    run = false;
                break;
            default:
                System.out.println("Invalid Choice: ");
            }
        }
    }
    
    public static void UserAccess(){
        
        conf config = new conf();
        Scanner sc = new Scanner(System.in);
        String stat = "";
        System.out.println("==== APPROVAL MENU ====");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        
        if(choice == 1){
        
        String sqlQuery = "SELECT u_id, u_name, u_position, u_approval FROM users";
        
        String[] headers = {"ID", "User Name", "Position", "Status"};
        
        String[] cols = {"u_id", "u_name", "u_position", "u_approval"};
        
        config.viewRecords(sqlQuery, headers, cols);
        }
        
        System.out.print("Enter ID to Validate System Access: ");
        int id = sc.nextInt();
        
        System.out.println("Enter Status: ");
        System.out.println("1: Approved");
        System.out.println("2: Pending");
        System.out.println("3: Disabled");
        System.out.print("Choice: ");
        int status = Validations.ChoiceValidation(1,3);
        
        switch(status){
            case 1: stat = "Approved"; break;
            case 2: stat = "Pending"; break;
            case 3: stat = "Disabled"; break;
            default:
                System.out.println("Invalid Choice: ");
        }
        
        String sqlUpdate = "UPDATE users SET u_approval = ? WHERE u_id = ?";
        
        config.updateRecord(sqlUpdate, stat, id);
    }
    
    public static void DelUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        System.out.println("==== REMOVE USER MENU ====");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        
        if(choice == 1){
            ViewUser();
        }
        
        System.out.print("Enter User ID to Remove: ");
        int u_id = sc.nextInt();
        
        String deleteInmateInfo = "DELETE FROM users WHERE u_id = ?";
        config.deleteRecord(deleteInmateInfo, u_id);
        
    }
    
    public static void ViewUser(){
        System.out.println("\n--- VIEWING USER MENU ---");
        conf config = new conf();
        
        String sqlQuery = "SELECT u_id, u_name, u_pass, u_position, u_badge, u_approval FROM users";
        
        String[] headers = {"ID", "User Name", "Password", "Position", "Badge Number", "Status"};
        
        String[] cols = {"u_id", "u_name", "u_pass", "u_position", "u_badge", "u_approval"};
        
        config.viewRecords(sqlQuery, headers, cols);
    }
    
    public static void UpdateUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        String pos = "", stat = "";
        System.out.println("====== UPDATE USER MENU ======");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        
        if(choice == 1){
            ViewUser();
        }
        
        System.out.print("Enter User ID to alter: ");
        int u_id = sc.nextInt();
        
        System.out.print("Enter User Name: ");
        String name = sc.next();
        
        System.out.print("Enter Password: ");
        String pass = sc.next();
        
        System.out.println("Enter Position: ");
        System.out.println("1: Behavioral Staff's");
        System.out.println("2: Higher Officials");
        System.out.println("3: Staff");
        System.out.println("4: Legal Authorities");
        System.out.print("Choice: ");
        int ps = Validations.ChoiceValidation(1,4);
        
        switch(ps){
            case 1: pos = "Behavioral Staff's"; break;
            case 2: pos = "Higher Officials"; break;
            case 3: pos = "Staff"; break;
            case 4: pos = "Legal Authorities"; break;
            default:
                System.out.println("Invalid choice. Try again.");
        }  
        
        System.out.print("Enter Badge Number: ");
        String badge = sc.next();
        
        System.out.println("Enter Status: ");
        System.out.println("1: Approved");
        System.out.println("2: Pending");
        System.out.println("3: Disabled");
        System.out.print("Choice: ");
        int status = Validations.ChoiceValidation(1,3);
        
        switch(status){
            case 1: stat = "Approved"; break;
            case 2: stat = "Pending"; break;
            case 3: stat = "Disabled"; break;
            default:
                System.out.println("Invalid Choice: ");
        }
        
       String sqlUpdate = "UPDATE users SET u_name = ?, u_pass = ?, u_position = ?, u_badge = ?, " +
                   "u_approval = ? WHERE u_id = ?";
        
        config.updateRecord(sqlUpdate, name, pass, pos, badge, stat, u_id);
    }
    
    public static void addUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        String stat = "", ps = "";
        
        System.out.println("===== Add User =====");
        
        System.out.print("Enter Name: ");
        String name = sc.next();
        
        while (true) {
                            
            String qry = "SELECT * FROM users WHERE u_name = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, name);

            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("User Name already exists, Enter Again: ");
                name = sc.next();
            }
        }
        
        System.out.println("Enter Password: ");
        String pass = sc.next();
        
        System.out.println("Position: ");
        System.out.println("1: Behavioral Staff's");
        System.out.println("2: Higher Officials");
        System.out.println("3: Staff");
        System.out.println("4: Legal Authorities");

        System.out.print("Enter Position: ");
        int pos = Validations.ChoiceValidation(1,4);
        
        switch(pos){
            case 1: ps = "Behavioral Staff's"; break;
            case 2: ps = "Higher Officials"; break;
            case 3: ps = "Staff"; break;
            case 4: ps = "Legal Authorities"; break;
            default:
                System.out.println("Invalid Position: ");
        }
        
        System.out.println("1: Approved");
        System.out.println("2: Pending");
        System.out.println("3: Disabled");
        System.out.print("User Status: ");
        int status = Validations.ChoiceValidation(1,3);
        
        switch(status){
            case 1: stat = "Approved"; break;
            case 2: stat = "Pending"; break;
            case 3: stat = "Disabled"; break;
            default:
                System.out.println("Invalid Status: ");
        }
        
        System.out.print("Enter Badge: ");
        String badge = sc.next();
        
        String sql = "INSERT INTO users(u_name, u_pass, u_position, u_badge, u_approval) VALUES (?,?,?,?,?)";
        config.addRecordAndReturnId(sql, name, pass, ps, badge, stat);
        
        if(!sql.isEmpty()){
            System.out.println("User Succesfully Added: ");
        }
    }
}




