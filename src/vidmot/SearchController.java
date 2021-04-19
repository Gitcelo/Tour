package vidmot;

import Controller.ReservationController;
import Controller.TourController;
import Model.Tour;
import Model.TourDate;
import application.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TableColumn<Tour,String> cName;
    @FXML
    private TableColumn<Tour,String> cDesc;
    @FXML
    private TableColumn<Tour,Integer> cPrice;
    @FXML
    private TableColumn<Tour,String> cDiff;
    @FXML
    private TableColumn<Tour,String> cLoc;
    @FXML
    private TableColumn<Tour,String> cSafe;
    @FXML
    private TableColumn<Tour,String> cProv;
    @FXML
    private TableView<Tour> tourTable;
    @FXML
    private TableColumn<Tour,String> cDate;
    @FXML
    private TableColumn<Tour,String> cAvail;
    @FXML
    private TableView<TourDate> dateTable;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox bookBox;
    @FXML
    private Label fxBooked;
    private static final String[] diff = { "Handicap", "Easy", "Medium", "Hard" };
    private static final String[] loc = { "Reykjavík", "Akureyri", "Ísafjörður", "Egilsstaðir" };
    private final Integer[] seats = { 1, 2, 3, 4, 5 };
    private TourController tc;
    private ReservationController rc;
    private ObservableList<Tour> ot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tc = new TourController();
        rc = new ReservationController();
        searchBox.getChildren().get(1).requestFocus();
        tourTable.setPlaceholder(new Label("Tours will appear here"));
        dateTable.setPlaceholder(new Label("Tour dates"));
        initComboBoxes();
        initTableColumns();
    }

    private void initComboBoxes() {
        ObservableList<Node> searchList = searchBox.getChildren();
        ObservableList<String> diffList = FXCollections.observableArrayList(diff);
        ObservableList<String> locList = FXCollections.observableArrayList(loc);
        ObservableList<Integer> seatList = FXCollections.observableArrayList(seats);
        ((ComboBox<String>) searchList.get(0)).setItems(diffList);
        ((ComboBox<String>) searchList.get(3)).setItems(locList);
        ((ComboBox<Integer>) searchList.get(6)).setItems(seatList);
    }

    private void initTableColumns() {
        cName.setCellValueFactory(
                new PropertyValueFactory<Tour,String>("tourName")
        );
        cDesc.setCellValueFactory(
                new PropertyValueFactory<Tour,String>("description")
        );
        cPrice.setCellValueFactory(
                new PropertyValueFactory<Tour,Integer>("price")
        );
    }

    @FXML
    private void searchHandler() {
        ObservableList<Node> searchList = searchBox.getChildren();
        int difficulty = convertDifficulty(((ComboBox<String>) searchList.get(0)).getSelectionModel().getSelectedItem());
        int location = convertLocation(((ComboBox<String>) searchList.get(3)).getSelectionModel().getSelectedItem());
        int[] priceRange = {
                Integer.parseInt(((TextField) searchList.get(1)).getText()),
                Integer.parseInt(((TextField) searchList.get(2)).getText())
        };
        String startDate = ((TextField) searchList.get(4)).getText();
        String endDate = ((TextField) searchList.get(5)).getText();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate[] dates = {
                LocalDate.parse(startDate, dateTimeFormatter),
                LocalDate.parse(endDate, dateTimeFormatter)
        };
        int groupSize = ((ComboBox<Integer>) searchList.get(6)).getSelectionModel().getSelectedItem();
        System.out.println(groupSize);
        System.out.println(dates[0] +" "+dates[1]);
        Parameters p = new Parameters(
                difficulty,
                priceRange,
                groupSize,
                dates,
                0,
                location
        );
        ot = tc.searchTour(p);
        for(Tour t: ot) {
            System.out.println(t.getValidTour());
        }
        tourTable.setItems(ot);
    }

    @FXML
    private void bookHandler() {

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
