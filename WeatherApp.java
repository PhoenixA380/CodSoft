package Weather;

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

    private final String API_KEY = "1231aae004d9f5e2b8a096eeebc4803a";
    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    private JTextField cityField;
    private JLabel cityLabel, tempLabel, humidityLabel, windLabel;
    private JButton searchButton;

    public WeatherApp() {
        setTitle("Weather Application");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Background Color
        getContentPane().setBackground(new Color(240, 240, 240));

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cityLabel = new JLabel("Enter city:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cityLabel.setForeground(Color.WHITE);

        cityField = new JTextField(20);
        searchButton = new JButton("Search");

        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(searchButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(inputPanel, gbc);

        // Output Panel
        JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBackground(new Color(255, 255, 255)); // White
        outputPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        GridBagConstraints outputGbc = new GridBagConstraints();
        outputGbc.gridx = 0;
        outputGbc.gridy = 0;
        outputGbc.insets = new Insets(5, 10, 5, 10);

        tempLabel = new JLabel("Temperature: ");
        tempLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        tempLabel.setForeground(new Color(0, 102, 204)); // Dark Blue

        humidityLabel = new JLabel("Humidity: ");
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        humidityLabel.setForeground(new Color(0, 102, 204)); // Dark Blue

        windLabel = new JLabel("Wind Speed: ");
        windLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        windLabel.setForeground(new Color(0, 102, 204)); // Dark Blue

        outputPanel.add(tempLabel, outputGbc);
        outputGbc.gridy++;
        outputPanel.add(humidityLabel, outputGbc);
        outputGbc.gridy++;
        outputPanel.add(windLabel, outputGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(outputPanel, gbc);

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
            app.setLocationRelativeTo(null); // Center the window
            app.setVisible(true);
        });
    }
}
