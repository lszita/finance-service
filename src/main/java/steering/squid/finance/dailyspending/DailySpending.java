package steering.squid.finance.dailyspending;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DailySpending {
    
    private Long amount;
    @Temporal(TemporalType.DATE)
    private Date day;

    public DailySpending(Long amount, Date day) {
        this.amount = amount;
        this.day = day;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
