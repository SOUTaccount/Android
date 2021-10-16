package first.example.beeradviser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class FindBeerActivity extends AppCompatActivity {
private BeerExpert expert=new BeerExpert();
EditText message;
private int seconds = 0;
private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_beer_activity);
        if (savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();


        }
    public void OnClickListener (View view){
        TextView brands=findViewById(R.id.brands);
        Spinner chose=findViewById(R.id.Chose);
        String BeerType = String.valueOf(chose.getSelectedItem());
        List<String> beer = expert.getBrands(BeerType);
        StringBuilder builder=new StringBuilder();
        for (String brand:beer) {
        builder.append(brand).append("\n");
        }
        brands.setText(builder);
        }
        public void OnClickBarmen (View view){
        message=findViewById(R.id.send);
            String ddl=message.getText().toString();
            String ChoseBar=getString(R.string.chooser);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Barmen.EXTR_MASSAGE,ddl);
            Intent Chose = intent.createChooser(intent,ChoseBar);
            startActivity(Chose);



        }
        public void OnStartClick (View view){
        running=true;

        }
    public void OnStopClick (View view){
        running=false;

    }
    public void OnResumeClick (View view){
        running=false;
        seconds=0;

    }
    private void runTimer(){
        final TextView textView=(TextView)findViewById(R.id.textView);
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int sec=seconds%60;
                String time =String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,sec);
                textView.setText(time);
                if (running==true){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    public void OnSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running",running);
    }
    }
