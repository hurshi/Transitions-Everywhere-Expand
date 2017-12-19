package hurshi.github.com.expand;

import android.animation.Animator;
import android.view.ViewGroup;

import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionValues;

/**
 * Created by hurshi on 2017/12/19.
 */

public class RotatePlus extends Rotate {

    private float pivotXPercent = 0.5f;
    private float pivotYPercent = 0.5f;

    public RotatePlus setPivotXPercent(float value) {
        if (value < 0f) {
            throw new IllegalArgumentException("pivotXPercent cannot be negative!");
        }
        pivotXPercent = value;
        return this;
    }

    public RotatePlus setPivotYPercent(float value) {
        if (value < 0f) {
            throw new IllegalArgumentException("pivotYPercent cannot be negative!");
        }
        pivotYPercent = value;
        return this;
    }


    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        endValues.view.setPivotX(endValues.view.getWidth() * pivotXPercent);
        endValues.view.setPivotY(endValues.view.getHeight() * pivotYPercent);
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
