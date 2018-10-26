package net.querz.mcaselector.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TransparentStage extends Stage {

	private StackPane content;
	private int shadowSize = 10;

	public TransparentStage(Stage parent) {
		//create ONE Pane that contains all the elements
		initStyle(StageStyle.TRANSPARENT);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		initOwner(parent);

		Pane shadow = new Pane();
		shadow.getStyleClass().add("transparent-stage-shadow");

		Rectangle innerRect = new Rectangle();
		Rectangle outerRect = new Rectangle();
		shadow.layoutBoundsProperty().addListener(
				(observable, oldBounds, newBounds) -> {
					innerRect.relocate(
							newBounds.getMinX() + shadowSize,
							newBounds.getMinY() + shadowSize
					);
					innerRect.setWidth(newBounds.getWidth() - shadowSize * 2);
					innerRect.setHeight(newBounds.getHeight() - shadowSize * 2);

					outerRect.setWidth(newBounds.getWidth());
					outerRect.setHeight(newBounds.getHeight());

					Shape clip = Shape.subtract(outerRect, innerRect);
					shadow.setClip(clip);
				}
		);

		content = new StackPane(shadow);

		content.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.0);" +
						"-fx-background-insets: " + shadowSize + ";"
		);

		Scene scene = new Scene(content);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().addAll(parent.getScene().getStylesheets());
		setScene(scene);
	}

	public void setContent(Node node) {
		node.getStyleClass().add("transparent-stage-content");
		content.getChildren().add(node);
	}
}
