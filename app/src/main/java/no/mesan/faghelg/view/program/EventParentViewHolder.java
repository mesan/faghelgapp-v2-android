package no.mesan.faghelg.view.program;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelgapps.R;

public class EventParentViewHolder extends ParentViewHolder {

    @Bind(R.id.event_title)
    TextView title;

    @Bind(R.id.event_timestamp)
    TextView timestamp;

    public EventParentViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
