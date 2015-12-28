package net.leludo.tuto.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import net.leludo.helpers.SqlHelper;

public class CommonTest
{
    protected static EntityManagerFactory emf;

    protected SqlHelper sqlHelper;

    private static Connection con;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        try
        {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e)
        {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        // Connection c = DriverManager
        // .getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");

        con = DriverManager.getConnection("jdbc:hsqldb:mem:championshipdb",
                "SA", "");

        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class",
                "org.hsqldb.jdbc.JDBCDriver");
        properties.put("hibernate.connection.url",
                "jdbc:hsqldb:mem:championshipdb");
        properties.put("hibernate.connection.username", "SA");
        properties.put("hibernate.connection.password", "");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.show_sql", "true");

        emf = Persistence.createEntityManagerFactory("jpa-tuto", properties);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        sqlHelper = new SqlHelper().with(con);
        sqlHelper.executeSql(
                "CREATE TABLE championship(id INTEGER IDENTITY, name VARCHAR(50));");
        sqlHelper.executeSql(
                "CREATE TABLE race(id INTEGER IDENTITY, championshipId INTEGER, name VARCHAR(50));");
    }

    @After
    public void tearDown() throws Exception
    {
        sqlHelper.executeSql("DROP TABLE championship;");
        sqlHelper.executeSql("DROP TABLE race;");
    }
}
