package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

/**
 * <p>Feature 5 - Add image URL to maintenance ticket and delete it</p>
 * @author Julia B.Grenier
 */
public class AssetPlusFeatureSet5Controller {
  
  /**
   * <p>Add an image URL to the specified maintenance ticket</p>
   * @param imageURL the URL of the image
   * @param ticketID the ID number of the ticket
   * @return an empty string or an error message 
   */
  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(ticketID, "ticketID") + 
                  AssetPlusFeatureUtility.isStringValid(imageURL, "imageURL");
    if (!err.isEmpty()) {
      return err;
    }

    // Add image
    try {
      MaintenanceTicket.getWithId(ticketID).addTicketImage(imageURL);
    }
    catch (RuntimeException e) {
      return e.getMessage();
    }
    return ""; // empty string means operation was successful (no error)
  }

  /**
   * <p>Delete an image URL from the specified maintenance ticket</p>
   * @param imageURL the URL of the image
   * @param ticketID the ID number of the ticket
   */
  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    // Input validations
    String err =  AssetPlusFeatureUtility.isGreaterThanOrEqualToZero(ticketID, "ticketID") + 
                  AssetPlusFeatureUtility.isStringValid(imageURL, "imageURL");
    if (!err.isEmpty()) {
      return;
    }

    // Find the image with the URL and delete it
    for (TicketImage image : MaintenanceTicket.getWithId(ticketID).getTicketImages() ) {
      if (image.getImageURL() == imageURL) {
        image.getTicket().removeTicketImage(image);
      }
    }
  }

}
