package ai.tech5.t5billing.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transactions")
public class TransactionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    @Column(name = "no_of_transactions", nullable = true )
    private int numberOfTransactions = 1;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "transaction_status", nullable = true)
    private String transactionStatus;
    @Column(name = "application_id", nullable = false)
    private String applicationId;
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;
    @Column(name = "transaction_time", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date transactionTime;
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date createdAt = new Date();
    @Column(name = "client_info", nullable = true)
    private String clientInfo;

    @Column(name = "host", nullable = true)
    private String host;

    @Column(name = "client_ip", nullable = true)
    private String clientIP;



    @Column(name = "transaction_type", nullable = true)
    private String transactionType;

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
