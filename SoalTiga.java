import src.Bank;
import src.CreateConnection;
import src.PrintFile;

import java.util.*;

import javax.swing.text.Style;  


class SoalTiga{
    public static void main(String[] args){
        CreateConnection createConnection = new CreateConnection();

        // List<Bank> listBank = createConnection.Get("transaction_status = 'success'"); 
        PrintFile pf = new PrintFile();
        
        Scanner scanner =  new Scanner(System.in);
        int input = 0;

        System.out.println("Masukan Filter yang Diiginkan");
        System.out.println("---------------------------------------------");
        System.out.println("1. PrintAll Transaction");
        System.out.println("2. Get All Negative Transaction");
        System.out.println("3. Get All where amout >= 100000");
        // System.out.println("4. Custom query");
        System.out.print("Input :");

        input = scanner.nextInt();

        switch (input) {
            case 1:
                pf.print(createConnection.Get());
                System.out.println("1. PrintAll Transaction Done");
                return;
            case 2:
               pf.print(createConnection.Get("transaction_status = 'failed'"));
               System.out.println("2. Get All Negative Transaction Done");
               return;
            case 3:
               pf.print(createConnection.Get("transaction_amount >=100000"));
               System.out.println("3. Get All where amount >= 100000 Done");
               return;
            // case 4:
            //     System.out.println("Masukan Query anda");
            //     String query = scanner.next();
            //     pf.print(createConnection.Get(query));
            //     System.out.println("4. Custom query");
            //     return;
        
            default:
                System.out.println("Not Found");

                return;
        }



        
    }
}