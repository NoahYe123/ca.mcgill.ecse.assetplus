package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import javafx.application.Application;
import javafx.scene.text.Font;

public class AssetPlusApplication {

  private static AssetPlus assetPlus;

  public static void main(String[] args) {
    // TODO Start the application user interface here
  Font.loadFont("https://fonts.googleapis.com/css2?family=Montserrat&display=swap", 12);

    Application.launch(AssetPlusFXMLView.class, args);
  }

  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      // these attributes are default, you should set them later with the setters
      assetPlus = new AssetPlus();
    }
    return assetPlus;
  }

}
