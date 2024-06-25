package Weather;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp extends JFrame {

    private final String API_KEY = "f9c55b06c9d745a5e6e35bd11ce4cd7a";
    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    private JTextField cityField;
    private JLabel cityLabel, tempLabel, humidityLabel, windLabel;
    private JButton searchButton;

    public WeatherApp() {
        setTitle("Weather Application");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        cityLabel = new JLabel("Enter city:");
        cityField = new JTextField(15);
        searchButton = new JButton("Search");

        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(searchButton);

        JPanel outputPanel = new JPanel(new GridLayout(4, 1));
        tempLabel = new JLabel("Temperature: ");
        humidityLabel = new JLabel("Humidity: ");
        windLabel = new JLabel("Wind Speed: ");

        outputPanel.add(tempLabel);
        outputPanel.add(humidityLabel);
        outputPanel.add(windLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText().trim();
                if (!city.isEmpty()) {
                    fetchWeather(city);
                } else {
                    JOptionPane.showMessageDialog(WeatherApp.this, "Please enter a city name.");
                }
            }
        });
    }

    private void fetchWeather(String city) {
        String urlString = String.format(API_URL, city, API_KEY);
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Increase connection timeout (optional)
            connection.setConnectTimeout(10000); // 10 seconds

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                parseWeatherData(response.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch weather data. Response code: " + responseCode);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void parseWeatherData(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject wind = jsonObject.getJSONObject("wind");

        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        double windSpeed = wind.getDouble("speed");

        tempLabel.setText("Temperature: " + temperature + " °C");
        humidityLabel.setText("Humidity: " + humidity + "%");
        windLabel.setText("Wind Speed: " + windSpeed + " m/s");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherApp app = new WeatherApp();
            app.setVisible(true);
        });
    }
}
