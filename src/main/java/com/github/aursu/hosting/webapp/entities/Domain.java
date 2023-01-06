package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class Domain {
    @Id
    @Column(name = "Domain", nullable = false)
    private String id;

    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Password")
    private String password;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "SuspendDate")
    private LocalDateTime suspendDate;

    @Column(name = "ExpireDate")
    private LocalDateTime expireDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ProductCode", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "domain", fetch = FetchType.EAGER)
    private List<DomainHostingService> domainServices = new ArrayList<>();

/*    @ManyToMany(fetch = FetchType.EAGER)
 *    @JoinTable(name = "DomainService",
 *            joinColumns = @JoinColumn(name = "Domain"),
 *            inverseJoinColumns = @JoinColumn(name = "Product", referencedColumnName="Code"))
 *    private List<Product> services = new ArrayList<>(); */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PrimaryDomain")
    private Domain primaryDomain;

    public Domain getPrimaryDomain() {
        return primaryDomain;
    }

    public void setPrimaryDomain(Domain primaryDomain) {
        this.primaryDomain = primaryDomain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DomainHostingService> getDomainServices() {
        return domainServices;
    }

    public void setDomainServices(List<DomainHostingService> domainServices) {
        this.domainServices = domainServices;
    }

}