package com.example.lab4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> new RegisterTask().execute());
    }

    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // URL corrigida
                URL url = new URL("http://10.0.2.2:8080/api/register");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                User user = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                JSONObject json = new JSONObject();
                json.put("id", 1); // Defina um ID único se necessário
                json.put("username", user.getUsername());
                json.put("password", user.getPassword());

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                    return true;
                } else {
                    // Ler resposta do servidor para depuração
                    InputStream inputStream = conn.getInputStream();
                    String response = new BufferedReader(new InputStreamReader(inputStream))
                            .lines().collect(Collectors.joining("\n"));
                    System.out.println("Response: " + response); // Exibir resposta
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(RegisterActivity.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Falha no registro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
