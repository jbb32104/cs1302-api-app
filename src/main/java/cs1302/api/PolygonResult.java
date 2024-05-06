package cs1302.api;

/**
 * Represents an instance of the object returned in
 * the API call to the Polygon Stocks API.
 * This class stores the open price
 */
public class PolygonResult {
    String symbol;
    double open;
    double close;

    @Override
    public String toString() {
        String output = "Ticker: " + symbol;
        output += "\nDay Open: " + String.format("%.3f$", open);
        output += "\nDay Close: " + String.format("%.3f$", close);
        return output;
    }

    /**
     * Method to get the open value for the Stock Result.
     * @return the double for the open value.
     */
    public double getOpen() {
        return open;
    }

    /**
     * Method to get the close value for the Stock Result.
     * @return the double for the close value.
     */
    public double getClose() {
        return close;
    }
}
