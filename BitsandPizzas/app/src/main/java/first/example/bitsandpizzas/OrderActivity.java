package first.example.bitsandpizzas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class OrderActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void onClickDone(View view){
        CharSequence text= "Your order has been update";
        int duration = Snackbar.LENGTH_SHORT;
        Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinat),text,duration);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(OrderActivity.this,"Undone!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        snackbar.show();
    }
}