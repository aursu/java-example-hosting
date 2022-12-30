package com.github.aursu.hosting.webapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Cluster {
    @Id
    @Column(name = "ClusterId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 10)
    private String name;

    @Column(name = "Region", nullable = false, length = 32)
    private String region;

    @Column(name = "WebMailDomain", nullable = false, length = 128)
    private String webMailDomain;

    @Column(name = "MySQLServer", nullable = false, length = 128)
    private String mysqlServer;

    @Column(name = "FTPServer", nullable = false, length = 128)
    private String ftpServer;

    @Column(name = "PanelDomain", nullable = false, length = 128)
    private String panelDomain;

    @Column(name = "ShellServer", nullable = false, length = 128)
    private String shellServer;

    @OneToMany(mappedBy = "cluster")
    private Set<Hoster> hosters = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWebMailDomain() {
        return webMailDomain;
    }

    public void setWebMailDomain(String webMailDomain) {
        this.webMailDomain = webMailDomain;
    }

    public String getMysqlServer() {
        return mysqlServer;
    }

    public void setMysqlServer(String mysqlServer) {
        this.mysqlServer = mysqlServer;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getPanelDomain() {
        return panelDomain;
    }

    public void setPanelDomain(String panelDomain) {
        this.panelDomain = panelDomain;
    }

    public String getShellServer() {
        return shellServer;
    }

    public void setShellServer(String shellServer) {
        this.shellServer = shellServer;
    }

    public Set<Hoster> getHosters() {
        return hosters;
    }

    public void setHosters(Set<Hoster> hosters) {
        this.hosters = hosters;
    }

}