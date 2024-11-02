package ao.co.isptec.aplm.lab4_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddProductActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private Button buttonAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextProductName = findViewById(R.id.editTextProductName);
        buttonAddProduct = findViewById(R.id.buttonRegisterProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = editTextProductName.getText().toString();
                if (!productName.isEmpty()) {
                    new AddProductTask().execute(productName);
                } else {
                    Toast.makeText(AddProductActivity.this, "Por favor, insira um nome para o produto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class AddProductTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String productName = params[0];
            String urlString = "http://192.168.6.24:8080/products";

            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // Criar o JSON para enviar
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("name", productName);

                // Enviar o JSON
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Obter a resposta
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                    return "Produto adicionado com sucesso!";
                } else {
                    return "Erro ao adicionar produto: " + responseCode;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Erro: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(AddProductActivity.this, result, Toast.LENGTH_SHORT).show();
            // Limpar o campo de texto após adicionar
            editTextProductName.setText("");
        }
    }
}
