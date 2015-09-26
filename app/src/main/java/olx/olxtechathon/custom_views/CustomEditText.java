package olx.olxtechathon.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import olx.olxtechathon.R;

/**
 * Created by anurag on 26/09/15.
 */
public class CustomEditText extends LinearLayout {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ImageView left_image, right_image;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.custom_edittext, this);
        this.removeAllViews();
        this.addView(view);

        left_image = (ImageView) view.findViewById(R.id.left_image);
        right_image = (ImageView) view.findViewById(R.id.right_image);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText, 0, 0);
//        isFullScreen = typedArray.getBoolean(R.styleable.CrossFade_isFullScreenWidth, false);
//        isCrossFadeEnabled = typedArray.getBoolean(R.styleable.CrossFade_isCrossFadeEnabled, true);
    }

}
