package src;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;  


public class PrintFile {
    private final int LONG_DATE=20;
    private final int LONG_TIME=15;
    private final int LONG_STATUS=20;
    private final int LONG_TAGIHAN=20;
    private final int LONG_ADMIN=10;
    private final int LONG_TRANSAKSI=20;


    private String getCurrentDate(){
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();  
        return dtm.format(now);
    }

    private String printFormat(int longChar, String word, String schema){
        int inputLongWord = word.length();
        String append = new String();
        for (int i=0;i<longChar-inputLongWord;i++){
            append+=" ";
        }
        switch (schema) {
            case "rps":
                return word+append;
            case "lps":
                return append+word;
            default:
                return word;

        }
    }

    private void printHeader (FileWriter myWriter){
         try {
            myWriter.write("KODE CLIENT : JLN                        LAPORAN                               HAL        : 1");
            myWriter.write(System.lineSeparator());
            myWriter.write("KODE REPORT : ACT-01                  TRANSAKSI PEMBAYARAN BERHASILS           Tanggal    : "+this.getCurrentDate());
            myWriter.write(System.lineSeparator());
            myWriter.write(System.lineSeparator());
            myWriter.write(printFormat(LONG_DATE, "Tanggal","rps") + printFormat(LONG_TIME, "Waktu","rps") + printFormat(LONG_STATUS, "Status","rps") + printFormat(LONG_TAGIHAN, "Nominal","rps" )+ printFormat(LONG_ADMIN, "Admin","rps") + printFormat(LONG_TRANSAKSI, "Nominal","lps"));
            myWriter.write(System.lineSeparator());
            myWriter.write(printFormat(LONG_DATE, "Transaksi","rps") + printFormat(LONG_TIME, "Transaksi","rps") + printFormat(LONG_STATUS, "Transaksi","rps") + printFormat(LONG_TAGIHAN, "Tagihan","rps") + printFormat(LONG_ADMIN, "","rps" )+ printFormat(LONG_TRANSAKSI, "Transaksi","lps"));


         } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
         }
    }

    private void printTransaction(FileWriter myWriter, List<Bank> listBank){
        try {
            for(Bank bank:listBank){
                myWriter.write(System.lineSeparator());
                myWriter.write(
                    printFormat(LONG_DATE, convertDateFormat(bank.transaction_date),"rps") + 
                    printFormat(LONG_TIME, bank.transaction_time.toString(),"rps") + 
                    printFormat(LONG_STATUS, bank.transaction_status.toString(),"rps") + 
                    printFormat(LONG_TAGIHAN, String.valueOf(bank.transaction_amount),"rps") + 
                    printFormat(LONG_ADMIN,String.valueOf(bank.transaction_fee),"rps" )+
                    printFormat(LONG_TRANSAKSI, String.valueOf(bank.transaction_amount+bank.transaction_fee),"lps")
                );

            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void printFooter(FileWriter myWriter, List<Bank> listBank){
        int totalTransaksi = 0;
        int nominalTagihan = 0;
        int adminFee =0;

        for(Bank bank:listBank){
            totalTransaksi+=1;
            nominalTagihan+=bank.transaction_amount;
            adminFee+=bank.transaction_fee;
        }


        try {
            myWriter.write(System.lineSeparator());
            myWriter.write(System.lineSeparator());
            myWriter.write("                                    * * * SUMMARY TOTAL * * *                     ");
            myWriter.write(System.lineSeparator());
            myWriter.write("                              TOTAL TRANSAKSI          ="+totalTransaksi);
            myWriter.write(System.lineSeparator());
            myWriter.write("                              TOTAL NOMINAL TAGIHAN    ="+nominalTagihan);
            myWriter.write(System.lineSeparator());
            myWriter.write("                              TOTAL ADMIN FEE          ="+adminFee);
            myWriter.write(System.lineSeparator());
            myWriter.write("                              TOTAL NOMINAL TRANSAKSI  ="+(nominalTagihan+adminFee));
            myWriter.write(System.lineSeparator());
            
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String convertDateFormat(Date date) {
        try {
            SimpleDateFormat dateFormatOld = new SimpleDateFormat("YYYY-MM-DD");
            SimpleDateFormat dateFormatNew = new SimpleDateFormat("DDMMYYYY");
            java.util.Date dateJava = dateFormatOld.parse(date.toString());
            return dateFormatNew.format(dateJava);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public void print(List<Bank> listBank){
        try {
            FileWriter myWriter = new FileWriter("Output/output.txt");
            this.printHeader(myWriter);
            this.printTransaction(myWriter,listBank);
            this.printFooter(myWriter, listBank);
            myWriter.close();
            
        } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    }




}
