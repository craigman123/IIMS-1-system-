
package Main;

import Config.Validations;
import Config.conf;
import java.util.Scanner;
import java.time.LocalDate;

public class Log_Reg {
    
    public static void Register(){
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        int sec_code = 123456;
        String log_role = "";
        
        System.out.println("\n ----- Register Menu -----");
        
        System.out.print("Enter User Name: ");
        String name = sc.next();

        System.out.print("Enter Password: ");
        String pass = sc.next();

        String HashPass = config.hashPassword(pass);

        System.out.print("Badge ID: ");
        String badge = sc.next();

        while (true) {

            String qry = "SELECT * FROM users WHERE u_badge = ?";
            java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, badge);

            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("Badge Number already exists, Enter Again: ");
                badge = sc.next();
            }
        }

        int pos;
            System.out.println("\n ----- Position: ----- ");
            System.out.println("1: Behavioral Staff's");
            System.out.println("2: Higher Officials");
            System.out.println("3: Staff");
            System.out.println("4: Legal Authorities");
            System.out.println("=====================");

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

        String sql = "INSERT INTO users(u_name, u_pass, u_position, u_badge, u_approval, date_added) VALUES (?,?,?,?,?,?)";
        int u_id = config.addRecordAndReturnId(sql, name, HashPass, log_role, badge, "Pending", Date());

        sql = "INSERT INTO admin(u_id, user_name, user_true_pass, user_badge) VALUES (?,?,?,?)";
        config.addRecord(sql, u_id, name, pass, badge);

    }
    
    public static void LogIn(){
        
        String log_role = "";
        int flag = 0, pos;
        Scanner sc = new Scanner(System.in);
        conf config = new conf();
        boolean run = true;
        
        System.out.println("\n ----- Log In Menu -----");
        
        do{
            System.out.print("Enter User Name: ");
            String log_name = sc.next();

            System.out.print("Enter Password: ");
            String log_pass = sc.next();

            String hashPass = config.hashPassword(log_pass);

            System.out.print("Badge ID: ");
            String log_badge = sc.next();

            while (run) {
                String qry = "SELECT * FROM users WHERE u_name = ? AND u_pass = ? AND u_badge = ?";
                java.util.List<java.util.Map<String, Object>> result = config.fetchRecords(qry, log_name, hashPass, log_badge);
                
                if (result.isEmpty()) {
                    System.out.println("INVALID CREDENTIALS");
                    System.out.println("Enter Again: ");
                    flag = 1;
                    break;
                } else {
                    java.util.Map<String, Object> user = result.get(0);
                    String stat = user.get("u_approval").toString();
                    String posi = user.get("u_position").toString();
                    
                    
                    switch (stat) {
                        case "Pending":
                            System.out.println("Account is Pending, Contact the Higher Officials!");
                            run = false;
                            break;
                        case "Disabled":
                            System.out.println("Account Disabled: Contact Admin");
                            run = false;
                            break;
                        default:
                            System.out.println("LOGIN SUCCESS!");
                            flag = 0;
                            switch (posi) {
                                case "Behavioral Staff's":
                                    Bh behavioral = new Bh();
                                    behavioral.Behavioral();
                                    
                                    break;
                                case "Higher Officials":
                                    Ho higher = new Ho();
                                    higher.Higher();
                                    
                                    break;
                                case "Staff":
                                    Staff st = new Staff();
                                    st.Staff();
                                    
                                    break;
                                case "Legal Authorities":
                                    La authorities = new La();
                                    authorities.Authorities();
                                    
                                    break;
                            }
                            break;
                    }
                }
            }
        }while(flag == 1);
    }
    
    public static String Date(){
        Object c_date = LocalDate.now();
        String date = c_date.toString();
        
        return date;
    }
}
