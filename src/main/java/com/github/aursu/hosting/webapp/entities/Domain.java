package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Domain", nullable = false)
    private String id;

    @Lob
    @Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Password")
    private String password;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "StartDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "SuspendDate")
    private LocalDateTime suspendDate;

    @Column(name = "ExpireDate")
    private LocalDateTime expireDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductCode", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "domain")
    private Set<DomainService> domainServices = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Set<DomainService> getDomainServices() {
        return domainServices;
    }

    public void setDomainServices(Set<DomainService> domainServices) {
        this.domainServices = domainServices;
    }

}