package project.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Map<String, Double> latency = new HashMap<>();
    TextView result;
    ProgressBar progressBar;
    Spinner spinnerUser;
    Spinner spinnerEnvironment;
    Button authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latency.put("Cloud Server", 0.0);
        latency.put("Fog Server", 0.0);
        latency.put("Mobile Server", 0.0);

        result = findViewById(R.id.resultText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        final String[] spinnerUsers = new String[] {
                "S001", "S002", "S003", "S004", "S005", "S006", "S007", "S008", "S009", "S010"
        };

        String[] spinnerEnvironments = new String[] {
                "Cloud Server", "Fog Server", "Mobile", "Automatic"
        };
        spinnerUser  =  findViewById(R.id.spinnerUser);
        ArrayAdapter<String> adapterUsers = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerUsers);
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapterUsers);

        spinnerEnvironment =  findViewById(R.id.spinnerEnvironment);
        ArrayAdapter<String> adapterEnvironment = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerEnvironments);
        adapterEnvironment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnvironment.setAdapter(adapterEnvironment);

        authenticate = findViewById(R.id.auth);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                spinnerEnvironment.setEnabled(false);
                spinnerUser.setEnabled(false);
                authenticate.setEnabled(false);
                String user = spinnerUser.getSelectedItem().toString();
                String env = spinnerEnvironment.getSelectedItem().toString();
                String target;
                if(env.equals("Automatic")) env = getMininumLatency();
                if(env.equals("Mobile")){
                    mobileAuth(user);
                    Toast.makeText(MainActivity.this, "Mobile authentication",Toast.LENGTH_SHORT).show();
                }else if(env.equals("Fog Server")){
                    target = getResources().getString(R.string.fog);
                    auth(user, target, env);
                }else if(env.equals("Cloud Server")) {
                    target = "https://mc-project-261300.appspot.com/";
                    auth(user, target, env);
                    Log.e("SERVICE", "API Called");
                }
            }
        });

    }

    public String getMininumLatency(){
        Double min = 1000000000.0;
        String target = "Fog Server";
        for(Map.Entry<String,Double> entry : latency.entrySet()){
            if(entry.getValue() < min) {
                min = entry.getValue();
                target = entry.getKey();
            }
        }
        return target;
    }
    public void mobileAuth(String user)
    {
        //pass
    }
    public void auth(String user, String target, final String env) {
        AuthenticateRequest request = new AuthenticateRequest(user);
        RetrofitClient.getClient(target).create(APIService.class).authenticate(request).enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                if(response.isSuccessful()){
                    KNN knn = response.body().getKNN();
                    NB nb = response.body().getNB();
                    SVM svm = response.body().getSVM();
                    latency.put(env, (svm.getLatency() + knn.getLatency() + nb.getLatency())/3);
                    String resultText = "Naive Bayes : Accuracy - " + nb.getAccuracy() + " ,Latency - " + nb.getLatency();
                    resultText += "\nk-Nearest Neighbours : Accuracy - " + knn.getAccuracy() + " ,Latency - " + knn.getLatency();
                    resultText += "\nSupport Vector Machine : Accuracy - " + svm.getAccuracy() + " ,Latency - " + svm.getLatency();
                    result.setText(resultText);
                    progressBar.setVisibility(View.INVISIBLE);
                    spinnerEnvironment.setEnabled(true);
                    spinnerUser.setEnabled(true);
                    authenticate.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Service Failure", Toast.LENGTH_LONG).show();
                Log.e("SERVICE",t.getMessage()+"");
                progressBar.setVisibility(View.INVISIBLE);
                spinnerEnvironment.setEnabled(true);
                spinnerUser.setEnabled(true);
                authenticate.setEnabled(true);
            }

        });
    }
}
