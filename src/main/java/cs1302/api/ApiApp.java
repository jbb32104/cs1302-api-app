package cs1302.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This app integrates the Wall Street Bets Reddit API and the Polygon
 * Historical Stock Data API. The search Button will query the Wall Street
 * Bets API and use the most popular stock result to query the Polygon API.
 * Additionally, with the query for the Wall Street Bets API, the Combo Box
 * containing all of the results is updated. This allows the user to select
 * different stocks based on the date they searched for and inspect the reddit
 * prediction and compare to the actual movement of the stock that day.
 *
 * The UI displays the Sentiment, which is the prediction of whether the stock will
 * be bearish (go down), or bullish (go up). It additionally displays the sentiment
 * score which is the strength prediction, where a higher magnitude means a stronger
 * confidence.
 *
 * The Ticker is displayed to indicate the stock that is being displayed.
 */
public class ApiApp extends Application {

    Stage stage;
    Scene scene;
    VBox root;

    WallStreetBetsResult[] wsResults;
    PolygonResult stockResult;
    NavBar navBar;
    WallStreetComponent wsContent;
    PolygonComponent stockContent;
    int wsQueries = 0;



    /**
     * This method Queries the WallStreetBets API based on the selected date in
     * the {@code DatePicker}. The method constructs a formatted URI to query
     * the api. If the method is unable to query the API, it will alert the
     * user.
     * The API is queried on a separate thread.
     */
    public void wsQuery() {
        String date = this.navBar.getDate();
        URI uri = ApiMethods.formatWSUri(date);
        System.out.println(uri);
        Platform.runLater(() -> navBar.getSearchButton().setDisable(true));
        Task<Void> wsReqTask = new Task<>() {
                @Override
                protected Void call() throws IOException, IllegalArgumentException {
                    wsResults = ApiMethods.makeWSReq(uri);
                    if (wsResults.length == 0) {
                        throw new IOException("No top Wall Street Bets posts for " + date);
                    }
                    return null;
                } //call()
            }; //wsReqTask
        wsReqTask.setOnFailed(event -> {
            Throwable e = wsReqTask.getException();
            System.out.println(e);
            Platform.runLater(() -> {
                alert(e);
                navBar.getSearchButton().setDisable(false);
            });
        });

        wsReqTask.setOnSucceeded(event -> {
            System.out.println("Wall Street Bets Query Successful.");
            Platform.runLater(() -> {
                navBar.getStockPriceButton().setDisable(false);
                wsContent.setTicker(wsResults[0].getTicker());
                wsContent.setNumComments(wsResults[0].getNumComments());
                wsContent.setSentiment(wsResults[0].getSentiment());
                wsContent.setSentimentScore(wsResults[0].getSentimentScore());
                navBar.topTickers.getItems().clear();
                for (WallStreetBetsResult result: wsResults) {
                    navBar.addTopTicker(result.getTicker());
                }
                navBar.topTickers.setValue(wsResults[0].ticker);
                polygonQuery();
            }); //runLater

        }); //setOnSucceeded

        Thread wsQueryThread = new Thread(wsReqTask);
        wsQueryThread.setDaemon(true);
        System.out.println("WS Query Task started");
        wsQueryThread.start();

    } //wsQuery


    /**
     * Method to query the polygon API and update all UI elements accordingly.
     * This method utilizes a {@code Task<Void>} to retrieve all of the
     * API queries and sets the UI on Task success.
     * If an Exception is thrown during the task, an alert pops up and lets
     * the user know that there is an error.
     * This query is for the Polygon API and can modify the Stock Component UI
     * and the Wall Street Bets UI depending on the selection of the {@code ComboBox}.
     */
    public void polygonQuery() {
        Platform.runLater(() -> disableButton(navBar.getStockPriceButton(), 6));
        Platform.runLater(() -> disableButton(navBar.getSearchButton(), 6));
        String ticker = this.navBar.getSelectedTicker();
        String date = this.navBar.getDate();
        URI uri = ApiMethods.formatStockUri(ticker, date);
        System.out.println(uri);
        Task<Void> polygonReqTask = new Task<>() {
            @Override
            protected Void call() throws IOException, IllegalArgumentException {
                stockResult = ApiMethods.makeStockReq(uri);
                System.out.println(stockResult);
                return null;
            }
        };
        polygonReqTask.setOnSucceeded((event) -> {
            double dayChange = stockResult.getClose() - stockResult.getOpen();
            Platform.runLater(() -> {
                for (WallStreetBetsResult result: wsResults) {
                    if (ticker.equalsIgnoreCase(result.ticker)) {
                        wsContent.setTicker(result.getTicker());
                        wsContent.setNumComments(result.getNumComments());
                        wsContent.setSentiment(result.getSentiment());
                        wsContent.setSentimentScore(result.getSentimentScore());
                    } //if
                } //for
                disableButton(navBar.getSearchButton(), 6);
                disableButton(navBar.getStockPriceButton(), 6);
                stockContent.setOpen(stockResult.getOpen());
                stockContent.setClose(stockResult.getClose());
                stockContent.setTicker(ticker);
                stockContent.dayChange(dayChange);
            }); //runLater
            //case if stock goes down and prediction is bearish
            for (WallStreetBetsResult result: wsResults) {
                if (result.ticker.equals(ticker)) {
                    if (dayChange < 0 && result.getSentiment().equalsIgnoreCase("Bearish") ||
                        dayChange > 0 && result.getSentiment().equalsIgnoreCase("Bullish")) {
                        Platform.runLater(() -> {
                            stockContent.setMatchesSentiment("YES");
                        }); //runLater
                    } else {
                        Platform.runLater(() -> {
                            stockContent.setMatchesSentiment("NO");
                        }); //runLater
                    } //else

                } //for
            }
        });
        polygonReqTask.setOnFailed(event -> {
            Throwable e = polygonReqTask.getException();
            Platform.runLater(() -> alert(e));
        });
        Thread polyThread = new Thread(polygonReqTask);
        polyThread.setDaemon(true);
        System.out.println("Polygon Query thread started.");
        polyThread.start();
    }

    /**
     * Method to disable the given button for the given duration.
     * @param seconds the duration to disable for.
     * @param button the button object to disable.
     */
    private static void disableButton(Button button, int seconds) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> button.setDisable(true)),
            new KeyFrame(Duration.seconds(seconds), e -> button.setDisable(false))
            );
        timeline.play();
    }


    /**
     * Method to alert the user when an exception is thrown.
     * @param cause the Throwable object of the cause.
     */
    private void alert(Throwable cause) {
        TextArea text = new TextArea(cause.toString());
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    }

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */

    public ApiApp() {
        root = new VBox();
        navBar = new NavBar();
        wsContent = new WallStreetComponent();
        stockContent = new PolygonComponent();
    } // ApiApp

    /** {@inheritDoc} */
    public void init() {
        try {
            navBar.getSearchButton().setOnAction(e -> {
                wsQuery();
            });

            navBar.getStockPriceButton().setOnAction(e -> {
                polygonQuery();
            });

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        root.getChildren().addAll(navBar, wsContent, stockContent);
        this.stage = stage;
        scene = new Scene(root);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
        root.requestFocus();
        System.out.println(scene.getWidth());
        System.out.println(scene.getHeight());

    } // start

} // ApiApp
