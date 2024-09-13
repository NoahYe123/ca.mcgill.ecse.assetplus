/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

// line 12 "../../../../../AssetPlusTransferObjects.ump"
public class TOMaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMaintenanceNote Attributes
  private Date date;
  private String description;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMaintenanceNote(Date aDate, String aDescription)
  {
    date = aDate;
    description = aDescription;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}