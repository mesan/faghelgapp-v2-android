package no.mesan.faghelg.view.social;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelg.model.Message;
import no.mesan.faghelgapps.R;

public class SocialAdapter extends RecyclerView.Adapter {
    private List<Message> messages = new ArrayList<>();

    public interface ImageListener {
        void imageWasClicked(String url);
    }

    private final ImageListener imageListener;

    public SocialAdapter(ImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View socialMessageItemView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.social_message_item, parent, false);
        return new SocialAdapter.SocialMessageViewHolder((SocialMessageItemView) socialMessageItemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SocialMessageViewHolder) holder).socialMessageItemView.bindTo(messages.get(position), imageListener);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class SocialMessageViewHolder extends RecyclerView.ViewHolder {
        public final SocialMessageItemView socialMessageItemView;

        public SocialMessageViewHolder(SocialMessageItemView socialMessageItemView) {
            super(socialMessageItemView);
            this.socialMessageItemView = socialMessageItemView;
        }

    }
}
