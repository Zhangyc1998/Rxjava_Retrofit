package com.Glide;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

public class TargetLayout extends LinearLayout {

    private ViewTarget<TargetLayout, GlideDrawable> target;

    public TargetLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        target = new ViewTarget<TargetLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                TargetLayout view = getView();
                view.setBackground(resource);
            }
        };
    }

    public ViewTarget<TargetLayout, GlideDrawable> getTarget() {
        return target;
    }

    public void setTarget(
            ViewTarget<TargetLayout, GlideDrawable> target) {
        this.target = target;
    }
}
