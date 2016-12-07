    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author marti
 */
@Configuration
@PropertySources(value = {@PropertySource("classpath:application.properties")})
@ComponentScan(basePackages =
{
    "calculus.backend.model"
})
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaConfig
{
    private static final Logger LOG = LoggerFactory.getLogger(JpaConfig.class);

    @Value("${dataSource.enablePropertiesConfig}")
    @NotNull
    private Boolean enablePropertiesConfig;
    
    @Value("${dataSource.driver}")
    private String driver;
    
    @Value("${dataSource.url}")
    private String url;
    
    @Value("${dataSource.user}")
    private String user;
    
    @Value("${dataSource.pass}")
    private String pass;
    
    @Value("${dataSource.poolSize}")
    @NotNull
    private Integer poolSize;
    
    @Value("${javax.persistence.schema-generation.database.action}")
    @NotNull
    private String schemaGeneration;

    /**
     * posibles valores: mysql o postgresql
     */
    @Value("${dataSource.dataBaseType}")
    @NotNull
    private String dataBaseType;
    
    private Database dataBase;
    
    @Bean
    public DataSource dataSource() {
        
        loadProperties();

        BasicDataSource driver = new BasicDataSource();
        driver.setDriverClassName(this.driver);
        driver.setUrl(this.url);
        driver.setUsername(this.user);
        driver.setPassword(this.pass);
        driver.setMaxIdle(poolSize);
        driver.setMaxActive(poolSize);
        return driver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(this.dataBase);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("calculus.backend.model");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(propiedadesAdicionalesJpa());
        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager()
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * 
     * @return 
     */
    private Properties propiedadesAdicionalesJpa() {
        return new Properties() {
            {
                setProperty("javax.persistence.schema-generation.database.action", schemaGeneration);
            }
        };
    }
    
    /**
     * 
     */
    public void loadProperties() {

        if (!Boolean.TRUE.equals(enablePropertiesConfig)) {
            loadEnviromentConfig();
        } else {
            switch(DataBaseTypeEnum.valueOf(dataBaseType)) {
                case mysql:
                    dataBase = Database.MYSQL;
                    break;
                case postgresql:
                    dataBase = Database.POSTGRESQL;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "El tipo de base de datos introducido, dataBaseType='" + dataBaseType 
                                    + "' no es soportado. Solo son válidos 'mysql' y 'postgresql'");
            }
        }
        
        LOG.info("Usando DB url=" + url);
        
// y otro mas: Neroku
//            this.driver = "com.mysql.jdbc.Driver";
//            this.url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3138396";
//            this.user = "sql3138396";
//            this.pass = "R6KXYvjsVJ";
//            this.dataBase = Database.MYSQL;
//            this.poolSize = 3;

    }

    /**
     * 
     */
    private void loadEnviromentConfig() {
        LOG.info("Cargando configuracion de la BDs en funcion del ambiente ..");
        
        String env = "RD00155DFBD797"; //DEFAULT_ENV
        try {
            env = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            LOG.error(ex.getMessage(),ex);
        }
        
        LOG.info("env=" + env);
        
        if (env.equals("localhost")){
            //postgres local
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://localhost:5432/pis2016";
            this.user = "postgres";
            this.pass = "user";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        } else if (env.equals("RD00155DFBD985")){ //AZURE_PROD
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://nutty-custard-apple.db.elephantsql.com:5432/gxedfpdw";
            this.user = "gxedfpdw";
            this.pass = "Ci05wYOZACatHbnzav_VmHiSIF6J-fNB";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        } else if (env.equals("abentan-PC")){
            this.driver = "com.mysql.jdbc.Driver";
            this.url = "jdbc:mysql://localhost:3306/pis2016";
            this.user = "pis2016";
            this.pass = "pis2016";
            this.dataBase = Database.MYSQL;
            this.poolSize = 5;
        } else if (env.equals("RD00155DFBD797")){ //AZURE_BETA
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://nutty-custard-apple.db.elephantsql.com:5432/fbonhmwr";
            this.user = "fbonhmwr";
            this.pass = "4axmS5tbJRUh_f5j8K2LBQZ8wrEO4jBR";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        } else if (env.equals("MacBook-Pro.local")){
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://localhost:5432/pis2016";
            this.user = "tavidian";
            this.pass = "";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        } else if (env.equals("NetPC")){
            //postgres local
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://localhost:5432/postgres";
            this.user = "postgres";
            this.pass = "4512161-7";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        }
        else if (env.equals("MARTINV-DELL")){
            //postgres local
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://localhost:5432/postgres";
            this.user = "postgres";
            this.pass = "user";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        } else if (env.equals("PC3")) {
            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://localhost:5432/pis2016";
            this.user = "postgres";
            this.pass = "admin";
            this.dataBase = Database.POSTGRESQL;
            this.poolSize = 1;
        }
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    /**
     * posibles tipos de base de datos soportados
     */
    public static enum DataBaseTypeEnum {
        mysql, postgresql;
    };

}
