package ao.co.isptec.aplm.lab4_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchProductActivity extends AppCompatActivity {

    private ListView productListView;
    private ArrayList<String> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        productListView = findViewById(R.id.product_list);
        productList = new ArrayList<>();

        // Chama a tarefa para buscar produtos
        new FetchProductsTask().execute();
    }

    private class FetchProductsTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> products = new ArrayList<>();
            try {
                // Substitua pela sua URL
                String urlString = "http://192.168.6.24:8080/products"; // Para emulador Android
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parsear a resposta JSON
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String productName = jsonObject.getString("name");
                        products.add(productName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return products;
        }

        @Override
        protected void onPostExecute(ArrayList<String> products) {
            // Atualiza o ListView com os produtos recebidos
            ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchProductActivity.this,
                    android.R.layout.simple_list_item_1, products);
            productListView.setAdapter(adapter);
        }
    }
}
