package no.mesan.view.people;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class PeopleAdapter extends RecyclerView.Adapter {

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

    class PeopleViewHolder extends RecyclerView.ViewHolder {

        public PeopleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
