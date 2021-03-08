package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textField;

    @FXML
    private Button searchButton;

    @FXML
    private Label pressure;

    @FXML
    private Label min;

    @FXML
    private Label max;

    @FXML
    private Label feel;

    @FXML
    private Label temp;

    @FXML
    void initialize() {
        searchButton.setOnAction(event -> {
            String getUserCity = textField.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=82b797b6ebc625032318e16f1b42c016&units=metric");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp.setText("Температура : " + obj.getJSONObject("main").getDouble("temp"));
                    feel.setText("Ощущается : " + obj.getJSONObject("main").getDouble("feels_like"));
                    max.setText("Максимум : " + obj.getJSONObject("main").getDouble("temp_max"));
                    min.setText("Минимум : " + obj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Давление : " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line + "\n");
            }
            br.close();

        } catch (Exception e) {
            System.err.println("Город не найден!");
        }
        return content.toString();
    }

}
