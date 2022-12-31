package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Code", nullable = false, length = 32)
    private String id;

    @Column(name = "Name", nullable = false, length = 64)
    private String name;


    @Column(name = "Type", nullable = false)
    private String type;


    @Column(name = "Billing")
    private String billing;


    @Column(name = "Description")
    private String description;

    @Column(name = "Price", nullable = false)
    private Float price;

    @Column(name = "Mailboxes", columnDefinition = "INT UNSIGNED not null")
    private Long mailboxes;

    @Column(name = "Quota", columnDefinition = "INT UNSIGNED not null")
    private Long quota;

    @Column(name = "TransferLimit", columnDefinition = "INT UNSIGNED not null")
    private Long transferLimit;

    @Column(name = "MailQuota", columnDefinition = "INT UNSIGNED not null")
    private Long mailQuota;

    @Column(name = "FtpUserLimit", columnDefinition = "INT UNSIGNED not null")
    private Long ftpUserLimit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getMailboxes() {
        return mailboxes;
    }

    public void setMailboxes(Long mailboxes) {
        this.mailboxes = mailboxes;
    }

    public Long getQuota() {
        return quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public Long getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Long transferLimit) {
        this.transferLimit = transferLimit;
    }

    public Long getMailQuota() {
        return mailQuota;
    }

    public void setMailQuota(Long mailQuota) {
        this.mailQuota = mailQuota;
    }

    public Long getFtpUserLimit() {
        return ftpUserLimit;
    }

    public void setFtpUserLimit(Long ftpUserLimit) {
        this.ftpUserLimit = ftpUserLimit;
    }

}