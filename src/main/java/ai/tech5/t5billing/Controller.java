package ai.tech5.t5billing;

import ai.tech5.t5billing.entity.TransactionEntry;
import ai.tech5.t5billing.jpa.TransactionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class Controller {
    private static Logger logger = Logger.getLogger("T5Billing");
    private static String strDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    private static DateFormat df = new SimpleDateFormat(strDateFormat);
    @Autowired
    TransactionEntryRepository repository;
    @RequestMapping("/version")
    public String getVersion() {

        return "TECH5 Billing Service 1.0";
    }

    @GetMapping("/transaction")
    public boolean addTransactionBillingEntry(HttpServletRequest request,
                                              @RequestParam String tid,
                                              @RequestParam String time,
                                              @RequestParam String cid,
                                              @RequestParam String appId,
                                              @RequestParam String nt,
                                              @RequestParam String host)
    {
        TransactionEntry entry = new TransactionEntry();
        entry.setTransactionId(tid);
        entry.setClientId(cid);
        //entry.setTransactionStatus(sc);
        entry.setApplicationId(appId);
        entry.setClientInfo(request.getRemoteAddr());
        //entry.setTransactionType(trnType);
        entry.setHost(host);
        entry.setClientIP(request.getRemoteAddr());
        int noOfTransactions = 1;
        try {
            Integer.parseInt(nt);
        }
        catch (Exception e)
        {

        }
        entry.setNumberOfTransactions(noOfTransactions);
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.log(Level.INFO, tid + " Transaction timestamp value is not in format as : " + strDateFormat + " . using current time as a transaction timestamp.");
            date = new Date();
        }
        entry.setTransactionTime(date);
        repository.save(entry);
        logger.log(Level.FINEST, tid + " Transaction stored successfully.");
        return true;
    }
}
