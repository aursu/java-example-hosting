package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Hoster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HosterId", nullable = false)
    private Integer id;

    @Column(name = "Password", length = 40)
    private String password;

    @Column(name = "Company", length = 80)
    private String company;

    @Column(name = "Address1", length = 70)
    private String address1;

    @Column(name = "Address2", length = 70)
    private String address2;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "State", length = 40)
    private String state;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "AltPhone", length = 24)
    private String altPhone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Column(name = "Email", nullable = false, length = 60)
    private String email;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "SuspendDate")
    private LocalDateTime suspendDate;

    @Column(name = "DNS1_Host", length = 100)
    private String dnsHost1;

    @Column(name = "DNS1_IP", length = 16)
    private String dnsIp1;

    @Column(name = "DNS2_Host", length = 100)
    private String dnsHost2;

    @Column(name = "DNS2_IP", length = 16)
    private String dnsIp2;

    @Column(name = "Mail_Host", length = 100)
    private String mailHost;

    @Column(name = "Mail_IP", length = 16)
    private String mailIp;

    @Column(name = "MySQL_Host", length = 100)
    private String mysqlHost;

    @Column(name = "MySQL_IP", length = 16)
    private String mysqlIp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ClusterId", nullable = false)
    private Cluster cluster;

    @OneToMany(mappedBy = "hoster")
    private Set<Customer> customers = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getSuspendDate() {
        return suspendDate;
    }

    public void setSuspendDate(LocalDateTime suspendDate) {
        this.suspendDate = suspendDate;
    }

    public String getDnsHost1() {
        return dnsHost1;
    }

    public void setDnsHost1(String dnsHost1) {
        this.dnsHost1 = dnsHost1;
    }

    public String getDnsIp1() {
        return dnsIp1;
    }

    public void setDnsIp1(String dnsIp1) {
        this.dnsIp1 = dnsIp1;
    }

    public String getDnsHost2() {
        return dnsHost2;
    }

    public void setDnsHost2(String dnsHost2) {
        this.dnsHost2 = dnsHost2;
    }

    public String getDnsIp2() {
        return dnsIp2;
    }

    public void setDnsIp2(String dnsIp2) {
        this.dnsIp2 = dnsIp2;
    }

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getMailIp() {
        return mailIp;
    }

    public void setMailIp(String mailIp) {
        this.mailIp = mailIp;
    }

    public String getMysqlHost() {
        return mysqlHost;
    }

    public void setMysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;
    }

    public String getMysqlIp() {
        return mysqlIp;
    }

    public void setMysqlIp(String mysqlIp) {
        this.mysqlIp = mysqlIp;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

}