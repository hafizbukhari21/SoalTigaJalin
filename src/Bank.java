package src;
import java.sql.Date;
import java.sql.Time;

public class Bank {
    public Date transaction_date;
    public Time transaction_time;
    public String transaction_status;
    public int transaction_amount;
    public int transaction_fee; 

    Bank(Date transaction_date, Time transaction_time, String transaction_status, int transaction_amount, int transaction_fee){
        this.transaction_date = transaction_date;
        this.transaction_time = transaction_time;
        this.transaction_status = transaction_status;
        this.transaction_amount = transaction_amount;
        this.transaction_fee = transaction_fee;
    }
}
