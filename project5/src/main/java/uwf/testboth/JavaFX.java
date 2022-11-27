package uwf.testboth;

import javafx.geometry.Insets;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**
 * 
 * Class: JavaFX
 * Extends: Application
 * 
 * Creates the user interface and manages the client.
 * 
 * @author Tia Sharpe
 *
 */

public class JavaFX extends Application {

	ChoiceBox<String> instruments;
	ChoiceBox<String> brand;
	ChoiceBox<String> warehouse;
	
	TextField maxCost;
	
	ReentrantLock lockHorses = new ReentrantLock();
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Application.launch(args);
		
	}
	
	/**
	 * 
	 * Method: start(Stage)
	 * 
	 * Manages the JavaFX
	 * 
	 */
	
	public void start(Stage primaryStage) throws FileNotFoundException {
        
		primaryStage.setTitle("Musical Instruments");
		
		// instrument
		
		instruments = new ChoiceBox<String>();
		
		Label iLabel = new Label("Instrument Type: ");
		instruments.getItems().addAll("all", "guitar", "bass", "keyboard", "drums");
		instruments.getSelectionModel().select(0);
		
		HBox instrumentsHBox = new HBox();
		instrumentsHBox.setAlignment(Pos.CENTER);
		instrumentsHBox.getChildren().addAll(iLabel, instruments);
		
		// brand
		
		brand = new ChoiceBox<String>();
		
		Label bLabel = new Label("Instrument Brand: ");
		brand.getItems().addAll("all", "gibson", "fender", "ludwig", "roland", "alesis", "yamaha");
		brand.getSelectionModel().select(0);
		
		HBox brandHBox = new HBox();
		brandHBox.setAlignment(Pos.CENTER);
		brandHBox.getChildren().addAll(bLabel, brand);
		
		// maximum cost
		
		maxCost = new TextField();
		Label cLabel = new Label("Maximum Cost: ");
		
		HBox maxCostHBox = new HBox();
		maxCostHBox.setAlignment(Pos.CENTER);
		maxCostHBox.getChildren().addAll(cLabel, maxCost);
		
		// warehouse
		
		warehouse = new ChoiceBox<String>();
		Label wLabel = new Label("Warehouse Location: ");
		warehouse.getItems().addAll("all", "PNS", "CLT", "DFW");
		warehouse.getSelectionModel().select(0);
		
		HBox warehouseHBox = new HBox();
		warehouseHBox.setAlignment(Pos.CENTER);
		warehouseHBox.getChildren().addAll(wLabel, warehouse);
		
		// button
		Button text = new Button("Submit Request");
		text.setOnAction(new TextButtonListener());
		
		HBox button = new HBox(10);
		button.setAlignment(Pos.CENTER);
		button.getChildren().addAll(text);
		
		// vbox
		
		VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));
		vbox.getChildren().addAll(instrumentsHBox, brandHBox, maxCostHBox, warehouseHBox, button);
		
		// scene
		
		Scene scene = new Scene(vbox, 500, 350);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	/**
	 * 
	 * Method: select(ChoiceBox<String>)
	 * 
	 * @param i
	 * 
	 */
	
	public void select(ChoiceBox<String> i) {
		
		brands();
		
	}
	
	/**
	 * 
	 * Method: brands()
	 * 
	 * Selects the correct brands given the selected instruments.
	 * 
	 */
	
	public void brands() {
		
		brand.getItems().clear();
		
		String instrument = instruments.getValue();
		
		// guitar
		
		if(instrument.equals("guitar")) {
			
			brand.getItems().addAll("all", "yamaha", "gibson");
			
		}
		
		// keyboard
		
		else if(instrument.equals("keyboard")) {
			
			brand.getItems().addAll("all", "roland", "alesis");
			
		}
		
		// bass
		
		else if(instrument.equals("bass")) {
			
			brand.getItems().addAll("all", "fender");
			
		}
		
		// drums
		
		else if(instrument.equals("drums")) {
			
			brand.getItems().addAll("all", "ludwig", "yamaha");
			
		}
		
		brand.getSelectionModel().select(0);
		
	}
	
	/**
	 * 
	 * Method: sendRequest()
	 * 
	 * Sends the request to the server.
	 * 
	 */
	
	private void sendRequest() {
		
		final int portNumber = 8888;
		
		String thisInstrument = instruments.getValue();
		String thisBrand = brand.getValue();
		String thisCost = maxCost.getText();
		
			if(thisCost.length() == 0) {
				
				thisCost = "0";
				
			}
			
		String thisWarehouse = warehouse.getValue();
		String request = thisInstrument + " " + thisBrand + " " + thisCost + " " + thisWarehouse;
		
		try {
			
			Socket s = new Socket("localhost", portNumber);
			
			InputStream inStream = s.getInputStream();
			OutputStream outStream = s.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream);
			
			out.print(request);
			out.flush();
			
			String response = "";
			
			int lines = 0;
			
			while(in.hasNext()) {
				
				response += in.nextLine();
				response += "\n";
				
				lines++;
				
			}
			
			System.out.println(response);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Musical Instruments");
			alert.setResizable(true);
			alert.getDialogPane().setPrefSize(500,  150 + 20 * lines);
			alert.setHeaderText("Results");
			alert.setContentText(response);
			System.out.println(alert.getHeight() + alert.getWidth());
			
			alert.show();
			
		}
		
		catch(Exception e) {
			
		}
		
	}

	private class TextButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			sendRequest();
			
		}

	}
	
}
