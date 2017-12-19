package hurshi.github.com.expand;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionUtils;
import com.transitionseverywhere.TransitionValues;
import com.transitionseverywhere.Visibility;
import com.transitionseverywhere.extra.Scale;

/**
 * Created by hurshi on 2017/12/19.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ScalePlus extends Visibility {
    private boolean scaleHorizontalEnable = true;
    private boolean scaleVerticalEnable = true;
    private float pivotXPercent = 0.5f;
    private float pivotYPercent = 0.5f;

    private static final String PROPNAME_SCALE_X = "scale:scaleX";
    private static final String PROPNAME_SCALE_Y = "scale:scaleY";

    private float mDisappearedScale = 0f;

    public ScalePlus() {

    }

    public ScalePlus(float disappearedScale) {
        setDisappearedScale(disappearedScale);
    }

    public ScalePlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, com.transitionseverywhere.R.styleable.Scale);
        setDisappearedScale(a.getFloat(com.transitionseverywhere.R.styleable.Scale_disappearedScale, mDisappearedScale));
        a.recycle();
    }

    /**
     * @param disappearedScale Value of scale on start of appearing or in finish of disappearing.
     *                         Default value is 0. Can be useful for mixing some Visibility
     *                         transitions, for example Scale and Fade
     * @return This Scale object.
     */
    public ScalePlus setDisappearedScale(float disappearedScale) {
        if (disappearedScale < 0f) {
            throw new IllegalArgumentException("disappearedScale cannot be negative!");
        }
        mDisappearedScale = disappearedScale;
        return this;
    }

    public ScalePlus setPivotXPercent(float value) {
        if (value < 0f) {
            throw new IllegalArgumentException("pivotXPercent cannot be negative!");
        }
        pivotXPercent = value;
        return this;
    }

    public ScalePlus setPivotYPercent(float value) {
        if (value < 0f) {
            throw new IllegalArgumentException("pivotYPercent cannot be negative!");
        }
        pivotYPercent = value;
        return this;
    }

    public ScalePlus setScaleHorizontalEnable(boolean enable) {
        scaleHorizontalEnable = enable;
        return this;
    }

    public ScalePlus setScaleVerticalEnable(boolean enable) {
        scaleVerticalEnable = enable;
        return this;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        if (transitionValues.view != null) {
            transitionValues.values.put(PROPNAME_SCALE_X, transitionValues.view.getScaleX());
            transitionValues.values.put(PROPNAME_SCALE_Y, transitionValues.view.getScaleY());
        }
    }


    @Nullable
    private Animator createAnimation(final View view, float startScale, float endScale, TransitionValues values) {
        final float initialScaleX = view.getScaleX();
        final float initialScaleY = view.getScaleY();
        float startScaleX = initialScaleX * startScale;
        float endScaleX = initialScaleX * endScale;
        float startScaleY = initialScaleY * startScale;
        float endScaleY = initialScaleY * endScale;
        view.setPivotX(view.getWidth() * pivotXPercent);
        view.setPivotY(view.getHeight() * pivotYPercent);

        if (values != null) {
            Float savedScaleX = (Float) values.values.get(PROPNAME_SCALE_X);
            Float savedScaleY = (Float) values.values.get(PROPNAME_SCALE_Y);
            // if saved value is not equal initial value it means that previous
            // transition was interrupted and in the onTransitionEnd
            // we've applied endScale. we should apply proper value to
            // continue animation from the interrupted state
            if (savedScaleX != null && savedScaleX != initialScaleX) {
                startScaleX = savedScaleX;
            }
            if (savedScaleY != null && savedScaleY != initialScaleY) {
                startScaleY = savedScaleY;
            }
        }

        Animator resultAnimator;
        Animator horizontalAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX);
        Animator verticalAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY);
        if (scaleHorizontalEnable && scaleVerticalEnable) {
            view.setScaleX(startScaleX);
            view.setScaleY(startScaleY);
            resultAnimator = TransitionUtils.mergeAnimators(horizontalAnimator, verticalAnimator);
        } else if (scaleHorizontalEnable) {
            view.setScaleX(startScaleX);
            resultAnimator = horizontalAnimator;
        } else {
            view.setScaleY(startScaleY);
            resultAnimator = verticalAnimator;
        }
        addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
//                if (scaleHorizontalEnable) {
                view.setScaleX(initialScaleX);
//                }
//                if (scaleVerticalEnable) {
                view.setScaleY(initialScaleY);
//                }
                transition.removeListener(this);
            }
        });
        return resultAnimator;
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, final View view, TransitionValues startValues,
                             TransitionValues endValues) {
        return createAnimation(view, mDisappearedScale, 1f, startValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, final View view, TransitionValues startValues,
                                TransitionValues endValues) {
        return createAnimation(view, 1f, mDisappearedScale, startValues);
    }

}
