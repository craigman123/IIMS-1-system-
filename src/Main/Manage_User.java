
package Main;

import Config.Validations;
import Config.conf;
import java.util.Scanner;

public class Manage_User {
    
    public static void ManageUsers(){ //Only for Higher Officials and Developers
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        
        while(run){
        System.out.println("\n===== User Manager =====");
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
            case 4: UniversalDataDisplay.ShowallUser();
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
    
    public static int UpdateUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        boolean running = true, run = true;
        
        String pos = "", stat = "";
        System.out.println("====== UPDATE USER MENU ======");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        
        if(choice == 0){
            System.out.print("Exiting not allowed: ");
            choice = Validations.ChoiceValidation(1,2);
        }
        
        if(choice == 1){
            UniversalDataDisplay.ShowallUser();
        }
        
        System.out.print("Enter User ID to alter: ");
        int u_id = Validations.IntegerValidation();
        
        if(u_id == 0){
            Validations.ExitTrigger(u_id);
            return 0;
        }
        
        while(run){
            
            String qry = "SELECT * FROM users WHERE u_id = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, u_id);
            
            if (result.isEmpty()) {
                    System.out.println("\n ----- ID NOT FOUND ----- ");
                    System.out.print("Enter Again: ");
                    u_id = Validations.IntegerValidation();
                    if(u_id == 0){
                        Validations.ExitTrigger(u_id);
                        return 0;
                    }

            }else{
                run = false;
                }
            }
        
        while (running) {
        System.out.println("\n----- Alternation Options -----");
        System.out.println("1: User Name");
        System.out.println("2: Password");
        System.out.println("3: Position");
        System.out.println("4: Badge");
        System.out.println("5: Status");
        System.out.println("6: Back / Exit");
        System.out.print("Choose an option to change (1-6): ");

        int option = Validations.ChoiceValidation(1, 6);

        switch (option) {
            case 1:
                System.out.print("Enter new Name: ");
                String newName = Validations.StringValidation();

                String sqlName = "UPDATE users SET u_name = ? WHERE u_id = ?";
                config.updateRecord(sqlName, newName, u_id);

                String sqlAdminName = "UPDATE admin SET user_name = ? WHERE user_badge = (SELECT u_badge FROM users WHERE u_id = ?)";
                config.updateRecord(sqlAdminName, newName, u_id);

                System.out.println("Name updated successfully.");
                break;

            case 2: 
                System.out.print("Enter new Password: ");
                String newPass = sc.next();

                String hashPass = config.hashPassword(newPass);

                String sqlPass = "UPDATE users SET u_pass = ? WHERE u_id = ?";
                config.updateRecord(sqlPass, hashPass, u_id);

                String sqlAdminPass = "UPDATE admin SET user_true_pass = ? WHERE user_badge = (SELECT u_badge FROM users WHERE u_id = ?)";
                config.updateRecord(sqlAdminPass, newPass, u_id);

                System.out.println("Password updated successfully.");
                break;

            case 3: 
                System.out.println("Enter Position: ");
                System.out.println("1: Behavioral Staff's");
                System.out.println("2: Higher Officials");
                System.out.println("3: Staff");
                System.out.println("4: Legal Authorities");
                System.out.print("Choice: ");
                int ps = Validations.ChoiceValidation(1,4);
                
                if(ps == 0){
                    System.out.println("Caancellations not allowed: ");
                    ps = Validations.ChoiceValidation(1,4);
                }
                switch(ps){
                    case 1: pos = "Behavioral Staff's"; break;
                    case 2: pos = "Higher Officials"; break;
                    case 3: pos = "Staff"; break;
                    case 4: pos = "Legal Authorities"; break;
                }

                String sqlPos = "UPDATE users SET u_position = ? WHERE u_id = ?";
                config.updateRecord(sqlPos, pos, u_id);

                System.out.println("Position updated successfully.");
                break;

            case 4:
                System.out.print("Enter new Badge Number: ");
                int newBadge = Validations.IntegerValidation();

                while (true) {
                    String qry = "SELECT * FROM users WHERE u_badge = ?";
                    java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, newBadge);

                    if (result.isEmpty()) {
                        break;
                    } else {
                        Object existingIdObj = result.get(0).get("u_id");
                        int existingId = -1;
                        if (existingIdObj instanceof Number) {
                            existingId = ((Number) existingIdObj).intValue();
                        } else {
                            try { existingId = Integer.parseInt(String.valueOf(existingIdObj)); } catch (Exception e) { existingId = -1; }
                        }
                        if (existingId == u_id) {
                            break;
                        }

                        System.out.print("User Badge already exists, Enter Again: ");
                        newBadge = Validations.IntegerValidation();
                    }
                }

                String sqlBadge = "UPDATE users SET u_badge = ? WHERE u_id = ?";
                config.updateRecord(sqlBadge, newBadge, u_id);

                String sqlAdminBadge = "UPDATE admin SET user_badge = ? WHERE user_name = (SELECT u_name FROM users WHERE u_id = ?)";
                config.updateRecord(sqlAdminBadge, newBadge, u_id);

                System.out.println("Badge updated successfully.");
                break;

            case 5:
                System.out.println("Enter Status: ");
                System.out.println("1: Approved");
                System.out.println("2: Pending");
                System.out.println("3: Disabled");
                System.out.print("Choice: ");
                int status = Validations.ChoiceValidation(1,3);
                
                if(status == 0){
                    System.out.println("Caancellations not allowed: ");
                    ps = Validations.ChoiceValidation(1,4);
                }
                
                switch(status){
                    case 1: stat = "Approved"; break;
                    case 2: stat = "Pending"; break;
                    case 3: stat = "Disabled"; break;
                }

                String sqlStatus = "UPDATE users SET u_approval = ? WHERE u_id = ?";
                config.updateRecord(sqlStatus, stat, u_id);
                System.out.println("Status updated successfully.");
                break;

            case 6:
                running = false;
                System.out.println("Returning to previous menu...");
                break;

            default:
                System.out.println("Invalid choice. Try again.");
                break;
            }
        }
        return 0;
    }
    
    public static void addUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        String stat = "", ps = "";
        
        System.out.println("\n===== Add User =====");
        
        System.out.print("Enter Name: ");
        String name = Validations.StringValidation();
        
        System.out.print("Enter Password: ");
        String pass = sc.next();
        
        String hashPass = config.hashPassword(pass);
        
        System.out.print("Enter Badge: ");
        int badge = Validations.IntegerValidation();
        
        while (true) {
                            
            String qry = "SELECT * FROM users WHERE u_badge = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, badge);

            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("User Badge already exists, Enter Again: ");
                badge = Validations.IntegerValidation();
            }
        }
        
        System.out.println("\n===== Position: ===== ");
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
        
        System.out.println("\n ========================");
        System.out.println("1: Approved");
        System.out.println("2: Pending");
        System.out.println("3: Disabled");
        System.out.print("Account Status: ");
        int status = Validations.ChoiceValidation(1,3);
        
        switch(status){
            case 1: stat = "Approved"; break;
            case 2: stat = "Pending"; break;
            case 3: stat = "Disabled"; break;
            default:
                System.out.println("Invalid Status: ");
        }
        
        String sql = "INSERT INTO users(u_name, u_pass, u_position, u_badge, u_approval, date_added) VALUES (?,?,?,?,?,?)";
        config.addRecordAndReturnId(sql, name, hashPass, ps, badge, stat, Validations.Date());
        
        sql = "INSERT INTO admin(user_name, user_true_pass, user_badge ) VALUES (?,?,?)";
        int get = config.addRecordAndReturnId(sql, name, pass, badge);
        
        if(get > 0){
            System.out.println("User Succesfully Added: ");
        }
    }
    
    public static int DelUser(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        boolean run = true;
        String position = "";
        int flag = 1;
        System.out.println("==== REMOVE USER MENU ====");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        
        if(choice == 0){
            Validations.ExitTrigger(choice);
            return 0;
        }
        
        if(choice == 1){
            UniversalDataDisplay.ShowallUser();
        }
        
        do{
        System.out.print("Enter User ID to Remove: ");
        int u_id = Validations.IntegerValidation();
        
        if(u_id == 0){
            Validations.ExitTrigger(u_id);
            return 0;
        }
        
        while(run){
            
            String qry = "SELECT * FROM users WHERE u_id = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, u_id);
            
            if (result.isEmpty()) {
                    System.out.println("\n ----- ID NOT FOUND ----- ");
                    System.out.print("Enter Again: ");
                    u_id = Validations.IntegerValidation();
                    if(u_id == 0){
                        Validations.ExitTrigger(u_id);
                        return 0;
                    }
            }else{
                run = false;
                }
            }
        
        String DisallowDeleteAdmin = "SELECT * FROM users WHERE u_id = ?";
        java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(DisallowDeleteAdmin, u_id);
        
        if (!result.isEmpty()) {
            java.util.Map<String, Object> user = result.get(0);
            position = user.get("u_position").toString();
        }
        
        if("admin".equals(position)){
            System.out.println("This user cannot be deleted: ");
            System.out.println("Enter Another: \n");
        } else {
            flag = 0;
            String deleteInmateInfo = "DELETE FROM users WHERE u_id = ?";
            config.deleteRecord(deleteInmateInfo, u_id);
            
            deleteInmateInfo = "DELETE FROM admin WHERE u_id = ?";
            config.deleteRecord(deleteInmateInfo, u_id);
            }
        }while(flag == 1);
        
        return 0;
    }
    
    public static int UserAccess(){
        
        conf config = new conf();
        Scanner sc = new Scanner(System.in);
        String stat = "";
        boolean run = true;
        System.out.println("==== APPROVAL MENU ====");
        
        System.out.println("Do you want to view User Records?");
        System.out.println("1: YES");
        System.out.println("2: NO");
        System.out.print("Choice: ");
        int choice = Validations.ChoiceValidation(1,2);
        if (choice == 0){
            Validations.ExitTrigger(choice);
            return 0;
        }
        
        if(choice == 1){
        
        String sqlQuery = "SELECT u_id, u_name, u_position, u_approval FROM users";
        
        String[] headers = {"ID", "User Name", "Position", "Status"};
        
        String[] cols = {"u_id", "u_name", "u_position", "u_approval"};
        
        config.viewRecords(sqlQuery, headers, cols);
        }
        
        System.out.print("Enter ID to Validate System Access: ");
        int id = Validations.IntegerValidation();
        if (id == 0){
            Validations.ExitTrigger(id);
            return  0;
        }
        
        while(run){
            
            String qry = "SELECT * FROM users WHERE u_id = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, id);
            
            if (result.isEmpty()) {
                    System.out.println("\n ----- ID NOT FOUND ----- ");
                    System.out.print("Enter Again: ");
                    id = Validations.IntegerValidation();
                    if (id == 0){
                        Validations.ExitTrigger(id);
                        return  0;
                    }

            }else{
                run = false;
                }
            }
        
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
        return 0;
    }
}
