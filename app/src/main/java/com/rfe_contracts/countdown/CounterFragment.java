package com.rfe_contracts.countdown;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.rfe_contracts.countdown.CounterDB.CounterEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CounterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CounterFragment extends LifecycleFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView textDB;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //My Vars
//    private CounterDb counterDb;
//    private View view;
    private RecyclerView recyclerView;
    private CounterAdapter recyclerViewAdapter;
    private CounterViewModel viewModel;

    public CounterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_counter_recycler_view);
        recyclerViewAdapter = new CounterAdapter(new ArrayList<CounterEntity>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(recyclerViewAdapter);

        //The viewmodel will be created and added to the provider if it doesn't exist.
        viewModel = ViewModelProviders.of(this).get(CounterViewModel.class);
        viewModel.getAllCounters().observe(CounterFragment.this, new Observer<List<CounterEntity>>() {
            @Override
            public void onChanged(@Nullable List<CounterEntity> allCounters) {
                recyclerViewAdapter.addCounters(allCounters);
            }
        });



        //Delete button
        //ToDo: Implement a way to delete all using the CounterViewModel
        Button delete = (Button) view.findViewById(R.id.fragment_counter_delete);
        delete.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
//                counterDb.counterModel().removeAllCounters();
//                updateFragment();
                //viewModel.deleteCounter();
            }
        });
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        updateFragment();
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
