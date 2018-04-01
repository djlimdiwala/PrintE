package com.example.lkh.printe;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DJL on 4/1/2018.
 */

public class TabFragment extends Fragment {
    int position;

    private ListView lv;
    private ArrayAdapter<String> adapter_0;
    private ArrayAdapter<String> adapter_1;

    private List<save_job_information> values_0 = new ArrayList<save_job_information>();;
    private List<String> displayJobs_0;
    private List<save_job_information> values_1;
    private List<String> displayJobs_1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();

        lv = (ListView) view.findViewById(R.id.listv);



        displayJobs_0 = new ArrayList<>();
        adapter_0 = new ArrayAdapter<String>(getActivity(), R.layout.job_list, R.id.tV, displayJobs_0);

        values_1 = new ArrayList<save_job_information>();
        displayJobs_1 = new ArrayList<>();
        adapter_1 = new ArrayAdapter<String>(getActivity(), R.layout.job_list, R.id.tV, displayJobs_1);



        Log.e("position",Integer.toString(position));
        if (position == 0)
        {
            refresh_list_0();
//            for(int i=0;i<displayJobs_0.size();i++){
//                Log.e("element",displayJobs_0.get(i));
//            }
            Log.e("position1",Integer.toString(position));
        }
        else
        {
            refresh_list_1();
//            for(int i=0;i<displayJobs_1.size();i++){
//                Log.e("element",displayJobs_1.get(i));
//            }
            Log.e("position2",Integer.toString(position));
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                if (position == 0)
                {
//                    String text = values_0.get(position).document_name;
//                    Log.e("check",text);

                    Log.e("size",Integer.toString(displayJobs_0.size()));
                    Log.e("position000",Integer.toString(position));
                }
                else
                {
//                    String text = values_1.get(position).document_name;
//                    Log.e("check",text);

                    Log.e("size",Integer.toString(displayJobs_1.size()));
                    Log.e("position111",Integer.toString(position));
                }

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        textView = (TextView) view.findViewById(R.id.textView);
//
//        textView.setText("Fragment " + (position + 1));

    }


    public void refresh_list_0 ()
    {


        db_reference.child("jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                values_0.clear();
                displayJobs_0.clear();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    save_job_information job = single.getValue(save_job_information.class);
                    if (job.finished_on.equals("-99"))
                    {
                        displayJobs_0.add(job.document_name);
                        values_0.add(job);
                    }

                    i++;
                }

//                for(i=0;i<displayJobs_0.size();i++){
//                    Log.e("element",displayJobs_0.get(i));
//                }
                lv.setAdapter(adapter_0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void refresh_list_1 ()
    {


        db_reference.child("jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                values_1.clear();
                displayJobs_1.clear();

                for (DataSnapshot single : dataSnapshot.getChildren()) {

                    save_job_information job = single.getValue(save_job_information.class);
                    if (!job.finished_on.equals("-99"))
                    {
                        displayJobs_1.add(job.document_name);
                        values_1.add(job);
                    }

                    i++;
                }

                for(i=0;i<displayJobs_1.size();i++){
                    Log.e("element",displayJobs_1.get(i));
                }
                lv.setAdapter(adapter_1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
