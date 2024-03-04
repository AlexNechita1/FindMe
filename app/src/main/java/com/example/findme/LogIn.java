package com.example.findme;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.textfield.TextInputLayout;
import java.sql.*;
import android.os.AsyncTask;

class DatabaseTask extends AsyncTask<Void, Void, Boolean> {
    private String username;
    private String password;
    private LoginCallback callback;
    private Exception exception;

    //interfata pentru a trimite rezultatul
    interface LoginCallback {
        void onLoginResult(boolean success);
    }

    public DatabaseTask(String username, String password, LoginCallback callback) {
        this.username = username;
        this.password = password;
        this.callback = callback;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(DatabaseConnection.DB_URL, DatabaseConnection.DB_USER, DatabaseConnection.DB_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT USERNAME, PASSWORD  FROM users");

            while (resultSet.next()) {
                String us = resultSet.getString("USERNAME");
                String pas = resultSet.getString("PASSWORD");

                if(us.equals(this.username) && pas.equals(this.password)){
                    return true;
                }else{
                    return false;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
           this.exception = e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

      return false;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (exception != null) {
            exception.printStackTrace();
            callback.onLoginResult(false);
        } else {
            callback.onLoginResult(result);
        }
    }
}

public class LogIn extends AppCompatActivity implements DatabaseTask.LoginCallback{

    private boolean authenticationSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextInputLayout textInputUsername = findViewById(R.id.textInputLayoutLogUser);
        TextInputLayout textInputPass = findViewById(R.id.textInputLayoutLogPass);
        Button buttonSubmit = findViewById(R.id.buttonSubmitLog);
        AppCompatTextView textErrorLog = findViewById(R.id.textErrorLog);

        buttonSubmit.setOnClickListener(v -> {
            String usernameInput = textInputUsername.getEditText().getText().toString().trim();
            String passInput = textInputPass.getEditText().getText().toString().trim();

            new DatabaseTask(usernameInput, passInput, this).execute();

            if(authenticationSuccess){
               //il trimit in feed



            }
        });

        Button buttonNoAccount = findViewById(R.id.buttonNoAccount);
        buttonNoAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);
            startActivity(intent);

        });
    }
    @Override
    public void onLoginResult(boolean success) {
        AppCompatTextView textErrorLog = findViewById(R.id.textErrorLog);
        if (success) {
            authenticationSuccess = true;
            textErrorLog.setText(" ");

        } else {
            authenticationSuccess = false;
            textErrorLog.setText("Datele de autentificare nu sunt corecte");
        }
    }
 }
