import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class CurrencyService {


    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/try/?format=json";

    public double getTryRate() {
        try {

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 5 seconds timeout


            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("⚠️ Could not connect to NBP API. Using default rate.");
                return 0.0;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();


            String json = content.toString();
            String searchKey = "\"mid\":";
            int startIndex = json.indexOf(searchKey);

            if (startIndex != -1) {

                int start = startIndex + searchKey.length();
                int end = json.indexOf("}", start); // Find the closing bracket or comma
                if (json.indexOf(",", start) != -1 && json.indexOf(",", start) < end) {
                    end = json.indexOf(",", start);
                }

                String rateString = json.substring(start, end);
                return Double.parseDouble(rateString);
            }

        } catch (Exception e) {
            System.out.println("⚠️ API Error: " + e.getMessage());
        }
        return 0.0;
    }
}