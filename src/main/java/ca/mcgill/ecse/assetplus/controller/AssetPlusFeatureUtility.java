package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureUtility {
  // Input validation static methods:

  public static String isGreaterThanOrEqualToZero(int number, String subject){
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than or equal to 0.\n";
    }
    return "";
  }

  public static String isGreaterThanZero(int number, String subject){
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than  0.\n";
    }
    return "";
  }

  public static String isStringValid(String input, String subject) {
    if (input.isEmpty()) {
      return "Error: " + subject + " cannot be empty.\n";
    }
    return "";
  }

  public static String isExistingAssetType(String name){
    String err = "";
    AssetType type =  AssetType.getWithName(name);
    if(type == null){
      err = "Error: the asset type specified with this name does not exist.\n";
    }
    return err;
  }

    public static String isExistingAsset(int assetNumber) {
      if (SpecificAsset.getWithAssetNumber(assetNumber) != null){
        return "";
      } else {
        return "Error: there is no specific asset with that assetNumber.\n";
      }
    }


    public static String isExistingUser(String email) {
      if (!User.hasWithEmail(email)) {
        return "Error: user not found.";
      }
      return "";
    }

    public static String isExistingTicket(int id){
      if (!MaintenanceTicket.hasWithId(id)){
        return "Ticket does not exist";
      }
      return "";
    }

    public static boolean isEmployeeEmailValid(String email) {
      return email.endsWith("@ap.com");
    }

    public static String isValidAssetNumberForTicket(int assetNumber){
      if ((SpecificAsset.getWithAssetNumber(assetNumber) != null) || (assetNumber == -1)){
        return "";
      } else {
        return "Error: not a valid asset number for a ticket.\n";
      }
    }

    public static String isStartingWithHttpOrHttps(String imageURL) {
      if (imageURL.startsWith("http://") || imageURL.startsWith("https://")){
        return "";
      } else {
        return "Error: Image URL must start with http:// or https://.\n";
      }
    }

    public static String isExistingImageURL(String imageURL, int ticketID) {
      if (MaintenanceTicket.hasWithId(ticketID)){
        for (TicketImage image : MaintenanceTicket.getWithId(ticketID).getTicketImages() ) {
          if (imageURL.equals(image.getImageURL())) {
            return "Error: Image already exists for the ticket.\n";
          }
        }
      }
      return "";
    }

    // Other utility methods

    public static TOMaintenanceTicket convertFromMaintenanceTicket(MaintenanceTicket maintenanceTicket) {
      List<TOMaintenanceNote> toMaintenanceNotes = convertFromMaintenanceNotes(maintenanceTicket.getTicketNotes());
      TOMaintenanceNote[] allNotes = toMaintenanceNotes.toArray(new TOMaintenanceNote[0]);

      return new TOMaintenanceTicket(
          maintenanceTicket.getId(),
          maintenanceTicket.getRaisedOnDate(),
          maintenanceTicket.getDescription(),
          maintenanceTicket.getTicketRaiser().getEmail(),
          maintenanceTicket.getAsset().toString(),
          maintenanceTicket.getAsset().getAssetType().getExpectedLifeSpan(),
          maintenanceTicket.getAsset().getPurchaseDate(),
          maintenanceTicket.getAsset().getFloorNumber(),
          maintenanceTicket.getAsset().getRoomNumber(),
          convertFromTicketImages(maintenanceTicket.getTicketImages()),
          allNotes
        );
    }

    public static List<TOMaintenanceNote> convertFromMaintenanceNotes(List<MaintenanceNote> maintenanceNotes) {
      List<TOMaintenanceNote> toMaintenanceNotes = new ArrayList<>();

      for (MaintenanceNote maintenanceNote: maintenanceNotes) {
        TOMaintenanceNote toMaintenanceNote = new TOMaintenanceNote(
          maintenanceNote.getDate(), 
          maintenanceNote.getDescription(), 
          maintenanceNote.getNoteTaker().getEmail());
        toMaintenanceNotes.add(toMaintenanceNote);
      }
      return toMaintenanceNotes;
    }

    public static List<String> convertFromTicketImages(List<TicketImage> ticketImages) {
      List<String> imageURLS = new ArrayList<>();

      for (TicketImage ticketImage: ticketImages) {
        imageURLS.add(ticketImage.getImageURL());
      }
      return imageURLS;
    }
}
