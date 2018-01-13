package reversiapp;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;

import java.util.Formatter;

import javafx.geometry.*;

public class Settings {
		Color color1;
		Color color2;
		int size;
	public void display(){
		Stage window = new Stage();
		/*
		 * Default program values:
		 */
		//default file name
		String filePath = "src/settings";
		//default board size values
		final int minSize = 4;
		final int maxSize = 20;
		//make sure minSize < defaultBoardSize < maxSize
		final int defaultBoardSize = 8;
		//default color values
		final Color defaultColorOne = Color.RED;
		final Color defaultColorTwo = Color.BLUE;
		//default scene size values
		final double initialSceneWidth = 400;
		final double initialSceneHeight = 400;
		window.setWidth(initialSceneWidth);
		window.setHeight(initialSceneHeight);
		window.setTitle("Settings");
		
		Label playerOneLabel = new Label("Player 1 color:");
		ColorPicker playerOneColor = new ColorPicker(defaultColorOne);
		
		Label playerTwoLabel = new Label("Player 2 color:");
		ColorPicker playerTwoColor = new ColorPicker(defaultColorTwo);
		
		ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
		
		for(int i = minSize; i < maxSize; i++ ){
			choiceBox.getItems().add(i);
		}
		choiceBox.setValue(defaultBoardSize);
		Button button = new Button("Play");
		button.setOnAction(e -> {
			color1 = playerOneColor.getValue();
			color2 = playerTwoColor.getValue();
			size = choiceBox.getValue();
			System.out.println("New Color'sRGB = "+color1.getRed()+ " " + color1.getGreen()+ " " + color1.getBlue());
			System.out.println("New Color'sRGB = "+color2.getRed()+ " " + color2.getGreen()+ " " + color2.getBlue());
			System.out.println("Size: " + size);
			this.writeChoiceToFile(filePath);
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(playerOneLabel, playerOneColor, playerTwoLabel, playerTwoColor,choiceBox, button);
		layout.setAlignment(Pos.TOP_LEFT);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	private void writeChoiceToFile(String filePath){
		try{
			Formatter writer = new Formatter(filePath);
			writer.format("%d\n%s\n%s", size,
				    getColorWebFormat(color1),
					getColorWebFormat(color2));
			
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static String getColorWebFormat(Color color){
		return String.format( "#%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ) );
	}
}
