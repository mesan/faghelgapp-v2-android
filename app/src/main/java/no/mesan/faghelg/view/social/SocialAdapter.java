package no.mesan.faghelg.view.social;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.view.people.PeopleAdapter;

public class SocialAdapter extends RecyclerView.Adapter {
    private List<Message> messages = new ArrayList<>();

    public void setMessages(List<Person> messages, PeopleAdapter.PersonClickListener personClickListener) {
        this.personClickListener = personClickListener;
        this.people.clear();
        this.people.addAll(people);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
