package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

/**
 * <p>This class contains input validation methods that are used in multiple controller classes</p>
 * 
 * @author Sahar Fathi
 * @author Anjali Singhal
 * @author Julia B. Grenier
 * @author Tayba Jusab
 * @author Camille Pouliot
 * @author Émilia Gagné
 */
public class AssetPlusFeatureUtility {
  // Input validation static methods:

  /**
   * <p>Check if the input integer is greater than or equal zero, and returns an empty string if it is.</p>
   * 
   * @param number the integer to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isGreaterThanOrEqualToZero(int number, String subject) {
    if (number < 0) {
      return "Error: the number from " + subject + " must be greater than or equal to 0.\n";
    }
    return "";
  }

  /**
   * <p>Check if the input string is valid, and returns an empty string if it is.</p>
   * 
   * @param input the string to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isStringValid(String input, String subject, String cannotOrMustNot) {
    if (input.isEmpty()) {
      return "The " + subject + " " + cannotOrMustNot + " be empty";
    }
    return "";
  }


  /**
   * <p>Check if the input id is an existing maintenance ticket and returns an empty string if it is.</p>
   * 
   * @param id the ticket id associated to a maintenance ticket
   * @return an empty string or an error message
   */
  public static String isExistingTicket(MaintenanceTicket ticket) {
    if (ticket == null || !MaintenanceTicket.hasWithId(ticket.getId())) {
      return "Maintenance ticket does not exist.";
    }
    return "";
  }

  public static String isExistingTicket(int id) {
    if (!MaintenanceTicket.hasWithId(id)) {
      return "Ticket does not exist";
    }
    return "";
  }

  /**
   * <p>Check if the input string is empty, and return an error if it is.</p>
   * 
   * @param input the string to check for input validation
   * @param subject the name of the type of number
   * @return an empty string or an error message
   */
  public static String isDescriptionEmpty(String input) {
    if (input.isEmpty()) {
      return "Ticket description cannot be empty";
    }
    return "";
  }
}

