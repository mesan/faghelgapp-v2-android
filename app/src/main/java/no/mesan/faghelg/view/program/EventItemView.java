package no.mesan.faghelg.view.program;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelgapps.R;

public class EventItemView extends RelativeLayout {

    @Bind(R.id.event_title)
    TextView txtTitle;

    @Bind(R.id.event_timestamp)
    TextView txtTimestamp;

    @Bind(R.id.txtHosts)
    TextView txtHosts;

    public EventItemView(Context context) {
        super(context);
    }

    public EventItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void bindTo(Event event) {
        txtTitle.setText(event.getTitle());
        txtTimestamp.setText(event.getStartTime().toString("HH:mm"));
        txtHosts.setText(event.getHostNames());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
