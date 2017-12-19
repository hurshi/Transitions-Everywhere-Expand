package hurshi.github.com.transitions_everywhere_expand;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

import hurshi.github.com.expand.ScalePlus;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView tv;
    Button button;
    ScalePlus scalePlus = new ScalePlus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraintLayout);
        tv = findViewById(R.id.textTv);
        button = findViewById(R.id.button);
        scalePlus.setPivotXPercent(0.5f);
        scalePlus.setPivotYPercent(1f);

        scalePlus.setScaleHorizontalEnable(false);
//        scalePlus.setDuration(5000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(constraintLayout, scalePlus);
                    tv.setVisibility(View.GONE);
                } else {
                    TransitionManager.beginDelayedTransition(constraintLayout, scalePlus);
                    tv.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
