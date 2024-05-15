package cs1302.api;

import java.time.DayOfWeek;
import javafx.application.Platform;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.time.format.DateTimeFormatter;

/**
 * NavBar class representing the Navigation Bar
 * component.
 */
public class NavBar extends HBox {
    DatePicker datePicker;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Button searchButton;
    Button stockPriceButton;
    final Text selectDate = new Text("Select Date");
    Text topTickersText;
    ComboBox<String> topTickers;

    /**
     * NavBar constructor method.
     */
    public NavBar() {
        super(10);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5));

        datePicker = new DatePicker();
        // set the Callback to make all of the Weekend DateCells disabled and red.
        Callback<DatePicker, DateCell> weekendCellFactory = this.weekendCellFactory();
        datePicker.setDayCellFactory(weekendCellFactory);
        // date formatting to (yyyy-MM-dd)
        this.setDatePickerConverter();
        datePicker.setPromptText("yyyy-MM-dd");

        searchButton = new Button("Search");
        searchButton.setPrefWidth(80);
        searchButton.setOnAction(e -> {
            System.out.println(this.getDate());
        });

        stockPriceButton = new Button("Get Stock Data");
        stockPriceButton.setPrefWidth(150);
        stockPriceButton.setDisable(true);
        topTickersText = new Text("Top Tickers");
        topTickers = new ComboBox<String>();
        topTickers.setPrefWidth(100);
        this.getChildren().addAll(selectDate, datePicker,
                searchButton, stockPriceButton, topTickersText, topTickers);
    } // NavBar()

    /**
     * Functional interface override to set all of the
     * weekends to red boxes since the market is closed.
     * 
     * @return the {@code Callback<DatePicker, DateCell>} that
     *         will be used in the DatePicker factory.
     *         This method will essentially modify all of the DateCells
     *         that are weekends and that are later than two years ago.
     *         This method overrides the updateItem method in the
     *         {@code DateCell} and checks whether the date is a
     *         weekend, is later than yesterday, or is more than two
     *         years ago.
     */
    private Callback<DatePicker, DateCell> weekendCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // Disable weekends (Saturday and Sunday)
                if (item.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        item.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        item.getYear() < 2022 ||
                        item.isAfter(LocalDate.now().plusDays(-1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: red;");
                } // if
            } // updateItem
        }; // datePicker
    } // weekendCellFactory

    /**
     * Method to set the converter of the DatePicker instance
     * variable to format the date into proper input.
     * format in the search bar will be (yyyy-MM-dd).
     * The DatePicker object automatically does (yyyy/mm/dd)
     * which this method overrides.
     */
    private void setDatePickerConverter() {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                } // else
            } // toString

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            } // fromString
        }); // setConverter
    } // setDatePickerConverter

    /**
     * Method to format the date from the value of the
     * date picker into the form yyyy-MM-dd.
     * 
     * @return the formatted date String.
     * @throws IllegalArgumentException if date can't be retrieved.
     */
    public String getDate() throws IllegalArgumentException {
        try {
            LocalDate selectedDate = datePicker.getValue();
            String formattedDate = this.dateFormatter.format(selectedDate);
            Platform.runLater(() -> datePicker.setPromptText(formattedDate));
            return formattedDate;
        } catch (Exception e) {
            throw new IllegalArgumentException("Please enter a Valid date");
        }
    } // getDate

    /**
     * Method to get the searchButton instance variable.
     * 
     * @return the search button.
     */
    public Button getSearchButton() {
        return this.searchButton;
    }

    /**
     * Getter method for the Stock Price button.
     * 
     * @return the stock price button.
     */
    public Button getStockPriceButton() {
        return this.stockPriceButton;
    }

    /**
     * Method to add a ticker to the ComboBox.
     * 
     * @param ticker the String to add.
     */
    public void addTopTicker(String ticker) {
        topTickers.getItems().addAll(ticker);
    }

    /**
     * Method to get the highlighted ticker.
     * 
     * @return the highlighted ticker.
     */
    public String getSelectedTicker() {
        return topTickers.getValue();
    }
} // NavBar
