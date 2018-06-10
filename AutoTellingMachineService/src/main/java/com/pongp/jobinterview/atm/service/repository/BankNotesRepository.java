package com.pongp.jobinterview.atm.service.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pongp.jobinterview.atm.db.DataBaseService;
import com.pongp.jobinterview.atm.model.BankNote;

public class BankNotesRepository implements IRepository<BankNote>
{
    public static final Logger logger = LoggerFactory.getLogger(BankNotesRepository.class);

    public void update(BankNote item)
    {
	try (Connection connection = DataBaseService.getInstance().getConnection();)
	{
	    PreparedStatement statement = connection.prepareStatement("UPDATE banknote SET VOLUME = ? WHERE VALUE = ?");
	    statement.setInt(1, item.getNoteCount() );
	    statement.setInt(2, item.getValue() );
	    statement.executeUpdate();
	    connection.commit();
	}
	catch (SQLException | ClassNotFoundException e )
	{
	    logger.error( "Failed to query bank data from database : " + e.getMessage() );
	}
    }
    
    public List<BankNote> FindAll()
    {
	 List<BankNote> bankData = new ArrayList<BankNote>();
	try (Connection connection = DataBaseService.getInstance().getConnection(); Statement statement = connection.createStatement();)
	{
	    statement.execute("SELECT VALUE,VOLUME from banknote");

	    ResultSet result = statement.executeQuery("SELECT * FROM banknote");
	    while (result.next())
	    {
		bankData.add( new BankNote(result.getInt("VALUE"), result.getInt("VOLUME")) );
	    }
	} 
	catch (SQLException | ClassNotFoundException e)
	{
	    logger.error( "Failed to query bank data from database : " + e.getMessage() );
	}
	
	return bankData;
    }
    
    
    public void add(BankNote item)
    {
	//adding/removing new type of deliberately unsupported
	throw new UnsupportedOperationException();
    }

    public void remove(BankNote item)
    {
	//adding/removing new type of deliberately unsupported
	throw new UnsupportedOperationException();
    }

}
