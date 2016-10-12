package no.mesan.faghelg.view.program;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.view.ExpandableLayout;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private HashSet<Integer> expandedPositionSet;
    private Context context;
    private List<Event> events;

    public EventAdapter(Context context, List<Event> events) {
        expandedPositionSet = new HashSet<>();
        this.context = context;
        this.events = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View item = layoutInflater.inflate(R.layout.item_program, parent, false);

        return new EventViewHolder(item);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.expandable_layout)
        ExpandableLayout expandableLayout;

        @Bind(R.id.event_title)
        TextView txtTitle;

        @Bind(R.id.event_timestamp)
        TextView txtTimestamp;

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

        private EventViewHolder(final View view) {
            super(view);

            ButterKnife.bind(this, itemView);
        }

        private void updateItem(final int position) {
            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position, null);
                }
            });

            expandableLayout.setExpand(expandedPositionSet.contains(position));

            Event event = events.get(position);
            updateViews(event);
        }

        private void updateViews(Event event) {
            updateEventViews(event);

            Person responsible = event.getResponsible();
            if (responsible == null) {
                updateNoResponsible();
            } else {
                updateResponsibleViews(event);
            }
        }

        private void updateEventViews(Event event) {
            txtTitle.setText(event.getTitle());
            txtTimestamp.setText("19:00");//TODO
            txtDescription.setText(event.getDescription());
            Picasso.with(context).load(event.getEventImageUrl()).into(imgEvent);
        }

        private void updateNoResponsible() {
            txtName.setText("");
            txtNick.setText("");
            imgProfile.setImageDrawable(null);
        }

        private void updateResponsibleViews(Event event) {
            txtName.setText(event.getResponsible().getFullName());
            txtNick.setText(event.getResponsible().getShortName());

            String profileImageUrl = event.getResponsible().getProfileImageUrl();
            if (!TextUtils.isEmpty(profileImageUrl)) {
                Transformation transformation = new RoundedTransformationBuilder()
                        .oval(true)
                        .build();

                Picasso.with(context).load(profileImageUrl).transform(transformation).into(imgProfile);
            } else {
                imgProfile.setImageDrawable(null);
            }
        }

        private void registerExpand(int position, TextView textView) {
            if (expandedPositionSet.contains(position)) {
                removeExpand(position);
            } else {
                addExpand(position);
            }
        }

        private void removeExpand(int position) {
            expandedPositionSet.remove(position);
        }

        private void addExpand(int position) {
            expandedPositionSet.add(position);
        }
    }
}
