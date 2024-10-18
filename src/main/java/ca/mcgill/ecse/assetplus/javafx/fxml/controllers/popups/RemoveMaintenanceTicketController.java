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
	private TextField maintenanceTicketIdField;
	
	private static int maintenanceTicketId;
	
	
	
	// Method Generation
	@FXML
	void initialize() {
		maintenanceTicketIdField.setEditable(false);
		maintenanceTicketIdField.setFocusTraversable(false);
		maintenanceTicketIdField.setText(Integer.toString(maintenanceTicketId));
	}
	
	@FXML
	void cancelClicked(ActionEvent event) {
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	@FXML
	void deleteMaintenanceTicketClicked(ActionEvent event) {
	
		AssetPlusFeatureSet4Controller.removeMaintenanceTicket(maintenanceTicketId);
	
		maintenanceTicketIdField.setText("");
		ViewUtils.callController("");
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	public static void setMaintenanceTicketId(int id) {
		maintenanceTicketId = id;
	
	}
	
	
}
