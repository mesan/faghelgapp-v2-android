package no.mesan.view.program;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelgapps.R;

public class EventChildViewHolder extends ChildViewHolder {

    @Bind(R.id.imgEvent)
    ImageView imgEvent;

    @Bind(R.id.imgProfile)
    ImageView imgProfile;

    @Bind(R.id.txtName)
    TextView txtName;

    @Bind(R.id.txtNick)
    TextView txtNick;

    @Bind(R.id.txtDescription)
    TextView txtDescription;

    public EventChildViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(itemView);
    }
}
