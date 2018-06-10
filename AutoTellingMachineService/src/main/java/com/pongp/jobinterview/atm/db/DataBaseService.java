package com.pongp.jobinterview.atm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pongp.jobinterview.atm.model.BankNote;

public class DataBaseService
{
    private static DataBaseService _instance;

    private DataBaseService() throws ClassNotFoundException, SQLException
    {
	// initiate  built-in database in constructor
	Class.forName("org.hsqldb.jdbc.JDBCDriver");
	try (Connection connection = getConnection(); Statement statement = connection.createStatement();)
	{
	    statement.execute("CREATE TABLE banknote (VALUE INT NOT NULL, VOLUME INT NOT NULL, PRIMARY KEY (VALUE))");
	    connection.commit();

	    // initiate data
	    statement.executeUpdate("INSERT INTO banknote VALUES ( 20, 1000)");
	    statement.executeUpdate("INSERT INTO banknote VALUES ( 50, 1000)");
	    statement.executeUpdate("INSERT INTO banknote VALUES ( 100, 1000)");
	    statement.executeUpdate("INSERT INTO banknote VALUES ( 500, 1000)");
	    statement.executeUpdate("INSERT INTO banknote VALUES ( 1000, 1000)");
	    connection.commit();
	}

    }
    
    public static DataBaseService getInstance() throws ClassNotFoundException, SQLException
    {
	if( _instance == null )
	{
	    _instance = new DataBaseService();
	}
	return _instance;
    }

    public Connection getConnection() throws SQLException
    {
	return DriverManager.getConnection("jdbc:hsqldb:mem:employees");
    }

    public static BankNote getFirst()
    {
	BankNote user = null;
	try (Connection connection = DataBaseService.getInstance().getConnection(); Statement statement = connection.createStatement();)
	{
	    statement.execute("SELECT VALUE,VOLUME from banknote");

	    ResultSet result = statement.executeQuery("SELECT * FROM banknote");
	    if (result.next())
	    {
		user = new BankNote(result.getInt("VALUE"), result.getInt("VOLUME"));
	    }
	} 
	catch (SQLException | ClassNotFoundException e)
	{

	}
	return user;
    }

}
