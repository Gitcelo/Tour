package vidmot;

import Controller.TourController;
import Model.Tour;
import Model.TourDate;
import application.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TableView<Tour> tourTable;
    @FXML
    private TableView<TourDate> dateTable;
    @FXML
    private ComboBox<String> comboDiff;
    @FXML
    private TextField minLabel;
    @FXML
    private TextField maxLabel;
    @FXML
    private ComboBox<String> comboLoc;
    @FXML
    private TextField startLabel;
    @FXML
    private TextField endLabel;
    @FXML
    private ComboBox<Integer> comboSeats;
    @FXML
    private Button search;
    @FXML
    private Button book;
    private static final String[] diff = { "Handicap", "Easy", "Medium", "Hard" };
    private static final String[] loc = { "Reykjavík", "Akureyri", "Ísafjörður", "Egilsstaðir" };
    private final Integer[] seats = { 1, 2, 3, 4, 5 };
    private TourController tc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tc = new TourController();
        tourTable.setPlaceholder(new Label("Tours will appear here"));
        dateTable.setPlaceholder(new Label("Tour dates"));
        initComboBoxes();
    }

    private void initComboBoxes() {
        ObservableList<String> diffList = FXCollections.observableArrayList(diff);
        ObservableList<String> locList = FXCollections.observableArrayList(loc);
        ObservableList<Integer> seatList = FXCollections.observableArrayList(seats);
        comboDiff.setItems(diffList);
        comboLoc.setItems(locList);
        comboSeats.setItems(seatList);
    }

    @FXML
    private void searchHandler() {
        int difficulty = convertDifficulty(comboDiff.getSelectionModel().getSelectedItem());
        int location = convertLocation(comboLoc.getSelectionModel().getSelectedItem());
        int[] priceRange = {
                Integer.parseInt(minLabel.getText()),
                Integer.parseInt(maxLabel.getText())
        };
        String startDate = startLabel.getText();
        String endDate = endLabel.getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate[] dates = {
                LocalDate.parse(startDate, dateTimeFormatter),
                LocalDate.parse(endDate, dateTimeFormatter)
        };
        int groupSize = comboSeats.getSelectionModel().getSelectedItem();
        Parameters p = new Parameters(
                difficulty,
                priceRange,
                groupSize,
                dates,
                0,
                location
        );
        ObservableList<Tour> ot = tc.searchTour(p);
        for(Tour t: ot) {
            System.out.println(t.getTourName());
        }
    }

    private int convertLocation(String difficulty) {
        switch (difficulty) {
            case "Reykjavík":
                return 1;
            case "Akureyri":
                return 2;
            case "Ísafjörður":
                return 3;
            case "Egilsstaðir":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }

    private int convertDifficulty(String difficulty) {
        switch (difficulty) {
            case "Handicap":
                return 10;
            case "Easy":
                return 11;
            case "Medium":
                return 12;
            case "Hard":
                return 13;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }

}
