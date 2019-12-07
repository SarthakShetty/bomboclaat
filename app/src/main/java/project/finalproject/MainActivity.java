package project.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    Map<String, Integer> latency = new HashMap<>();
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latency.put("Cloud Server", 0);
        latency.put("Fog Server", 0);
        latency.put("Mobile Server", 0);

        result = findViewById(R.id.resultText);

        String[] spinnerUsers = new String[] {
                "S001", "S002", "S003", "S004", "S005", "S006", "S007", "S008", "S009", "S010"
        };

        String[] spinnerEnvironments = new String[] {
                "Cloud Server", "Fog Server", "Mobile", "Automatic"
        };
        final Spinner spinnerUser =  findViewById(R.id.spinnerUser);
        ArrayAdapter<String> adapterUsers = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerUsers);
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapterUsers);

        final Spinner spinnerEnvironment =  findViewById(R.id.spinnerEnvironment);
        ArrayAdapter<String> adapterEnvironment = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerEnvironments);
        adapterEnvironment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnvironment.setAdapter(adapterEnvironment);

        Button authenticate = findViewById(R.id.auth);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = spinnerUser.getSelectedItem().toString();
                String env = spinnerEnvironment.getSelectedItem().toString();
                String target;
                if(env.equals("Automatic")) env = getMininumLatency();
                if(env.equals("Mobile")){
                    mobileAuth(user);
                    Toast.makeText(MainActivity.this, "Mobile authentication",Toast.LENGTH_SHORT).show();
                }else if(env.equals("Fog Server")){
                    target = getResources().getString(R.string.fog);
                    auth(user, target);
                }else if(env.equals("Cloud Server")) {
                    target = "https://mc-project-261300.appspot.com/mc/";
                    auth(user, target);
                }
            }
        });

    }

    public String getMininumLatency(){
        int min = 1000000000;
        String target = "Fog Server";
        for(Map.Entry<String,Integer> entry : latency.entrySet()){
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
    public void auth(String user, String target) {
        AuthenticateRequest request = new AuthenticateRequest(user);
        RetrofitClient.getClient(target).create(APIService.class).authenticate(request.getUser(), request.getModel()).enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                if(response.isSuccessful()){
                    KNN knn = response.body().getKnn();
                    NB nb = response.body().getNb();
                    SVM svm = response.body().getSvm();
                    String resultText = "NB : Accuracy - " + nb.getAccuracy() + " ,Latency - " + nb.getLatency();
                    resultText += "\nKNN : Accuracy - " + knn.getAccuracy() + " ,Latency - " + knn.getLatency();
                    resultText += "\nSVM : Accuracy - " + svm.getAccuracy() + " ,Latency - " + svm.getLatency();
                    result.setText(resultText);
                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Service Failure", Toast.LENGTH_LONG).show();
            }

        });
    }
}
