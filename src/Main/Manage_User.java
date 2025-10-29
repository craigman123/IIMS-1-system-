
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
        
        System.out.println("\n===== Add User =====");
        
        System.out.print("Enter Name: ");
        String name = sc.next();
        
        System.out.print("Enter Password: ");
        String pass = sc.next();
        
        String hashPass = config.hashPassword(pass);
        
        System.out.print("Enter Badge: ");
        String badge = sc.next();
        
        while (true) {
                            
            String qry = "SELECT * FROM users WHERE u_badge = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, badge);

            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("User Badge already exists, Enter Again: ");
                badge = sc.next();
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
        
        String sql = "INSERT INTO users(u_name, u_pass, u_position, u_badge, u_approval) VALUES (?,?,?,?,?)";
        config.addRecordAndReturnId(sql, name, hashPass, ps, badge, stat);
        
        sql = "INSERT INTO admin(user_name, user_true_pass, user_badge ) VALUES (?,?,?)";
        int get = config.addRecordAndReturnId(sql, name, pass, badge);
        
        if(get > 0){
            System.out.println("User Succesfully Added: ");
        }
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
        
        deleteInmateInfo = "DELETE FROM admin WHERE u_id = ?";
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
}
