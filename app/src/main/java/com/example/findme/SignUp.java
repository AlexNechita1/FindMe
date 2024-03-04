package com.example.findme;


import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static com.example.findme.LocationManager.getLatitude;
import static com.example.findme.LocationManager.getLongitude;
import static java.sql.Types.NULL;

public class SignUp extends AppCompatActivity {

    public Boolean jobCheck=false;
    public Boolean studentCheck=false;
    public double latJob;
    public double lngJob;
    public double latStud;
    public double lngStud;
    private static final String TAG = "DatabaseHelper";


        //doar job check
        private class InsertJobDataTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... params) {
                String fullName = params[0];
                String username = params[1];
                String password = params[2];
                String email = params[3];
                String locationJob = params[4];


                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(DatabaseConnection.DB_URL, DatabaseConnection.DB_USER, DatabaseConnection.DB_PASSWORD);
                    String sql = "INSERT INTO users (FULLNAME, USERNAME, PASSWORD, EMAIL, JOB, STUDENT, LOCATIONJ, LOCATIONS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, fullName);
                    statement.setString(2, username);
                    statement.setString(3, password);
                    statement.setString(4, email);
                    statement.setString(5, "Y"); // Job
                    statement.setString(6, "N"); // Student
                    statement.setString(7, locationJob); // LocationJ
                    statement.setString(8, String.valueOf(NULL)); // Locations

                    statement.executeUpdate();

                    statement.close();
                    connection.close();
                } catch (SQLException | ClassNotFoundException e) {
                    Log.e(TAG, "Eroare la inserarea valorilor în baza de date " + e.getMessage());
                }
                return null;
            }
        }
        //niciunul
        private class InsertNeitherDataTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... params) {
                String fullName = params[0];
                String username = params[1];
                String password = params[2];
                String email = params[3];

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(DatabaseConnection.DB_URL, DatabaseConnection.DB_USER, DatabaseConnection.DB_PASSWORD);
                    String sql = "INSERT INTO users (FULLNAME, USERNAME, PASSWORD, EMAIL, JOB, STUDENT, LOCATIONJ, LOCATIONS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, fullName);
                    statement.setString(2, username);
                    statement.setString(3, password);
                    statement.setString(4, email);
                    statement.setString(5, "N"); // Job
                    statement.setString(6, "N"); // Student
                    statement.setNull(7, NULL); // LocationJ
                    statement.setNull(8, NULL); // Locations

                    statement.executeUpdate();

                    statement.close();
                    connection.close();
                } catch (SQLException | ClassNotFoundException e) {
                    Log.e(TAG, "Eroare la inserarea valorilor în baza de date " + e.getMessage());
                }
                return null;
            }
        }
        //ambele
        private class InsertBothDataTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... params) {
                String fullName = params[0];
                String username = params[1];
                String password = params[2];
                String email = params[3];
                String locationJob = params[4];
                String locatiomStud = params[5];

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(DatabaseConnection.DB_URL, DatabaseConnection.DB_USER, DatabaseConnection.DB_PASSWORD);
                    String sql = "INSERT INTO users (FULLNAME, USERNAME, PASSWORD, EMAIL, JOB, STUDENT, LOCATIONJ, LOCATIONS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, fullName);
                    statement.setString(2, username);
                    statement.setString(3, password);
                    statement.setString(4, email);
                    statement.setString(5, "Y");
                    statement.setString(6, "Y");
                    statement.setString(7, locationJob);
                    statement.setString(8, locatiomStud);

                    statement.executeUpdate();

                    statement.close();
                    connection.close();
                } catch (SQLException | ClassNotFoundException e) {
                    Log.e(TAG, "Eroare la inserarea valorilor în baza de date " + e.getMessage());
                }
                return null;
            }
        }
        //doar student selectat
    private class InsertStudentDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String fullName = params[0];
            String username = params[1];
            String password = params[2];
            String email = params[3];
            String locationStud = params[4];
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DatabaseConnection.DB_URL, DatabaseConnection.DB_USER, DatabaseConnection.DB_PASSWORD);
                String sql = "INSERT INTO users (FULLNAME, USERNAME, PASSWORD, EMAIL, JOB, STUDENT, LOCATIONJ, LOCATIONS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, fullName);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, email);
                statement.setString(5, "N"); // Job
                statement.setString(6, "Y"); // Student
                statement.setNull(7,NULL); // LocationJ
                statement.setString(8, locationStud); // Locations

                statement.executeUpdate();

                statement.close();
                connection.close();
            } catch (SQLException | ClassNotFoundException e) {
                Log.e(TAG, "Eroare la inserarea valorilor în baza de date " + e.getMessage());
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        CheckBox checkBoxJob = findViewById(R.id.checkbox_job);
        CheckBox checkBoxStudent = findViewById(R.id.checkbox_student);

        TextInputLayout fullNameLayout = findViewById(R.id.textInputLayoutSignName);
        TextInputLayout usernameLayout = findViewById(R.id.textInputLayoutSignUser);
        TextInputLayout passwordLayout = findViewById(R.id.textInputLayoutSignPass);
        TextInputLayout emailLayout = findViewById(R.id.textInputLayoutSignEmail);

        String fullName = fullNameLayout.getEditText().getText().toString().trim();
        String username = usernameLayout.getEditText().getText().toString().trim();
        String password = passwordLayout.getEditText().getText().toString().trim();
        String email = emailLayout.getEditText().getText().toString().trim();


        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("Introduceți un nume de utilizator");
            return;
        } else {
            usernameLayout.setError(null);
        }

        if (password.length() < 6) {
            passwordLayout.setError("Parola trebuie să conțină cel puțin 6 caractere");
            return;
        } else {
            passwordLayout.setError(null);
        }

        if (fullName.length() < 6) {
            fullNameLayout.setError("Numele complet trebuie să conțină cel puțin 6 caractere");
            return;
        } else {
            fullNameLayout.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Introduceți o adresă de email validă");
            return;
        } else {
            emailLayout.setError(null);
        }


        checkBoxJob.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                Intent intent = new Intent(SignUp.this, Location.class);
                startActivity(intent);
                jobCheck=true;
                latJob = getLatitude();
                lngJob = getLongitude();
                System.out.println(latJob  + "  dsd " + lngJob);
            }
        });
        checkBoxStudent.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                Intent intent = new Intent(SignUp.this, Location.class);
                startActivity(intent);
                studentCheck=true;
                latStud = getLatitude();
                lngStud = getLongitude();
                System.out.println(latStud  + "  dsd " + lngStud);
            }
        });


        if(jobCheck){
            if(studentCheck){
             //student and job check
                new InsertBothDataTask().execute(fullName, username, password, email,String.valueOf(new LatLng(latJob , lngJob)), String.valueOf(new LatLng(latStud , lngStud)));
            }
            else{
                new InsertJobDataTask().execute(fullName, username, password, email, String.valueOf(new LatLng(latJob , lngJob)));
            }
        }else{
            if(studentCheck){
                new InsertStudentDataTask().execute(fullName, username, password, email,String.valueOf(new LatLng(latStud , lngStud)));

            }else{
                new InsertNeitherDataTask().execute(fullName, username, password, email);
            }

        }
        Button buttonYesAccount = findViewById(R.id.buttonYesAccount);
        buttonYesAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);

        });
    }

}