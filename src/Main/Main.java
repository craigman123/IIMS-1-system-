
package Main;

import java.util.*;
import Config.conf;
import Config.archive_conf;
import Config.Validations;

public class Main {
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        Log_Reg lgreg = new Log_Reg();
                
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
        
        switch(choice){
            case 1:
                
                lgreg.Register();
                break;
            case 2:
                lgreg.LogIn();
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
    
    public static void AddInmate(int count){
        
        int r;
        for(r = 0; r < count; r++){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        archive_conf archive = new archive_conf();
        
        System.out.print("Enter Inmate Name " + (r + 1) +": ");
        String name = sc.next();

        System.out.print("Enter Inmate Age: ");
        int age = Validations.AgeValidations(17);
        
        System.out.println("Select Gender:");
        System.out.println("1: Male");
        System.out.println("2: Female");
        System.out.println("3: Other");
        System.out.print("Choice: ");
        int genderChoice = Validations.ChoiceValidation(1, 3);

        String sex = "";

        switch (genderChoice) {
            case 1: sex = "Male"; break;
            case 2: sex = "Female"; break;
            case 3: sex = "Other"; break;
            default:
                sex = "Alien";
                System.out.println("Invalid choice, defaulting to Alien");
                break;
        }
        
        System.out.print("Enter Inmate Nationality: ");
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
        
        System.out.print("Enter Inmate Date Registered: ");
        String date = sc.next();
        
        String sql;
        
        sql = "INSERT INTO inmate(i_name, i_age, i_gender, i_nationality, i_impri, i_stat, i_record_quan, i_type, i_apprehended,"
                + " i_date_register) VALUES (?,?,?,?,?,?,?,?,?,?)";
        int inmate = config.addRecordAndReturnId(sql, name, age, sex, nation, impri, stat, rec_quan, in_type, appre, 
            date);

        sql = "INSERT INTO history(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
        int history = archive.addRecordAndReturnId(sql, name, date, "Added Inmate", inmate);

        sql = "INSERT INTO type(t_name, t_level, i_id) VALUES (?,?,?)";
        int type_id = config.addRecordAndReturnId(sql, name, in_type, inmate);
        
        System.out.println("Inmate Record: ");
            
            int x;
            
            for(x = 0; x < rec_quan; x++){
            System.out.print("Enter Record Name " + (x+1) +": ");
            String rec_name = sc.next();
            
            System.out.println("Record Status: ");
            String rec_stat = RecordStatus();
            
            System.out.print("Conviction Time: ");
            String convict = sc.next();
            
            System.out.print("Total Imprisonment: ");
            String total_imrpi = sc.next(); 
            
            sql = "INSERT INTO record(r_name, r_stat, r_conviction_time, r_total_impri, i_id) VALUES (?,?,?,?,?)";
            int record = config.addRecordAndReturnId(sql, rec_name, rec_stat, convict, total_imrpi, inmate);
            
            sql = "INSERT INTO a_inmate(name, age, gender, nationality, impri, status, record_quan, type, date_apprehended,"
                    + " date_registered, i_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            int archive_inmate = archive.addRecordAndReturnId(sql, name, age, sex, nation, impri, stat, rec_quan, 
                    in_type, appre, date, inmate);
    
            sql = "INSERT INTO a_record(name, status, conviction_time, i_id) VALUES (?,?,?,?)";
            int archive_record = archive.addRecordAndReturnId(sql, rec_name, rec_stat, convict, inmate);
            
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
        archive_conf archive = new archive_conf();
        
        System.out.print("Do you want to view Inmate Records?(y-1/n-0): ");
        int ans = sc.nextInt();
        
        System.out.println("Enter Current Date:");
        String c_date = sc.next();        
        if(ans == 1){
            viewInmateInformation();
        }
        
        System.out.print("Delete Inmate Data: ");
        int iid = sc.nextInt();
        
        String in_name = "";
        String sqlQuery = "SELECT i_name FROM inmate WHERE i_id = ?";
        List<Map<String, Object>> result = dbConfig.fetchRecords(sqlQuery, iid);
        
        String deleteInmateInfo;
        deleteInmateInfo = "DELETE FROM inmate WHERE i_id = ?";
        dbConfig.deleteRecord(deleteInmateInfo, iid);

        if (!result.isEmpty()) {
            java.util.Map<String, Object> getname = result.get(0);
            in_name = getname.get("i_name").toString();
        }

        String sql = "INSERT INTO history(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
        archive.addRecordAndReturnId(sql, in_name, c_date, "Deleted Inmate", iid);
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
        conf config = new conf();
        archive_conf archive = new archive_conf();
        System.out.println("\n--- UPDATE MENU ---");
        String sql;
        
        String name = "", gender, nation, status, appre, regis;
        int flag = 0, alt_num = 0;

        System.out.println("What do you want to change?");
        System.out.println("1: Inmate Information");
        System.out.println("2: Inmate Record");
        System.out.print("Choice: ");
        int ans = Validations.ChoiceValidation(1,2);
        
        System.out.print("Do you want to see inmate List: (y-1/n-2): ");
        int choice = Validations.ChoiceValidation(1,2);
        
        System.out.println("Enter Current Date: ");
        String c_date = sc.next();
        
        int iid = 0;
        
        if(choice == 1){
            
            if(ans == 1){
                viewInmateInformation();
            }else if(ans == 2){
                viewInmateInformation();
                
                System.out.print("Enter Inmate ID: ");
                iid = sc.nextInt();
                
                viewInmateRecord(iid);
            }
        }
        
        switch(ans){
            case 1:
                System.out.print("Enter inmate ID to change: ");
            int id = sc.nextInt();

            System.out.println("Alternation Options: ");
            System.out.println("1: Name");
            System.out.println("2: Age");
            System.out.println("3: Gender");
            System.out.println("4: Nationality");
            System.out.println("5: Imprisonment Years");
            System.out.println("6: Inmate Status");
            System.out.println("7: Type");
            System.out.println("8: Date Apprehended");
            System.out.println("9: Date Registered");

            System.out.print("Select attribute to change: ");
            int alter = sc.nextInt();

            String sqlUpdate = null, archiveUpdate = null;

            switch (alter) {
                case 1: 
                    System.out.print("Enter new Inmate Name: ");
                    name = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_name = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, name, id);
                    archiveUpdate = "UPDATE a_inmate SET name = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, name, id);
                    alt_num = 1;
                    break;
                case 2: 
                    System.out.print("Enter new Inmate Age: ");
                    int age = sc.nextInt();
                    while (age <= 17) {
                        System.out.println("Invalid: Juvenile not in Legal Age.");
                        System.out.print("Try Again: ");
                        age = sc.nextInt();
                    }
                    sqlUpdate = "UPDATE inmate SET i_age = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, age, id);
                    archiveUpdate = "UPDATE a_inmate SET age = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, age, id);
                    alt_num = 2;
                    break;
                case 3:
                    System.out.print("Enter new Gender: ");
                    gender = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_gender = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, gender, id);
                    archiveUpdate = "UPDATE a_inmate SET gender = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, gender, id);
                    alt_num = 3;
                    break;
                case 4:
                    System.out.print("Enter new Nationality: ");
                    nation = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_nationality = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, nation, id);
                    archiveUpdate = "UPDATE a_inmate SET nationality = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, nation, id);
                    alt_num = 4;
                    break;
                case 5:
                    System.out.print("Enter new Imprisonment Years: ");
                    int impri = sc.nextInt();
                    sqlUpdate = "UPDATE inmate SET i_impri = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, impri, id);
                    archiveUpdate = "UPDATE a_inmate SET impri = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, impri, id);
                    alt_num = 5;
                    break;
                case 6:
                    System.out.println("Enter new Status: ");
                    status = InmateStatus();
                    sqlUpdate = "UPDATE inmate SET i_stat = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, status, id);
                    archiveUpdate = "UPDATE a_inmate SET status = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, status, id);
                    alt_num = 6;
                    break;
                case 7:
                    System.out.println("1: Low Security Inmate");
                    System.out.println("2: Medium Security Inmate");
                    System.out.println("3: High Security Inmate");
                    System.out.println("4: Maximum Security Inmate");
                    System.out.println("5: Death Row Inmate");
                    System.out.print("Enter Inmate Type: ");
                    int type = Validations.ChoiceValidation(1, 5);

                    String in_type = "";
                    switch (type) {
                        case 1: in_type = "Low Security"; break;
                        case 2: in_type = "Medium Security"; break;
                        case 3: in_type = "High Security"; break;
                        case 4: in_type = "Maximum Security"; break;
                        case 5: in_type = "Death Row Inmate"; break;
                    }
                    sqlUpdate = "UPDATE inmate SET i_type = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, in_type, id);
                    archiveUpdate = "UPDATE a_inmate SET type = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, in_type, id);
                    alt_num = 7;
                    break;
                case 8:
                    System.out.print("Enter new Date Apprehended (YYYY-MM-DD): ");
                    appre = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_apprehended = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, appre, id);
                    archiveUpdate = "UPDATE a_inmate SET date_apprehended = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, appre, id);
                    alt_num = 8;
                    break;
                case 9:
                    System.out.print("Enter new Date Registered (YYYY-MM-DD): ");
                    regis = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_date_register = ? WHERE i_id = ?";
                    flag = config.updateRecord(sqlUpdate, regis, id);
                    archiveUpdate = "UPDATE a_inmate SET date_register = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, regis, id);
                    alt_num = 9;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    return;
            }

            if (flag == 1) {
                System.out.println("Inmate Successfully Updated: ");
            }

            String context = "";
            switch (alt_num) {
                case 1: context = "Altered Name"; break;
                case 2: context = "Altered Age"; break;
                case 3: context = "Altered Gender"; break;
                case 4: context = "Altered Nationality"; break;
                case 5: context = "Altered Imprisonment Years"; break;
                case 6: context = "Altered Inmate Status"; break;
                case 7: context = "Altered Inmate Type"; break;
                case 8: context = "Altered Date of Apprehension"; break;
                case 9: context = "Altered Date of Registration"; break;
                default: context = "Invalid option"; break;
            }

            String in_name = "";
            if (flag == 1) {
                String sqlQuery = "SELECT i_name FROM inmate WHERE i_id = ?";
                List<Map<String, Object>> result = config.fetchRecords(sqlQuery, id);

                if (!result.isEmpty()) {
                    java.util.Map<String, Object> getname = result.get(0);
                    in_name = getname.get("i_name").toString();
                }

                sql = "INSERT INTO history(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
                archive.addRecordAndReturnId(sql, in_name, c_date, context, id);
            }
            break;
        
        case 2:  

            System.out.print("Enter Inmate Record ID: ");
            int rec_id = sc.nextInt();

            System.out.println("Alternation Options: ");
            System.out.println("1: Record Name");
            System.out.println("2: Status");
            System.out.println("3: Conviction Time");
            System.out.println("4: Total Imprisonment(disabled temporarily)");

            System.out.print("Select attribute to change: ");
            alter = sc.nextInt();

            switch (alter) {
                case 1:
                    System.out.print("Enter new Record Name: ");
                    name = sc.next();
                    String sqlUpdateName = "UPDATE record SET r_name = ? WHERE r_id = ?";
                    flag = config.updateRecord(sqlUpdateName, name, rec_id);
                    archiveUpdate = "UPDATE a_record SET name = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, name, rec_id);
                    alt_num = 10;
                    break;

                case 2:
                    System.out.print("Enter new Record Status: ");
                    String stat = RecordStatus();
                    String sqlUpdateStatus = "UPDATE record SET r_stat = ? WHERE r_id = ?";
                    flag = config.updateRecord(sqlUpdateStatus, stat, rec_id);
                    archiveUpdate = "UPDATE a_record SET status = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, stat, rec_id);
                    alt_num = 11;
                    break;

                case 3:
                    System.out.print("Enter new Conviction Time: ");
                    String conv = sc.next();
                    String sqlUpdateConviction = "UPDATE record SET r_conviction_time = ? WHERE r_id = ?";
                    flag = config.updateRecord(sqlUpdateConviction, conv, rec_id);
                    archiveUpdate = "UPDATE a_record SET conviction_time = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, conv, rec_id);
                    alt_num = 12;
                    break;

                case 4:
                    System.out.print("Enter Total Imprisonment: ");
                    String impri = sc.next();
                    String sqlUpdateImprisonment = "UPDATE record SET r_total_impri = ? WHERE r_id = ?";
                    flag = config.updateRecord(sqlUpdateImprisonment, impri, rec_id);
                    archiveUpdate = "UPDATE a_record SET i_name = ? WHERE i_id = ?";
                    archive.updateRecord(archiveUpdate, impri, rec_id);
                    alt_num = 13;
                    break;

                default:
                    System.out.println("Invalid option selected.");
                    break;
            }

            if (flag == 1) {
                System.out.println("Inmate Record Successfully Updated");
            }

            context = "";
            switch (alt_num) {
                case 10: context = "Altered Record Name"; break;
                case 11: context = "Altered Record Status"; break;
                case 12: context = "Altered Conviction Time"; break;
                case 13: context = "Altered Total Imprisonment"; break;
                default: context = "Invalid option"; break;
            }

            in_name = "";
            if (flag == 1) {
                String sqlQuery = "SELECT i_name FROM inmate WHERE i_id = ?";
                List<Map<String, Object>> result = config.fetchRecords(sqlQuery, iid);

                if (!result.isEmpty()) {
                    java.util.Map<String, Object> getname = result.get(0);
                    in_name = getname.get("i_name").toString();
                }

                sql = "INSERT INTO history(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
                archive.addRecordAndReturnId(sql, in_name, c_date, context, iid);
            }
            break;
        }
    }
}
