package pl.pobiegne.mobile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;


public class GradientTextView extends TextView {
    
    public GradientTextView(Context context) {
        this(context, null);
    }
    
    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView, defStyle, 0);
        
        int startColor = attributes.getColor(R.styleable.GradientTextView_startColor, Color.BLACK);
        int centerColor = attributes.getColor(R.styleable.GradientTextView_centerColor, Color.BLACK);
        int endColor = attributes.getColor(R.styleable.GradientTextView_endColor, Color.BLACK);
        
        Shader textShader = new LinearGradient(0, 0, getWidth(), getHeight(), new int[] {startColor, centerColor, endColor},
                        new float[] {0, 0.5f, 1}, TileMode.CLAMP);
        this.getPaint().setShader(textShader);
        if (isInEditMode()) {
            this.getPaint().setShader(textShader);
        }
        
        attributes.recycle();
    }
}