
package Main;

import java.util.*;
import Config.conf;
import Config.Validations;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        Log_Reg lgreg = new Log_Reg();
                
        while(true){
        System.out.println(" ----- Welcome to the System: ----- ");
            int ans;
        do{
        System.out.println("\n +++ Enter Choice: +++ ");
        System.out.println("1: Register User: ");
        System.out.println("2: Log-In: ");
        System.out.println("3: Exit: ");
            System.out.println("=====================");
        
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
                
            default:
                System.out.println("Invalid Choice: ");
                
                }
        
                System.out.print("\n ----- Do you want to continue? ----- ");
                System.out.print("\n ------- (1 - yes || 2 - no): ------- ");
                System.out.print("\nChoice: ");
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
    
    public static void AddInmate(){
        
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        int impri = 0, add = 0;
        
        System.out.println("\n ----- Register Inmate -----");
        
        System.out.print("Inmate Quantity: ");
        int count = sc.nextInt();
        
        int r;
        for(r = 0; r < count; r++){
        
            System.out.print("\nEnter Inmate Name " + (r + 1) +": ");
            String name = sc.next();

            System.out.print("Enter Inmate Age: ");
            int age = Validations.AgeValidations(17);

            System.out.println("\n ----- Select Gender: ----- ");
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

            System.out.print("\nEnter Inmate Nationality: ");
            String nation = sc.next();

            System.out.println("");
            System.out.print("\n ----- Status Options ----- \n");
            String stat = InmateStatus();

            System.out.println("\n ----- Type Options ----- ");
            System.out.println("1: Low Security Inmate");
            System.out.println("2: Medium Security Inmate");
            System.out.println("3: High Security Inmate");
            System.out.println("4: Maximum Security Inmate");
            System.out.println("5: Death Row Inmate");

            System.out.print(" -- Enter Inmate Type: -- ");
            System.out.print("\nChoice: ");
            int type = Validations.ChoiceValidation(1,5);
            System.out.println("============================");

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
                
            System.out.println(" ----- Date Apprehended ----- ");
            System.out.print("\nYear: ");
            String year = sc.next();

            System.out.print("Month: ");
            String month = sc.next();

            System.out.print("Day: ");
            String day = sc.next();
            System.out.println("\n ======================== \n");
            sc.nextLine();
            String appre = year + "-" + month + "-" + day;

            String date = Validations.Date();
            
            System.out.print(" ----- Inmate Record ----- ");
            System.out.print("\nEnter Record Quantity: ");
            int rec_quan = sc.nextInt();

            String sql = "INSERT INTO inmate(u_id, i_name, i_age, i_gender, i_nationality, i_impri, i_stat, i_record_quan, i_type, i_apprehended,"
                    + " i_date_register, i_infoStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            int inmate = config.addRecordAndReturnId(sql, Session.getUserId(), name, age, sex, nation, 0, stat, rec_quan, in_type, appre, date, "Imprisoned");

            sql = "INSERT INTO logs(h_name, h_date, h_context, i_id, u_id) VALUES(?,?,?,?,?)";
            int history = config.addRecordAndReturnId(sql, name, date, "Added Inmate", inmate, Session.getUserId());

            sql = "INSERT INTO type(t_name, t_level, i_id) VALUES (?,?,?)";
            int type_id = config.addRecordAndReturnId(sql, name, in_type, inmate);

                int x;
                
            if(stat.equals(0)){
                System.out.println("No Record Inmate Cancelled: ");
            } else {
                for(x = 0; x < rec_quan; x++){
                    System.out.print("\nEnter Record Name " + (x+1) +": ");
                    String rec_name = sc.next();

                    System.out.println("\n ----- Record Status ----- ");
                    String rec_stat = RecordStatus();
                    System.out.println(" ====================== ");

                    System.out.print("Conviction Time: ");
                    int convict = sc.nextInt();

                    System.out.println("\n ----- Commited Date ----- ");
                    System.out.print("Year: ");
                    year = sc.next();
                    
                    System.out.print("Month: ");
                    month = sc.next();
                    
                    System.out.print("Day: ");
                    day = sc.next();
                    
                    System.out.println(" ======================== ");
                    
                    String commited = year + "-" + month + "-" + day;

                    sql = "INSERT INTO record(r_name, r_stat, r_conviction_time, i_id, r_date_commited, r_recordStatus) VALUES (?,?,?,?,?,?)";
                    int record = config.addRecordAndReturnId(sql, rec_name, rec_stat, convict, inmate, commited, "Guilty");
                    
                    System.out.println("----- Appending Record: -----");
                    System.out.println("Inmate Recorded Succesfully: ");
                    
                    impri = impri + convict;
                    
                }
                
                sql = "UPDATE inmate SET i_impri = ? WHERE i_id = ?";
                config.updateRecord(sql, impri, inmate);
            }
        }
    }
    
    public static String RecordStatus() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n----- Record Status Menu -----");
        System.out.println("1: Under Investigation");
        System.out.println("2: Awaiting Trial");
        System.out.println("3: Convicted");
        System.out.println("4: Acquitted");
        System.out.println("5: Appeal Pending");
        System.out.println("6: On Probation");
        System.out.println("7: Case Closed");

        System.out.print("Enter Status (1–7): ");
        int ans = Validations.ChoiceValidation(1, 7);

        String ans_stat = "";

        switch (ans) {
            case 1: ans_stat = "Under Investigation"; break;
            case 2: ans_stat = "Awaiting Trial"; break;
            case 3: ans_stat = "Convicted"; break;
            case 4: ans_stat = "Acquitted"; break;
            case 5: ans_stat = "Appeal Pending"; break;
            case 6: ans_stat = "On Probation"; break;
            case 7: ans_stat = "Case Closed"; break;
            default:
                ans_stat = "Unknown";
                System.out.println("Invalid Choice.");
                break;
        }

        return ans_stat;
    }
    
    public static String InmateStatus() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n----- Inmate Status Menu -----");
        System.out.println("0: Cancel");
        System.out.println("1: Pending Admission");
        System.out.println("2: Admitted");
        System.out.println("3: Under Investigation");
        System.out.println("4: Awaiting Trial");
        System.out.println("5: Convicted");
        System.out.println("6: Parole Granted");
        System.out.println("7: On Probation");
        System.out.println("8: Transferred");
        System.out.println("9: Released");
        System.out.println("10: Deceased");

        System.out.print("Enter Status (0–10): ");
        int ans = Validations.ChoiceValidation(0, 10);

        String ans_stat = "";

        switch (ans) {
            case 0: ans_stat = "Cancelled"; break;
            case 1: ans_stat = "Pending Admission"; break;
            case 2: ans_stat = "Admitted"; break;
            case 3: ans_stat = "Under Investigation"; break;
            case 4: ans_stat = "Awaiting Trial"; break;
            case 5: ans_stat = "Convicted"; break;
            case 6: ans_stat = "Parole Granted"; break;
            case 7: ans_stat = "On Probation"; break;
            case 8: ans_stat = "Transferred"; break;
            case 9: ans_stat = "Released"; break;
            case 10: ans_stat = "Deceased"; break;
            default:
                ans_stat = "Unknown";
                System.out.println("Invalid Choice. Please try again.");
                break;
        }

        return ans_stat;
    }
    
    public static void deleteInmateRecord(){
        
        System.out.println("\n--- DELETION MENU ---");
        Scanner sc = new Scanner(System.in);
        conf dbConfig = new conf();
        
        System.out.print("Do you want to view Inmate Records?(y-1/n-0): ");
        int ans = sc.nextInt();
        
        System.out.println("Enter Current Date:");
        String c_date = sc.next();        
        if(ans == 1){
            viewInmateInformation();
        }
        
        System.out.print("Delete Inmate Information: ");
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

        String sql = "INSERT INTO logs(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
        dbConfig.addRecordAndReturnId(sql, in_name, c_date, "Deleted Inmate", iid);
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
    
    public static int UpdateInmate() {
    Scanner sc = new Scanner(System.in);
    conf config = new conf();
    System.out.println("\n--- UPDATE MENU ---");

    String sql;
    String name = "", gender, nation, status, appre, regis;
    int flag = 0, alt_num = 0;

    System.out.println("What do you want to change?");
    System.out.println("1: Inmate Information");
    System.out.println("2: Inmate Record");
    System.out.print("Choice: ");
    int ans = Validations.ChoiceValidation(1, 2);

    System.out.print("\nDo you want to see inmate list? (y-1/n-2): ");
    int choice = Validations.ChoiceValidation(1, 2);
    int iid = 0;

    if (choice == 1) {
        if (ans == 1) {
            viewInmateInformation();
        } else if (ans == 2) {
            viewInmateInformation();
            System.out.print("Enter Inmate ID: ");
            iid = sc.nextInt();
            viewInmateRecord(iid);
        }
    }

    switch (ans) {
        case 1:
            System.out.println(" ----- Inmate Alteration Menu ------ ");
            System.out.print("Enter inmate ID to change: ");
            int id = sc.nextInt();

            System.out.println("\n ------ Alteration Options: ----- ");
            System.out.println("1: Name");
            System.out.println("2: Age");
            System.out.println("3: Gender");
            System.out.println("4: Nationality");
            System.out.println("5: Inmate Status");
            System.out.println("6: Type");
            System.out.println("7: Date Apprehended");
            System.out.println("8: Date Registered");
            System.out.println("9: Data Status");
            System.out.println("10: Exit");
            System.out.println(" ============================== ");
            System.out.print("Select attribute to change: ");

            int alter = sc.nextInt();
            while (alter <= 0 || alter > 10) {
                System.out.print("Invalid Choice: Enter Again: ");
                alter = sc.nextInt();
            }
            System.out.println("");

            String sqlUpdate = "";

            switch (alter) {
                case 1:
                    System.out.print("Enter new Inmate Name: ");
                    name = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_name = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, name, id);
                    flag = 1; alt_num = 1;
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
                    config.updateRecord(sqlUpdate, age, id);
                    flag = 1; alt_num = 2;
                    break;
                case 3:
                    System.out.println("\n ----- Gender Options ----- ");
                    System.out.println("1: Male");
                    System.out.println("2: Female");
                    System.out.println("3: Other");
                    System.out.print("Enter new Gender: ");
                    int genderChoice = sc.nextInt();

                    while (genderChoice < 1 || genderChoice > 3) {
                        System.out.println("Invalid choice. Try again.");
                        genderChoice = sc.nextInt();
                    }

                    String sex = "";
                    switch (genderChoice) {
                        case 1: sex = "Male"; break;
                        case 2: sex = "Female"; break;
                        case 3: sex = "Other"; break;
                    }

                    sqlUpdate = "UPDATE inmate SET i_gender = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, sex, id);
                    flag = 1; alt_num = 3;
                    break;
                case 4:
                    System.out.print("Enter new Nationality: ");
                    nation = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_nationality = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, nation, id);
                    flag = 1; alt_num = 4;
                    break;
                case 5:
                    System.out.println("Enter new Status: ");
                    status = InmateStatus();
                    sqlUpdate = "UPDATE inmate SET i_stat = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, status, id);
                    flag = 1; alt_num = 5;
                    break;
                case 6:
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
                        default: in_type = "";
                    }
                    
                    sqlUpdate = "UPDATE inmate SET i_type = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, in_type, id);
                    flag = 1; alt_num = 6;
                    break;
                case 7:
                    System.out.print("Enter new Date Apprehended (YYYY-MM-DD): ");
                    appre = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_apprehended = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, appre, id);
                    flag = 1; alt_num = 7;
                    break;
                case 8:
                    System.out.print("Enter new Date Registered (YYYY-MM-DD): ");
                    regis = sc.next();
                    sqlUpdate = "UPDATE inmate SET i_date_register = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, regis, id);
                    flag = 1; alt_num = 8;
                    break;
                case 9:
                    System.out.println("Select Info Status:");
                    System.out.println("1: Imprisoned");
                    System.out.println("2: Released");
                    System.out.print("Choice: ");
                    int infoChoice = Validations.ChoiceValidation(1, 2);
                    String stat = (infoChoice == 1) ? "Imprisoned" : "Released";
                    sqlUpdate = "UPDATE inmate SET i_infoStatus = ? WHERE i_id = ?";
                    config.updateRecord(sqlUpdate, stat, id);
                    flag = 1; alt_num = 9;
                    break;
                case 10:
                    System.out.println("Exiting...");
                    return 0;
            }

            if (flag == 1) {
                System.out.println("Inmate Successfully Updated.");
            }

            System.out.println("Added Logs");
            ContextGetter(alt_num, flag, id);
            break;
        
        case 2:  //Update Record
            
            System.out.println("Options: ");
            System.out.println("1: Add Record");
            System.out.println("2: Remove Record");
            System.out.println("3: Alter Record");
            System.out.print("Choice: ");
            int opt = sc.nextInt();
            
            switch(opt){
                case 1: AddRecord(iid); break;
                case 2: RemoveRecord(iid); break;
                
                case 3: 
                    System.out.print("Enter Inmate Record ID: ");
                    int rec_id = sc.nextInt();

                    System.out.println(" ----- Alternation Options: ----- ");
                    System.out.println("1: Record Name");
                    System.out.println("2: Status");
                    System.out.println("3: Conviction Time");
                    System.out.println("4: Date Commision");
                    System.out.println("5: Case Status");
                    System.out.println("6: Exit");

                    System.out.print("Choice:  ");
                    alter = sc.nextInt();
                    
                    while(alter <= 0 || alter >= 7){
                        System.out.println("Invalid Choice: Enter Again: ");
                        alter = sc.nextInt();
                    }

                    switch (alter) {
                        case 1:
                            System.out.print("Enter new Record Name: ");
                            name = sc.next();
                            String sqlUpdateName = "UPDATE record SET r_name = ? WHERE r_id = ?";
                            config.updateRecord(sqlUpdateName, name, rec_id);
                            flag = 1;
                            alt_num = 11;
                            break;

                        case 2:
                            System.out.print("Enter new Record Status: ");
                            String stat = RecordStatus();
                            String sqlUpdateStatus = "UPDATE record SET r_stat = ? WHERE r_id = ?";
                            config.updateRecord(sqlUpdateStatus, stat, rec_id);
                            flag = 1;
                            alt_num = 12;
                            break;

                        case 3:
                            int convict_time = 0;
                            
                            System.out.print("Enter new Conviction Time: ");
                            int conv = sc.nextInt();
                            
                            sql = "SELECT i_impri FROM inmate WHERE i_id = ?";
                            int data = (int)config.getSingleData(sql, iid);
                            sql = "SELECT * FROM record WHERE r_id = ?";
                            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(sql, rec_id);
                            
                            if (result.isEmpty()) {
                                System.out.println("Error no Case File: ");
                                
                            } else {
                                java.util.Map<String, Object> user = result.get(0);
                                convict_time = ((Number) user.get("r_conviction_time")).intValue();
                            }
                            
                            int res = data - convict_time;
                            int final_res = res + conv;
                            
                            String sqlUpdateConviction = "UPDATE record SET r_conviction_time = ? WHERE r_id = ?";
                            config.updateRecord(sqlUpdateConviction, final_res, rec_id);
                            
                            flag = 1;
                            alt_num = 13;
                            break;
                        case 4:
                            System.out.print("Enter Date Commited: ");
                            String commit = sc.next();
                            String sqlUpdateCommit = "UPDATE record SET r_date_commited = ? WHERE r_id = ?";
                            config.updateRecord(sqlUpdateCommit, commit, rec_id);
                            flag = 1;
                            alt_num = 14;
                            break;
                        case 5: 
                            System.out.println("Select Info Status:");
                            System.out.println("1: Active Record");
                            System.out.println("2: Closed Record");
                            System.out.print("Choice: ");
                            int infoChoice = Validations.ChoiceValidation(1, 2);

                            stat = "";
                            switch(infoChoice){
                                case 1: stat = "Active Record";
                                    break;
                                case 2: stat = "Closed Record";
                                    break;
                            }
                            String sqlUpdateInfo = "UPDATE record SET r_recordStatus = ? WHERE r_id = ?";
                            config.updateRecord(sqlUpdateInfo, stat, rec_id);
                            flag = 1;
                            alt_num = 15;
                            break;
                        case 6: 
                            System.out.println("Exiting . . ."); 
                            return 0;

                        default:
                            System.out.println("Invalid option selected.");
                            break;
                    }
                    
                    if (flag == 1) {
                        System.out.println("Inmate Record Successfully Updated");
                    }
                    
                System.out.println("Added Logs");
                ContextGetter(alt_num, flag, iid);

                default:
                    System.out.println("Invalid Choice: ");
                    break;
            }
            
            break;
        }
        return 0;
        
    }
    
    public static void AddRecord(int inmateId) {
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        String recordName = "";
        int convictionTime = 0;

        System.out.println("\n---- Add Record Menu ----");
        System.out.print("Record Quantity: ");
        int recordCount = sc.nextInt();

        for (int i = 0; i < recordCount; i++) {
            System.out.print("\nEnter Record Name: ");
            recordName = sc.next();

            String status = RecordStatus();

            System.out.print("\nConviction Time: ");
            convictionTime = sc.nextInt();

            String sql = "INSERT INTO record(r_name, r_stat, r_conviction_time, r_date_commited, r_recordStatus, i_id) VALUES (?,?,?,?,?,?)";

            String recordVerdict = "Case Closed".equals(status) ? "Guilty" : "Not Guilty";
            config.addRecordAndReturnId(sql, recordName, status, convictionTime, Validations.Date(), recordVerdict, inmateId);
        }

        String getQtySql = "SELECT i_record_quan FROM inmate WHERE i_id = ?";
        Object singleData = config.getSingleData(getQtySql, inmateId);
        int recordQty = ((Number) singleData).intValue();
        int newQty = recordQty + recordCount;
        String updateSql = "UPDATE inmate SET i_record_quan = ? WHERE i_id = ?";
        config.updateRecord(updateSql, newQty, inmateId);
        
        getQtySql = "SELECT i_impri FROM inmate WHERE i_id = ?";
        Object singleData2 = config.getSingleData(getQtySql, inmateId);
        int recimpri = ((Number) singleData2).intValue();
        int newimpri = recimpri + convictionTime;
        updateSql = "UPDATE inmate SET i_impri = ? WHERE i_id = ?";
        config.updateRecord(updateSql, newimpri, inmateId);
        
        String sql = "INSERT INTO logs(i_id, u_id, h_name, h_context, h_date) VALUES (?,?,?,?,?)";
        config.addRecordAndReturnId(sql, inmateId, Session.getUserId(), recordName, "Added Record", Validations.Date());

    }
    
    public static void RemoveRecord(int iid){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        boolean cont = true;
        
        while(cont){
        System.out.println("\n----- Record Delete Menu -----");
            System.out.println("\n--------- (0 to exit) --------");
        System.out.print("Enter Record ID to delete: ");
        int rec_id = sc.nextInt();
        
        if(rec_id == 0){
            System.out.println("Exiting . . .");
            cont = false;
        }else{
            String deleteInmateInfo;
            deleteInmateInfo = "DELETE FROM record WHERE r_id = ?";
            config.deleteRecord(deleteInmateInfo, rec_id);

            String sql = "SELECT i_record_quan FROM inmate WHERE i_id = ?";
            Object singledata = config.getSingleData(sql, iid);

            int recordQty = ((Number) singledata).intValue();
            int res = recordQty - 1;

            sql = "UPDATE inmate SET i_record_quan = ? WHERE i_id = ?";
            config.updateRecord(sql, res, iid);
            
            sql = "SELECT i_name FROM inmate WHERE i_id = ?";
            java.util.List<java.util.Map<String, Object>> name = config.fetchRecords(sql, iid);
            java.util.Map<String, Object> user = name.get(0);
            String inmateName = user.get("i_name").toString();

            sql = "INSERT INTO logs(i_id, u_id, h_name, h_context, h_date) VALUES (?,?,?,?,?)";
            config.addRecordAndReturnId(sql, iid, Session.getUserId(), inmateName, "Delete Record", Validations.Date());
            }
        }
    }
    
    public static void ContextGetter(int alt_num, int flag, int id){
        conf config = new conf();
        
        String date = Validations.Date();
        
        String context = "", sql = "";
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
                case 10: context = "Altered Inmate Data Status"; break;
                case 11: context = "Altered Record Name"; break;
                case 12: context = "Altered Record Status"; break;
                case 13: context = "Altered Conviction Time"; break;
                case 14: context = "Altered Total Imprisonment"; break;
                case 15: context = "Altered Case Status"; break;
                default: context = "Invalid option"; break;
            }

            String in_name = "";
            if (flag == 1) {
                String sqlQuery = "SELECT * FROM inmate WHERE i_id = ?";
                List<Map<String, Object>> result = config.fetchRecords(sqlQuery, id);

                if (!result.isEmpty()) {
                    java.util.Map<String, Object> getname = result.get(0);
                    in_name = getname.get("i_name").toString();
                }

                sql = "INSERT INTO logs(h_name, h_date, h_context, i_id) VALUES(?,?,?,?)";
                config.addRecordAndReturnId(sql, in_name, date, context, id);
            }
    }
}
