package no.mesan.view.program;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import no.mesan.faghelgapps.R;

public class EventExpandableAdapter extends ExpandableRecyclerAdapter<EventParentViewHolder, EventChildViewHolder> {

    private LayoutInflater layoutInflater;

    public EventExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public EventParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.program_parent, viewGroup, false);
        return new EventParentViewHolder(view);
    }

    @Override
    public EventChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.program_child, viewGroup, false);
        return new EventChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(EventParentViewHolder eventParentViewHolder, int i, Object o) {
        EventParent event = (EventParent) o;
        eventParentViewHolder.title.setText(event.getTitle());
        eventParentViewHolder.timestamp.setText("Timestamp");
    }

    @Override
    public void onBindChildViewHolder(EventChildViewHolder eventChildViewHolder, int i, Object o) {
        EventChild eventChild = (EventChild) o;

        eventChildViewHolder.txtName.setText(eventChild.getLecturerName());
        eventChildViewHolder.txtNick.setText(eventChild.getLecturerNick());
        eventChildViewHolder.txtDescription.setText(eventChild.getEventDescription());

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                .borderWidthDp(2)
                .oval(true)
                .build();

        Picasso.with(mContext).load(eventChild.getEventImage()).into(eventChildViewHolder.imgEvent);

        if (!TextUtils.isEmpty(eventChild.getProfileImage())) {
            Picasso.with(mContext).load(eventChild.getProfileImage()).transform(transformation).into(eventChildViewHolder.imgProfile);
        }

    }
}
