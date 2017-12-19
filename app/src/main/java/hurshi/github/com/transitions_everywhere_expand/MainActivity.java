package hurshi.github.com.transitions_everywhere_expand;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

import hurshi.github.com.expand.RotatePlus;
import hurshi.github.com.expand.ScalePlus;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private Button tv;
    private Button scaleBtn, rotateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        scaleTest();
        rotateTest();
    }


    private RotatePlus rotatePlus;
    boolean isRotated = false;

    private void rotateTest() {
        rotatePlus = getRotatePlus();
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRotated) {
                    isRotated = false;
                    TransitionManager.beginDelayedTransition(constraintLayout, rotatePlus);
                    tv.setRotation(0);
                } else {
                    isRotated = true;
                    TransitionManager.beginDelayedTransition(constraintLayout, rotatePlus);
                    tv.setRotation(180);
                }
            }
        });
    }


    private ScalePlus scalePlus;

    private void scaleTest() {
        scalePlus = getScalePlus();
        scaleBtn.setOnClickListener(new View.OnClickListener() {
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

    private ScalePlus getScalePlus() {
        ScalePlus sp = new ScalePlus();
        sp.setPivotXPercent(0.5f);
        sp.setPivotYPercent(1f);
        sp.setScaleHorizontalEnable(false);
        return sp;
    }

    private RotatePlus getRotatePlus() {
        RotatePlus sp = new RotatePlus();
//        sp.setPivotXPercent(0.5f);
//        sp.setPivotYPercent(0.5f);
        return sp;
    }

    private void findViews() {
        constraintLayout = findViewById(R.id.constraintLayout);
        tv = findViewById(R.id.textTv);
        scaleBtn = findViewById(R.id.scaleBtn);
        rotateBtn = findViewById(R.id.rotateBtn);
    }
}
