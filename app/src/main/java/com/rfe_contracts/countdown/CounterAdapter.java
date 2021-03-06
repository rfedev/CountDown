package com.rfe_contracts.countdown;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rfe_contracts.countdown.CounterDB.CounterEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Richard on 12/09/2017.
 */

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.CounterViewHolder> {

    private List<CounterEntity> allCounters;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CounterViewHolder extends RecyclerView.ViewHolder {

        public long id;

        //public CardView cardView;
        public TextView name;
        public TextView date;
        public TextView desc;
        public TextView counter;
        public TextView note;

        public void setId(long id) {
            this.id = id;
        }

        public CounterViewHolder(View view) {
            super(view);
            //cardView = (CardView)view.findViewById(R.id.counter_cardview);
            name = (TextView)view.findViewById(R.id.counter_cardview_event_name);
            date = (TextView)view.findViewById(R.id.counter_cardview_event_date);
            desc = (TextView)view.findViewById(R.id.counter_cardview_event_desc);
            counter = (TextView)view.findViewById(R.id.counter_cardview_event_counter);
            note = (TextView)view.findViewById(R.id.counter_cardview_event_note);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CounterAddActivity.class);
                    intent.putExtra("id",id);
                    view.getContext().startActivity(intent);
                }
            });



        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CounterAdapter(List<CounterEntity> allCounters) {
        this.allCounters = allCounters;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public CounterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragement_counter_cardview_card, viewGroup, false);
        //set the view's size, margins, paddings and layout parameters
        //...

        return new CounterViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CounterViewHolder counterViewHolder, int i) {

        counterViewHolder.id = allCounters.get(i).id;

        counterViewHolder.name.setText(allCounters.get(i).name);
        Date date = allCounters.get(i).date;
        String string = (String) Functions.getStringFromDate(allCounters.get(i).date,"dd/MM/YYYY");
        counterViewHolder.date.setText(string);
//        counterViewHolder.date.setText(Functions.getStringFromDate(allCounters.get(i).date,"dd/MM/YYYY"));
        counterViewHolder.desc.setText(allCounters.get(i).desc);
        counterViewHolder.counter.setText(Functions.getCounterString(allCounters.get(i).date,true,6));
        counterViewHolder.note.setText(allCounters.get(i).note);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return allCounters.size();
    }
}

