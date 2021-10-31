package first.example.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.SurfaceControl;

public class TempActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_actvity);
        if (savedInstanceState==null){
            StopWatchFragment fr=new StopWatchFragment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.stopwatch_container,fr);
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }
}