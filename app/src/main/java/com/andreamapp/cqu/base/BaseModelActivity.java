package com.andreamapp.cqu.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.andreamapp.cqu.R;
import com.andreamapp.cqu.bean.Resp;

/**
 * Created by Andream on 2018/3/31.
 * Email: andreamapp@qq.com
 * Website: http://andreamapp.com
 */

public class BaseModelActivity<T extends Resp> extends AppCompatActivity implements Observer<T> {

    @ColorInt
    int primiryColor;
    @ColorInt
    int accentColor;

    public @ColorInt
    int getPrimiryColor() {
        return primiryColor;
    }

    public int getAccentColor() {
        return accentColor;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TypedValue typedValue = new TypedValue();

        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primiryColor = typedValue.data;

        getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        accentColor = typedValue.data;
    }

    @Override
    public void onChanged(@Nullable T t) {
        if (t == null) {
            return;
        }
        if (t.status()) {
            String msg = t.msg();
            if (msg != null) {
                Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            String err = t.err();
            if (err != null) {
                Snackbar.make(getWindow().getDecorView(), err, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Show the state text with specified msg and clickHandler.
     * Attention that if show=false, the msg and clickHandler would be ignored.
     * Subclass can override this method to add related action as state changed.
     *
     * @param show         show or hide
     * @param resId          the message would show
     * @param clickHandler the action when click state text
     * @return return false if has no TextView with id R.id.state_text
     */
    protected boolean showState(boolean show, int resId, View.OnClickListener clickHandler) {
        TextView stateText = findViewById(R.id.state_text);
        if (stateText != null) {
            if (show) {
                if (resId > 0) {
                    stateText.setText(getResources().getString(resId));
                }
                stateText.setVisibility(View.VISIBLE);
                stateText.setOnClickListener(clickHandler);
            } else {
                stateText.setVisibility(View.GONE);
            }
            return true;
        }
        return false;
    }

    /**
     * Show the state text with specified msg and clickHandler
     *
     * @param resId          the message would show
     * @param clickHandler the action when click state text
     * @return return false if has no TextView with id R.id.state_text
     */
    protected boolean showState(int resId, View.OnClickListener clickHandler) {
        return showState(true, resId, clickHandler);
    }

    /**
     * convenient for showState(CharSequence msg, View.OnClickListener clickHandler)
     *
     * @param resId the message would show
     * @return return false if has no TextView with id R.id.state_text
     */
    protected boolean showState(int resId) {
        return showState(resId, null);
    }

    protected boolean hideState() {
        return showState(false, 0, null);
    }
}
