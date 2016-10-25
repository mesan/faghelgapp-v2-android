package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import no.mesan.faghelgapps.R;

public class SocialItemAuthorInfoView extends RelativeLayout {
    @Bind(R.id.author_shortname)
    TextView textViewAuthorShortname;

    @Bind(R.id.author_image)
    ImageView imageViewAuthor;

    public SocialItemAuthorInfoView(Context context) {
        super(context);
    }

    public SocialItemAuthorInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SocialItemAuthorInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SocialItemAuthorInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
