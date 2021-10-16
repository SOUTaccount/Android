package first.example.beeradviser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Barmen extends AppCompatActivity {
    public static final String EXTR_MASSAGE = "soobwenie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barmen);
        Intent intent=getIntent();
        TextView textik=findViewById(R.id.tv_b);
        textik.setText(intent.getStringExtra(EXTR_MASSAGE));
    }
}