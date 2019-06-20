package android.example.com.medbot20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private Button up;
    private Button left;
    private Button down;
    private Button right;
    private String direction;
    private String motion;
    private Boolean flag_up;
    private Boolean flag_left;
    private Boolean flag_down;
    private Boolean flag_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        up = findViewById(R.id.button_up);
        left = findViewById(R.id.button_left);
        down = findViewById(R.id.button_down);
        right = findViewById(R.id.button_right);
        direction = "s";
        motion = "s";
        flag_up = true; flag_left = true; flag_down = true; flag_right = true;

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_up) {
                    flag_up = !flag_up;
                    motion = "f";
                    sendCoordinates();
                }
                else {
                    flag_up = !flag_up;
                    motion = "s";
                    sendCoordinates();
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_left) {
                    flag_left = !flag_left;
                    direction = "l";
                    sendCoordinates();
                }
                else {
                    flag_left = !flag_left;
                    direction = "s";
                    sendCoordinates();
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_down) {
                    flag_down = !flag_down;
                    motion = "b";
                    sendCoordinates();
                }
                else {
                    flag_down = !flag_down;
                    motion = "s";
                    sendCoordinates();
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag_right) {
                    flag_right = !flag_right;
                    direction = "r";
                    sendCoordinates();
                }
                else {
                    flag_right = !flag_right;
                    direction = "s";
                    sendCoordinates();
                }
            }
        });
    }

    private void sendCoordinates() {
        final String body = direction + "," + motion;
        final String url = "http://192.168.43.100:8000/controller/";

        final OkHttpClient client = new OkHttpClient();

        final RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), body);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.e("MainActivity", response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
