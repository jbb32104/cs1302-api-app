package cs1302.api;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single instance of a WallStreetBets API result.
 */
public class WallStreetBetsResult {
    @SerializedName("no_of_comments")
    int numComments;
    String sentiment;
    @SerializedName("sentiment_score")
    double sentimentScore;
    String ticker;

    /**
     * toString method for the WallStreetResults object.
     * @return the string representation of the object.
     */
    @Override
    public String toString() {
        String output = "";
        output += "Ticker: " + ticker + "\n";
        output += "# of comments: " + Integer.toString(numComments) + "\n";
        output += "Sentiment: " + sentiment + "\n";
        output += "Sentiment Score: " + String.format("%.3f", sentimentScore);

        return output;
    } //toString

    /**
     * Getter method for the number of comments.
     * @return the number of comments.
     */
    public int getNumComments() {
        return this.numComments;
    }

    /**
     * Getter method for the Sentiment.
     * @return the String for the sentiment.
     */
    public String getSentiment() {
        return this.sentiment;
    }

    /**
     * Getter method for the ticker.
     * @return the String for the ticker.
     */
    public String getTicker() {
        return this.ticker;
    }

    /**
     * Getter method for the Sentiment Score.
     * @return the double for the sentiment score.
     */
    public double getSentimentScore() {
        return this.sentimentScore;
    }
}
