package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

/**
 * <p>Feature 6 - View status of all maintenance tickets / Delete employee / guest</p>
 * @author Tayba Jusab
 */
public class AssetPlusFeatureSet6Controller {

  /**
   * <p>Get a list of all maintenance tickets as transfer objects</p>
   * @return the list of tickets
   */
  public static List<TOMaintenanceTicket> getTickets() {
    List<MaintenanceTicket> maintenanceTickets = 
      AssetPlusApplication.getAssetPlus().getMaintenanceTickets();
    List<TOMaintenanceTicket> tickets = new ArrayList<>();

    for (MaintenanceTicket ticket: maintenanceTickets) {
      tickets.add(convertFromMaintenanceTicket(ticket));
    }

    return tickets;
  }

  /**
   * <p>Get a maintenance ticket as transfer object</p>
   * @return a ticket
   */
  public static TOMaintenanceTicket getTicket(int id) {
    if (MaintenanceTicket.hasWithId(id))
      return convertFromMaintenanceTicket(MaintenanceTicket.getWithId(id));
    
    return null;
  }


  /**
   * <p>Converts a maintenance ticket object into a TOMaintenanceTicket</p>
   * @param maintenanceTicket the object to convert
   * @return the converted TOMaintenanceTicket object
   */
  private static TOMaintenanceTicket convertFromMaintenanceTicket(
      MaintenanceTicket maintenanceTicket) {
    List<TOMaintenanceNote> toMaintenanceNotes =
        convertFromMaintenanceNotes(maintenanceTicket.getTicketNotes());
    TOMaintenanceNote[] allNotes = toMaintenanceNotes.toArray(new TOMaintenanceNote[0]);


    return new TOMaintenanceTicket(
      maintenanceTicket.getId(), 
      maintenanceTicket.getRaisedOnDate(),
      maintenanceTicket.getDescription(), 
      allNotes);
  }

  /**
   * <p>Converts a list of maintenance notes into a list of TOMaintenanceNote</p>
   * @param maintenanceNotes the list of notes to convert
   * @return the converted list of TOMaintenanceNote
   */
  private static List<TOMaintenanceNote> convertFromMaintenanceNotes(
      List<MaintenanceNote> maintenanceNotes) {
    List<TOMaintenanceNote> toMaintenanceNotes = new ArrayList<>();

    for (MaintenanceNote maintenanceNote : maintenanceNotes) {
      TOMaintenanceNote toMaintenanceNote = new TOMaintenanceNote(maintenanceNote.getId(), maintenanceNote.getDate(),
          maintenanceNote.getDescription());
      toMaintenanceNotes.add(toMaintenanceNote);
    }
    return toMaintenanceNotes;
  }
}
