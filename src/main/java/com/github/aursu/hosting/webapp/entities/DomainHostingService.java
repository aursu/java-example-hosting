package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "DomainService")
@NamedQueries({
        @NamedQuery(name = "DomainHostingService.findByDomain", query = "select d from DomainHostingService d where d.domain = :domain"),
        @NamedQuery(name = "DomainHostingService.findByDomainName", query = "select d from DomainHostingService d where d.domain.id = :domain")
})
public class DomainHostingService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Price", nullable = false)
    private Float price;

    @Column(name = "Quantity", columnDefinition = "INT UNSIGNED not null")
    private Integer quantity;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "SuspendDate")
    private LocalDateTime suspendDate;

    @Column(name = "ExpireDate")
    private LocalDateTime expireDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Domain", nullable = false)
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}