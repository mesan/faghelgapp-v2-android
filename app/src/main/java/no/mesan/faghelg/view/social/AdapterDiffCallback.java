package no.mesan.faghelg.view.social;


import android.support.v7.util.DiffUtil;

import java.util.List;

import no.mesan.faghelg.model.Message;

public class AdapterDiffCallback extends DiffUtil.Callback {


    private final List<Message> oldMessages;
    private final List<Message> newMessage;

    public AdapterDiffCallback(List<Message> oldMessages, List<Message> newMessage) {

        this.oldMessages = oldMessages;
        this.newMessage = newMessage;
    }

    @Override
    public int getOldListSize() {
        return oldMessages.size();
    }

    @Override
    public int getNewListSize() {
        return newMessage.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getId() == newMessage.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMessages.get(oldItemPosition).getId() == newMessage.get(newItemPosition).getId();
    }
}
