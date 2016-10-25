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

    private static int VIEW_TYPE_TEXT = 0;
    private static int VIEW_TYPE_IMAGE = 1;

    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_TEXT) {
            View socialTextItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_text_item, parent, false);
            return new SocialAdapter.TextSocialViewHolder((TextSocialItemView) socialTextItemView);
        } else {
            View socialImageItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_image_item, parent, false);
            return new SocialAdapter.ImageSocialViewHolder((ImageSocialItemView) socialImageItemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!TextUtils.isEmpty(messages.get(position).getImageUrl())) {
            return VIEW_TYPE_TEXT;
        }
        return VIEW_TYPE_IMAGE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_IMAGE) {
            ((ImageSocialViewHolder) holder).imageSocialItemView.bindTo(messages.get(position));
        }
        else {
            ((TextSocialViewHolder) holder).textSocialItemView.bindTo(messages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class TextSocialViewHolder extends RecyclerView.ViewHolder {
        public final TextSocialItemView textSocialItemView;

        public TextSocialViewHolder(TextSocialItemView textSocialItemView) {
            super(textSocialItemView);
            this.textSocialItemView = textSocialItemView;
        }

    }

    class ImageSocialViewHolder extends RecyclerView.ViewHolder {
        public final ImageSocialItemView imageSocialItemView;

        public ImageSocialViewHolder(ImageSocialItemView imageSocialItemView) {
            super(imageSocialItemView);
            this.imageSocialItemView = imageSocialItemView;
        }

    }
}
