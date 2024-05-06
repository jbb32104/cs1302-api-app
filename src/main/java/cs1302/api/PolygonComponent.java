package cs1302.api;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

/**
 * Represents the Polygon Component that stores the
 * Stock Data and change.
 */
public class PolygonComponent extends HBox {
    Text ticker;
    Text open;
    Text close;
    Text dayChange;
    Text matchesSentiment;
    VBox content;


    /**
     * Constructor for a {@code PolygonComponent}.
     */
    public PolygonComponent() {
        super(10);
        content = new VBox(10);
        content.setPrefWidth(400);
        content.setPadding(new Insets(10, 10, 10, 70));
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(8));
        ticker = new Text("Stock Data Ticker: ");
        ticker.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        open = new Text("Open Market Value: ");
        open.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        close = new Text("Close Market Value: ");
        close.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        dayChange = new Text("Day Change: ");
        dayChange.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        matchesSentiment = new Text("Matches Sentiment: ");
        matchesSentiment.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        content.getChildren().addAll(ticker, open, close, dayChange, matchesSentiment);
        this.getChildren().addAll(content);
    }

     /**
     * Method to set the text for the market open given the parameter.
     * @param open the double to set the ticker to.
     */
    public void setOpen(double open) {
        this.open.setText("Open Market Value: " + String.format("%.3f", open));
    }

    /**
     * Method to set the close market value given the parameter.
     * @param close the double representing the close market value.
     */
    public void setClose(double close) {
        this.close.setText("Close Market Value: " + String.format("%.3f", close));
    }

    /**
     * Method to set the day change text given the parameter.
     * @param dayChange the double representing the day change.
     */
    public void dayChange(double dayChange) {
        this.dayChange.setText("Day Change: " + String.format("%.3f", dayChange));
    }

    /**
     * Setter method for the matchesSentiment text.
     * @param statement the statement to set the Matches Sentiment to.
     */
    public void setMatchesSentiment(String statement) {
        this.matchesSentiment.setText("Matches Sentiment: " + statement);
    }

    /**
     * Method to set the ticker Text given the parameter.
     * @param ticker the ticker to set in the text.
     */
    public void setTicker(String ticker) {
        this.ticker.setText("Stock Data Ticker: " + ticker);
    }
}
