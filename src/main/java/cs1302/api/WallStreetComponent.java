package cs1302.api;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

/**
 * Represents a component that contains the content
 * for the WallStreetBets API response.
 */
public class WallStreetComponent extends HBox {
    VBox content;
    Text ticker;
    Text numComments;
    Text sentiment;
    Text sentimentScore;

    /**
     * Constructor for the WallStreetComponent object.
     * Contains a forward and back arrow with a content
     * to be displayed containing the info about the
     * WS Bets reddit post.
     */
    public WallStreetComponent() {
        super(10);
        // setting alignment and position
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(8));

        content = new VBox(10);
        content.setPrefWidth(400);
        content.setPadding(new Insets(10, 10, 10, 70));

        // setting fonts of Text elements
        ticker = new Text("Ticker:          ");
        ticker.setFill(Color.BLUE);
        ticker.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        numComments = new Text("# of comments:   ");
        numComments.setFill(Color.GREEN);
        numComments.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        sentiment = new Text("Sentiment:       ");
        sentiment.setFill(Color.PURPLE);
        sentiment.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        sentimentScore = new Text("Sentiment Score: ");
        sentimentScore.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        sentimentScore.setFill(Color.ORANGE);

        content.getChildren().addAll(ticker, numComments, sentiment, sentimentScore);

        this.getChildren().addAll(content);

    }

    /**
     * Method to set the text for the ticker given the parameter.
     * 
     * @param ticker the String to set the ticker to.
     */
    public void setTicker(String ticker) {
        this.ticker.setText("Ticker:                 " + ticker);
    }

    /**
     * Method to set the num comments text given the parameter.
     * 
     * @param numComments the integer number of comments to set
     *                    the text to.
     */
    public void setNumComments(int numComments) {
        this.numComments.setText("# of comments:       " + Integer.toString(numComments));
    }

    /**
     * Method to set the sentiment text given the parameter.
     * 
     * @param sentiment the sentiment to set the text to.
     */
    public void setSentiment(String sentiment) {
        this.sentiment.setText("Sentiment:         " + sentiment);
    }

    /**
     * Method to set the Sentiment Score text given the parameter.
     * 
     * @param sentimentScore the double sentiment score to set the text to.
     */
    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore.setText("Sentiment Score: " + String.format("%.3f", sentimentScore));
    }
}
