package it.polimi.ingsw;

import it.polimi.ingsw.view.gui.CardView;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class used to test the print of the cards in GUI mode
 */
public class TestCards extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Example card initialization
        Corner[] corners;
        corners = new Corner[4];
        for (int i = 0; i < 4; i++) {
            corners[i] = new Corner(false);
        }
        Card exampleCard = new ResourceCard(Resource.ANIMAL_KINGDOM, "img_1_fronte.png", "img_1_retro.png", 0, PointType.GENERAL, new ArrayList<>(), corners);
        CardView cardView = new CardView(exampleCard, true);

        // Display dimensions
        String dimensions = "Width: " + CardView.CARD_WIDTH + " px, Height: " + CardView.CARD_HEIGHT + " px";
        Label dimensionsLabel = new Label(dimensions);

        VBox root = new VBox(10, cardView, dimensionsLabel);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("CardView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
