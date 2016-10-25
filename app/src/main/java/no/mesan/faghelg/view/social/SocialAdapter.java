package no.mesan.faghelg.view.social;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelg.model.Message;
import no.mesan.faghelgapps.R;

public class SocialAdapter extends RecyclerView.Adapter {
    private List<Message> messages = new ArrayList<>();

    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View socialItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_item, parent, false);
        //return new SocialAdapter.SocialViewHolder((SocialItemView) socialItemView);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
