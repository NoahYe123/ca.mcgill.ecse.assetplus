/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;
import java.util.*;

// line 3 "../../../../../AssetPlusTransferObjects.ump"
public class TOMaintenanceTicket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMaintenanceTicket Attributes
  private int id;
  private Date raisedOnDate;
  private String description;

  //TOMaintenanceTicket Associations
  private List<TOMaintenanceNote> notes;

  //Helper Variables
  private boolean canSetNotes;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMaintenanceTicket(int aId, Date aRaisedOnDate, String aDescription, TOMaintenanceNote... allNotes)
  {
    id = aId;
    raisedOnDate = aRaisedOnDate;
    description = aDescription;
    canSetNotes = true;
    notes = new ArrayList<TOMaintenanceNote>();
    boolean didAddNotes = setNotes(allNotes);
    if (!didAddNotes)
    {
      throw new RuntimeException("Unable to create TOMaintenanceTicket, must not have duplicate notes. See http://manual.umple.org?RE001ViolationofImmutability.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }

  public Date getRaisedOnDate()
  {
    return raisedOnDate;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetMany */
  public TOMaintenanceNote getNote(int index)
  {
    TOMaintenanceNote aNote = notes.get(index);
    return aNote;
  }

  public List<TOMaintenanceNote> getNotes()
  {
    List<TOMaintenanceNote> newNotes = Collections.unmodifiableList(notes);
    return newNotes;
  }

  public int numberOfNotes()
  {
    int number = notes.size();
    return number;
  }

  public boolean hasNotes()
  {
    boolean has = notes.size() > 0;
    return has;
  }

  public int indexOfNote(TOMaintenanceNote aNote)
  {
    int index = notes.indexOf(aNote);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNotes()
  {
    return 0;
  }
  /* Code from template association_SetUnidirectionalMany */
  private boolean setNotes(TOMaintenanceNote... newNotes)
  {
    boolean wasSet = false;
    if (!canSetNotes) { return false; }
    canSetNotes = false;
    ArrayList<TOMaintenanceNote> verifiedNotes = new ArrayList<TOMaintenanceNote>();
    for (TOMaintenanceNote aNote : newNotes)
    {
      if (verifiedNotes.contains(aNote))
      {
        continue;
      }
      verifiedNotes.add(aNote);
    }

    if (verifiedNotes.size() != newNotes.length)
    {
      return wasSet;
    }

    notes.clear();
    notes.addAll(verifiedNotes);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "raisedOnDate" + "=" + (getRaisedOnDate() != null ? !getRaisedOnDate().equals(this)  ? getRaisedOnDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}