package ai.tech5.t5billing;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.tech5.t5billing.entity.TransactionEntry;
import ai.tech5.t5billing.jpa.TransactionEntryRepository;


@RestController
public class Controller {

    private final Logger logger = Logger.getLogger("T5Billing");

    private final String utcDate = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";

    private final DateFormat dateFormat = new SimpleDateFormat(utcDate);

    @Autowired
    TransactionEntryRepository repository;

    @RequestMapping("/version")
    public String getVersion() {

        return "TECH5 Billing Service 1.0";
    }

    @GetMapping("/transaction")
    public boolean addTransactionBillingEntry(
                        HttpServletRequest request,
                        @RequestParam String tid,
                        @RequestParam String time,
                        @RequestParam String cid,
                        @RequestParam String pid,
                        @RequestParam String appid,
                        @RequestParam String type,
                        @RequestParam String count,
                        @RequestParam String host,
                        @RequestParam String cip) {

        TransactionEntry entry = new TransactionEntry();
        entry.setTransactionId(tid);
        entry.setTransactionTime(getDateFromParameter(time, tid));
        entry.setCustomerId(cid);
        entry.setProjectId(pid);
        entry.setApplicationId(appid);
        entry.setTransactionType(type);
        entry.setTransactionsCount(getTransactionsCount(count, tid));
        entry.setHostName(host);
        entry.setClientIp(cip);
        entry.setRequestIp(request.getRemoteAddr());

        repository.save(entry);

        return true;
    }

    private Date getDateFromParameter(String time, String transactionId) {

        Date date = new Date();

        try {

            date = dateFormat.parse(time);

        } catch (Exception e) {

            logger.log(Level.INFO, transactionId + "Transaction " + transactionId + " -> timestamp format was not recognized: " + time + ": The billing transaction records was created using the Billing Server current time.");
        }

        return date;
    }

    private int getTransactionsCount(String count, String transactionId) {

        int transactionsCount = 0;

        try {

            transactionsCount = Integer.parseInt(count);

        }
        catch (Exception e)
        {
            logger.log(Level.INFO, transactionId + "Transaction " + transactionId + " -> count number was not successfully parsed: " + count + ": The billing transaction record was created with zero transactions.");
        }

        return transactionsCount;
    }
}
