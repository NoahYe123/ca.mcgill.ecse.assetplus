package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class RemoveMaintenanceTicketController{
	
    // Private Fields Generation
	@FXML
	private Button deleteMaintenanceTicketButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Rectangle fieldBgDelete;
	
	@FXML
	private TextField maintenanceticketIdField;
	
	private static int maintenanceticketId;
	
	
	
	

	// Method Generation
	@FXML
	void initialize() {
		maintenanceticketIdField.setEditable(false);
		maintenanceticketIdField.setFocusTraversable(false);
		maintenanceticketIdField.setText(Integer.toString(maintenanceticketId));
	}
	
	@FXML
	void cancelClicked(ActionEvent event) {
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	@FXML
	void deleteMaintenanceTicketClicked(ActionEvent event) {
		AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(maintenanceticketId);
		maintenanceticketIdField.setText("");
		ViewUtils.callController("");
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	public static void setId(int id){
		maintenanceticketId=id;
	}
	
	
}
