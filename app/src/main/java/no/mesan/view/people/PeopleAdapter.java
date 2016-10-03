package no.mesan.view.people;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelgapps.R;
import no.mesan.model.Person;

public class PeopleAdapter extends RecyclerView.Adapter {
    private List<Person> people = new ArrayList<>();

    public void setPeople(List<Person> people) {
        this.people.clear();
        this.people.addAll(people);

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View peopleItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item, parent, false);
        return new PeopleViewHolder((PeopleItemView) peopleItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PeopleViewHolder) holder).peopleItemView.bindTo(people.get(position));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    class PeopleViewHolder extends RecyclerView.ViewHolder {
        public final PeopleItemView peopleItemView;

        public PeopleViewHolder(PeopleItemView peopleItemView) {
            super(peopleItemView);
            this.peopleItemView = peopleItemView;
        }
    }
}
