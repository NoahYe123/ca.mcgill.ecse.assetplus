package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;

public class AssetPlusFeatureUtility {
  // Input validation static methods:

  public static String isTicketIDValid(int ticketID) {
    if (ticketID < 0) {
      return "Error: the ticketID must be greater than 0.\n";
    }

    return "";
  }

  public static String isStringNotEmpty(String input) {
    if (input.isEmpty()) {
      return "Error: the String input must not be empty.\n";
    }  

    return "";
  }

  public static String isLifeSpanValid(int lifeSpan) {
    if(lifeSpan<=0){
      return "Error: the life span in days must be greater than 0.\n";
    }

    return "";
  }

  public static String isNewAssetTypeName(String name){
    for (AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
      if(type.getName() == name)
        return "Error: the name of the asset type must not be already used by another asset type.\n";
    }
    return "";
  }

  public static String isExistingAssetType(String name){
    for (AssetType type : AssetPlusApplication.getAssetPlus().getAssetTypes()){
      if(type.getName() == name)
        return "";
    }

    return "Error: the asset type specified with this name does not exist.\n";
  }

  public static String isFloorNumberValid(int floorNumber){
    if (floorNumber <= 0){
      return "Error: the floorNumber must be greater than 0.\n";
    }  

    return "";
  }

    public static String isRoomNumberValid(int roomNumber){
      if (roomNumber <= 0){
        return "Error: the roomNumber must be greater than 0.\n";
      }  

      return "";
    }

    public static String isExistingAsset(int assetNumber) {
      for (SpecificAsset asset : AssetPlusApplication.getAssetPlus().getSpecificAssets()) {
          if (asset.getAssetNumber() == assetNumber) {
              return "";
          }
      }

      return "Error: The assetNumber is not found in the list of assets.\n";
    }

    public static String isAssetNumberValid(int assetNumber) {
      if (assetNumber <= 0){
        return "Error: the assetNumber must be greater than 0.\n";
      }  

      return "";
    }

    public static SpecificAsset findSpecificAsset(int assetNumber){
      for (SpecificAsset asset : AssetPlusApplication.getAssetPlus().getSpecificAssets()) {
        if (asset.getAssetNumber() == assetNumber) {
          return asset;
        }
      }
      return null;
    }
    
    public static int isEmployeeEmailValid(String email) {
      for (int i = 0; i < AssetPlusApplication.getAssetPlus().numberOfEmployees(); i++) {
        if(email.equalsIgnoreCase(AssetPlusApplication.getAssetPlus().getEmployee(i).getEmail())) {
          return i;
        }
      }
      return -1;
    }
    
    public static int isGuestEmailValid(String email) {
      for (int i = 0; i < AssetPlusApplication.getAssetPlus().numberOfGuests(); i++) {
        if(email.equalsIgnoreCase(AssetPlusApplication.getAssetPlus().getGuest(i).getEmail())) {
          return i;
        }
      }
      return -1;
    }
    
    public static String isEmployeeEmailUnique(String email) {
      if (isEmployeeEmailValid(email) == -1) {
        return "";
      }
      return "Error: An employee already uses this email.\n";
    }
    
    public static String isGuestEmailUnique(String email) {
      if (isGuestEmailValid(email) == -1) {
        return "";
      }
      return "Error: A guest already uses this email.\n";
    }
    
    public static boolean isEmployee(String email) {
      if (email.endsWith("@ap.com")){
        return true;
      }
      return false;
    }




}
