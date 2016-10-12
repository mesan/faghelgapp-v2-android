package no.mesan.faghelg.view.program;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import no.mesan.faghelg.model.Event;
import no.mesan.faghelgapps.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private ItemClickListener itemClickListener;

    public EventAdapter(List<Event> events, ItemClickListener itemClickListener) {
        this.events = events;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View item = layoutInflater.inflate(R.layout.item_program, parent, false);

        return new EventViewHolder((EventItemView)item);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.eventItemView.bindTo(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final EventItemView eventItemView;

        public EventViewHolder(EventItemView eventItemView) {
            super(eventItemView);
            this.eventItemView = eventItemView;
            eventItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            itemClickListener.itemClick(events.get(position));
        }
    }

    public interface ItemClickListener {
        void itemClick(Event event);
    }

}
