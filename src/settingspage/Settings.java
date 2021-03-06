package settingspage;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Formatter;

import javafx.geometry.*;

public class Settings {	
	/**
	 * Color variable of the first player 
	 */
	Color color1;
	/**
	 * Color variable of the second player
	 */
	Color color2;
	/**
	 * Size of the board
	 */
	int size;
	
	/**
	 * This function displays the settings page
	 */
	public void display(){
		Stage window = new Stage();
		/*
		 * Default program values:
		 */
		//default file name
		String filePath = "settings";
		//default board size values
		final int minSize = 4;
		final int maxSize = 20;
		try{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		
		final int defaultBoardSize = Integer.parseInt(reader.readLine());
		//default color values
		Color defaultColorOne = Color.web(reader.readLine());
		Color defaultColorTwo = Color.web(reader.readLine());
		reader.close();
		//default scene size values
		final double initialSceneWidth = 400;
		final double initialSceneHeight = 400;
		//setting the title, height and width of the window
		window.setWidth(initialSceneWidth);
		window.setHeight(initialSceneHeight);
		window.setTitle("Settings");
		//for color of first player
		Label explenationLabel = new Label("Enter game settings"
											+ "\nNote that you will not be able to play while both player\n"
											+ "have the same color");
		Label playerOneLabel = new Label("Player 1 color:");
		ColorPicker playerOneColor = new ColorPicker(defaultColorOne);
		//for color of second player
		Label playerTwoLabel = new Label("Player 2 color:");
		ColorPicker playerTwoColor = new ColorPicker(defaultColorTwo);
		//for size
		Label sizeLabel = new Label("Choose size of board:");
		ChoiceBox<Integer> sizeChoiceBox = new ChoiceBox<>();
		//adding size options
		for(int i = minSize; i <= maxSize; i++ ){
			sizeChoiceBox.getItems().add(i);
		}
		//setting default size value
		sizeChoiceBox.setValue(defaultBoardSize);
		//play button
		Button button = new Button("Play!");
		button.setOnAction(e -> {
			//setting the taken values from user
			color1 = playerOneColor.getValue();
			color2 = playerTwoColor.getValue();
			size = sizeChoiceBox.getValue();
			//if both player have the same color
			if(color1.equals(color2)){
				e.consume();
				return;
			}else{
			//writing values to file
				this.writeChoiceToFile(filePath);
				window.close();
			}
		});
		window.setOnCloseRequest(e -> {
			//This is used so that when a client clicks the red X button
			//the program should exit and the game shouldn't start
			//without this, the game would start with default values
			//and this is not the desired result
			System.exit(0);
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(explenationLabel, playerOneLabel, playerOneColor, playerTwoLabel, playerTwoColor, sizeLabel, sizeChoiceBox, button);
		layout.setAlignment(Pos.TOP_LEFT);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param filePath - the path in which this method writes to
	 * Writes values in format:
	 * <Size> (integer)
	 * <Color1 hex web value> (String)
	 * <Color2 hex web value> (String)
	 */
	private void writeChoiceToFile(String filePath){
		try{
			//open file
			Formatter writer = new Formatter(filePath);
			//format is <Number> \n <String> \n <String>
			writer.format("%d\n%s\n%s", size,
					getColorWebFormat(color1),
					getColorWebFormat(color2));
			//closing the file
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param color 
	 * @return web hex value of color as string
	 */
	private static String getColorWebFormat(Color color){
		return String.format( "#%02X%02X%02X",
				(int)( color.getRed() * 255 ),
				(int)( color.getGreen() * 255 ),
				(int)( color.getBlue() * 255 ) );
	}
}
