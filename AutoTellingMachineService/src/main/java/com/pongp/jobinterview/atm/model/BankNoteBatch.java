package com.pongp.jobinterview.atm.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contain multiple kind of {@link BankNote}
 * <br>This class is to encapsulate methods used for manipulating {@link BankNote} within this {@link BankNoteBatch}
 * @author PongP
 */
public class BankNoteBatch 
{
	  private Map<Integer,BankNote> _noteData = new HashMap<Integer,BankNote>();
	   
	   
	   public BankNoteBatch()
	   {
	   }
	   
	   public BankNoteBatch( List<BankNote> noteData )
	   {
		   for ( BankNote note : noteData )
		   {
			   _noteData.put(note.getValue(), note);
		   }
	   }
	   
	   public void addNote( int noteValue )
	   {
		   BankNote newNote = _noteData.get(noteValue);
		   if( newNote == null )
		   {
			   newNote = new BankNote(noteValue, 0);
			   _noteData.put(noteValue, newNote );
		   }
		   
		   newNote.increaseNoteCount();
	   }
	   
	   public void removeNote( int noteValue )
	   {
		   BankNote newNote = _noteData.get(noteValue);
		   if( newNote != null )
		   {
			   newNote.descreaseNoteCount();
		   }
	   }
	   
	   public Map<Integer,BankNote> getNoteData()
	   {
		   return _noteData;
	   }
}
