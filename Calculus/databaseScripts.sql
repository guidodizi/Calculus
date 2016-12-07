-- mysql 
create database if not exists pis2016 default charset utf8;

create database if not exists pis2016prod default charset utf8;

GRANT ALL PRIVILEGES ON pis2016prod.* TO 'pis2016'@'%' IDENTIFIED BY 'pis2016';
GRANT ALL PRIVILEGES ON pis2016.* TO 'pis2016'@'%' IDENTIFIED BY 'pis2016';


FLUSH PRIVILEGES;

