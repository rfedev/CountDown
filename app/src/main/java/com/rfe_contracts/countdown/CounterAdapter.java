package com.rfe_contracts.countdown;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rfe_contracts.countdown.CounterAdd.CounterAddActivity;
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

            // TODO: 02/10/2017 this is just a temporary implementation for longClickListener.
            //Will show the desc and note views if longclicked
            view.setOnLongClickListener(new View.OnLongClickListener() {
//                boolean visible;
                @Override
                public boolean onLongClick(View view) {

                    //TransitionManager.beginDelayedTransition(cardView);

                    if (desc.getVisibility() == View.GONE || note.getVisibility() == View.GONE) {
                        desc.setVisibility(View.VISIBLE);
                        note.setVisibility(View.VISIBLE);

                    } else {
                        desc.setVisibility(View.GONE);
                        note.setVisibility(View.GONE);
                    }

//                    visible = !visible;
//                    desc.setVisibility(visible ? View.GONE : View.VISIBLE);
//                    note.setVisibility(visible ? View.GONE : View.VISIBLE);


                    //true if the callback consumed the long click, false otherwise.
                    //ie. if we return false it would then go on to activate the normal onClick
                    //listener if one is implemented (which it is)
                    return true;
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
        String string = Functions.getStringFromDate(allCounters.get(i).date,"dd/MM/YYYY");
        counterViewHolder.date.setText(string);
        counterViewHolder.counter.setText(Functions.getCounterString(allCounters.get(i).date,true,6));
        counterViewHolder.desc.setText(allCounters.get(i).desc);
        counterViewHolder.note.setText(allCounters.get(i).note);

        //View.INVISIBLE hides the view but keeps the space for it.
        //View.GONE removes it from the layout.
        if (counterViewHolder.desc.getText().toString().trim().equals("")) {
            counterViewHolder.desc.setVisibility(View.GONE);
        }
        if (counterViewHolder.note.getText().toString().trim().equals("")){
            counterViewHolder.note.setVisibility(View.GONE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return allCounters.size();
    }

    public void addCounters(List<CounterEntity> allCounters){
        this.allCounters = allCounters;
        notifyDataSetChanged();
    }


}

