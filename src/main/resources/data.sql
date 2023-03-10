INSERT IGNORE INTO `Cluster` VALUES (0,'c0','Toronto','webmailc0.mailhosting.com','sqlc0.webhostig.com','hostingc0.webhosting.com','panelc0.webhosting.com','sshc0.webhosting.com');
INSERT IGNORE INTO `Cluster` VALUES (1,'c1','Toronto','webmailc1.mailhosting.com','sqlc1.webhostig.com','hostingc1.webhosting.com','panelc1.webhosting.com','sshc1.webhosting.com');
INSERT IGNORE INTO `Cluster` VALUES (2,'c2','Toronto','webmailc2.mailhosting.com','sqlc2.webhostig.com','hostingc2.webhosting.com','panelc2.webhosting.com','sshc2.webhosting.com');
INSERT IGNORE INTO `Cluster` VALUES (5,'c5','Florida','webmailc5.mailhosting.com','sqlc5.webhostig.com','hostingc5.webhosting.com','panelc5.webhosting.com','sshc5.webhosting.com');
INSERT IGNORE INTO `Cluster` VALUES (15,'c15','Frankfurt','webmailc15.mailhosting.de','sqlc15.webhostig.de','hostingc15.webhosting.de','panelc15.webhosting.de','sshc15.webhosting.de');
INSERT IGNORE INTO `Cluster` VALUES (25,'c25','Ireland','webmailc25.mailhosting.eu','sqlc25.webhostig.eu','hostingc25.webhosting.eu','panelc25.webhosting.eu','sshc25.webhosting.eu');

INSERT IGNORE INTO `Hoster` VALUES (400,'5KnTyy5aUhpQ9hjetH7E','Telus Communications Inc.','Attn: Laura Callahan','10405 104 Ave NW','Edmonton','Alberta','Canada','T5J 5G5','780-428-0764',NULL,NULL,'hosting@telus.com','OK','Regular','2013-09-24 00:00:00',NULL,'dns48.telus.ca','209.171.204.48','dns48.telus.com','209.171.203.48','mail56.telus.com','209.171.205.56','sql68c2.telus.com','209.171.205.68',2);
INSERT IGNORE INTO `Hoster` VALUES (401,'pu73tr9XWx4fm92wgD26','Network Solutions Inc.','Attn: Robert King','9640 Commerce Dr #420','Carmel','Indiana','United States','46032','888-247-0900',NULL,NULL,'hosting@nsi1.com','OK','Regular','2018-08-01 00:00:00',NULL,NULL,NULL,NULL,NULL,'mail.nsi1.com','205.178.189.131',NULL,NULL,0);

INSERT IGNORE INTO `Customer` VALUES (1,'Puja','Srivastava',NULL,'3,Raj Bhavan Road',NULL,'Bangalore',NULL,'India','560001','+91 080 22289999',NULL,NULL,'puja_srivastava@yahoo.in','PayPal',NULL,NULL,NULL,NULL,400);
INSERT IGNORE INTO `Customer` VALUES (2,'Manoj','Pareek',NULL,'12,Community Centre',NULL,'Delhi',NULL,'India','110017','+91 0124 39883988',NULL,NULL,'manoj.pareek@rediff.com','PayPal',NULL,NULL,NULL,NULL,400);
INSERT IGNORE INTO `Customer` VALUES (3,'Aaron','Mitchell',NULL,'696 Osborne Street',NULL,'Winnipeg','Manitoba','Canada','R3L 2B9','204-452-6452',NULL,NULL,'aaronmitchell@yahoo.ca','PayPal',NULL,NULL,NULL,NULL,401);
INSERT IGNORE INTO `Customer` VALUES (4,'Martha','Silk',NULL,'194A Chain Lake Drive',NULL,'Halifax','Nova Scotia','Canada','B3S 1C5','902-450-0450',NULL,NULL,'marthasilk@gmail.com','PayPal',NULL,NULL,NULL,NULL,401);
INSERT IGNORE INTO `Customer` VALUES (5,'Julia','Barnett',NULL,'302 S 700 E',NULL,'Salt Lake City','Utah','United States','84102','801-531-7272',NULL,NULL,'jubarnett@gmail.com','Credit',NULL,NULL,NULL,NULL,401);

INSERT IGNORE INTO `Product` VALUES ('BGMSSQL2','MS SQL 2000 10MB Lite (yearly)','Service','Yearly','MS SQL 2000 10MB Lite (yearly)',89.88,0,10,0,1024,0);
INSERT IGNORE INTO `Product` VALUES ('BRONZE','Bronze Hosting','Package','Monthly','Web Hosting  Domains - 500MB RAID-5 Storage, 20 Email Accounts, 24GB Bandwidth',14.99,30,6000,576000,3072,20);
INSERT IGNORE INTO `Product` VALUES ('GOLD','Gold Hosting','Package','Monthly','Web Hosting  Domains - 2GB RAID-5 Storage, 40 Email Accounts, 80GB Bandwidth',40.99,60,24000,1920000,3072,100);
INSERT IGNORE INTO `Product` VALUES ('MCLDSERV1000','Cloud Storage Server 1000','Service','Monthly','Upload, Sync and Share your files securely up to 1TB.',49.99,0,1048576,0,1024,0);
INSERT IGNORE INTO `Product` VALUES ('PLATINUM','Platinum Hosting','Package','Monthly','Web Hosting  Domains - 7GB RAID-5 Storage, 60 Email Accounts, 100GB Bandwidth',69.99,90,84000,2400000,3072,0);
INSERT IGNORE INTO `Product` VALUES ('PLATINUMPLUS','Platinum Plus paid monthly','Package','Monthly','Web Hosting  Domains - 10GB RAID-5 Storage, 300 Email Accounts, 500GB Bandwidth',129.98,450,120000,12000000,3072,0);
INSERT IGNORE INTO `Product` VALUES ('SOHOBASIC','SiteBuilder Basic paid monthly','Package','Monthly','Web Hosting  Domains - 100MB RAID-5 Storage, 5 Email Accounts, 4GB Bandwidth.',19.95,10,1200,96000,3072,2);
INSERT IGNORE INTO `Product` VALUES ('SSLCERTPREMIUM','Premium SSL Certificate (YR) 1 year','Service','Yearly',NULL,69.00,0,0,0,1024,0);
INSERT IGNORE INTO `Product` VALUES ('YEARLYPREMIUMPRO','Premium Pro Yearly','Package','Yearly','Web Hosting - 3GB RAID Storage, 60 Email Accounts, 160GB Bandwidth',576.00,30,3000,160000,3072,10);
