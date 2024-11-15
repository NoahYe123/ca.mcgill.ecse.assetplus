/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 19 "../../../../../AssetPlus.ump"
public class MaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private Date date;
  private String description;

  //MaintenanceNote Associations
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(Date aDate, String aDescription, MaintenanceTicket aTicket)
  {
    date = aDate;
    description = aDescription;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create ticketNote due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getTicket()
  {
    return ticket;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicket(MaintenanceTicket aTicket)
  {
    boolean wasSet = false;
    if (aTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingTicket = ticket;
    ticket = aTicket;
    if (existingTicket != null && !existingTicket.equals(aTicket))
    {
      existingTicket.removeTicketNote(this);
    }
    ticket.addTicketNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeTicketNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}