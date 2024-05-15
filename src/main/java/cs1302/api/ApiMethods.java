package cs1302.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class that contains all of the methods in referencing the
 * Wall Street Bets Reddit API and the API to retrieve stock
 * data.
 */
public class ApiMethods {
    /** Represents the Http Client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    // builds and returns a HttpClient object

    /** Gson object used in parsing API results. */
    public static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Method to make an Http Request with a specified URI
     * and return an {@code WallStreetBetsResult[]}.
     * 
     * @param uri the specified {@code URI} to make the
     *            Http request to.
     * @return the Array result of the API call.
     * @throws IOException if there is an error making the request.
     */
    public static WallStreetBetsResult[] makeWSReq(URI uri)
            throws IOException {
        try {
            // request object
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            // response object

            HttpResponse<String> response = HTTP_CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());

            WallStreetBetsResult[] returnArray = GSON.fromJson(response.body(),
                    WallStreetBetsResult[].class);
            return returnArray;
        } catch (Exception e) {
            throw new IOException("Unable to make Http Request at " + uri.toString());
        } // catch
    } // makeHttpRequest

    /**
     * Method to format the Wall Street Bets URI.
     * 
     * @param date the date in form of YEAR-MO-DAY.
     * @return the {@code URI} created from the date param.
     * @throws IllegalArgumentException if the URI can't be created.
     */
    public static URI formatWSUri(String date) throws IllegalArgumentException {
        try {
            String uriString = "https://tradestie.com/api/v1/apps/reddit?date=";
            uriString += date;
            URI uri = new URI(uriString);
            return uri;
        } catch (Exception e) {
            throw new IllegalArgumentException("URI can't be created for WallStreet Bets.");
        } // catch
    } // formatWSUri

    /**
     * Method to make an Http Request with a specified URI
     * and return an {@code PolygonResult}.
     * 
     * @param uri the specified {@code URI} to make the
     *            Http request to.
     * @return the Object result of the API call.
     * @throws IOException if there is an error making the request.
     */
    public static PolygonResult makeStockReq(URI uri)
            throws IOException {
        try {
            // request object
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            // response object
            HttpResponse<String> response = HTTP_CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            PolygonResult stockObject = GSON.fromJson(response.body(),
                    PolygonResult.class);
            return stockObject;
        } catch (Exception e) {
            throw new IOException("Unable to make Http Request.");
        } // catch
    } // makeHttpRequest

    /**
     * Method to format the {@code URI} for the Polygon stock
     * API.
     * 
     * @param ticker the specified ticker to set in the
     *               formatted string.
     * @param date   the date to set in the formatted string.
     * @return the {@code URI} to the API based on the specified parameters.
     * @throws IllegalArgumentException if the URI can't be created.
     */
    public static URI formatStockUri(String ticker, String date)
            throws IllegalArgumentException {
        try {
            String uriString = "https://api.polygon.io/v1/open-close/";
            uriString += URLEncoder.encode(ticker, "UTF-8") + "/";
            uriString += URLEncoder.encode(date, "UTF-8");
            ;
            uriString += "?adjusted=true&apiKey=XtQnwq6TjekrGAMmwe8kjeAouVR5iVLA";
            URI uri = new URI(uriString);
            return uri;
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("URI can't be created for Polygon Stock API.");
        } // catch
    }

} // ApiMethods
