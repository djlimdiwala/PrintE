package com.example.lkh.printe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJL on 4/1/2018.
 */

public class fragment_inprogress extends Fragment {


    private ListView lv;
    private ArrayAdapter<String> adapter_0;
    private ArrayAdapter<String> adapter_01;
    private List<String> display_hostel;


    private String new_time;
    private String new_date;
    private List<save_job_information> values_0;
    private List<String> displayJobs_0;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db_reference;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        db_reference = FirebaseDatabase.getInstance().getReference();

        lv = (ListView) view.findViewById(R.id.listv);



        values_0 = new ArrayList<save_job_information>();
        displayJobs_0 = new ArrayList<>();
        adapter_0 = new ArrayAdapter<String>(getActivity(), R.layout.job_list, R.id.tV, displayJobs_0);

        display_hostel = new ArrayList<>();
        adapter_01 = new ArrayAdapter<String>(getActivity(), R.layout.job_list, R.id.t, display_hostel);



        refresh_list_0();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;




                db_reference.child("shop_owner").child(values_0.get(position).shop_id).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                save_shopowner_information user = dataSnapshot.getValue(save_shopowner_information.class);

                                Intent intent = new Intent(getActivity(), Inprogress.class);
                                intent.putExtra("jobID",values_0.get(pos).job_id);
                                intent.putExtra("file",values_0.get(pos).document_name);
                                intent.putExtra("location",user.h_no);
                                intent.putExtra("owner",user.name);
                                intent.putExtra("contact",user.mobile);
                                intent.putExtra("l_link",values_0.get(pos).document_link);
                                intent.putExtra("copies",values_0.get(pos).copies);


                                if (values_0.get(pos).coloured.equals("1"))
                                {
                                    intent.putExtra("cld1","1");
                                    intent.putExtra("cld","YES");
                                }
                                else
                                {
                                    intent.putExtra("cld1","0");
                                    intent.putExtra("cld","NO");
                                }

                                if (values_0.get(pos).double_sided.equals("1"))
                                {
                                    intent.putExtra("ds","YES");
                                    intent.putExtra("ds1","1");
                                }
                                else
                                {
                                    intent.putExtra("ds","NO");
                                    intent.putExtra("ds1","0");
                                }

                                if (values_0.get(pos).orientation.equals("1"))
                                {
                                    intent.putExtra("port","YES");
                                    intent.putExtra("port1","1");
                                }
                                else
                                {
                                    intent.putExtra("port","NO");
                                    intent.putExtra("port1","0");
                                }

                                if (!values_0.get(pos).pages.equals("Contact shop"))
                                {
                                    intent.putExtra("price","\u20B9 " + values_0.get(pos).pages);
                                }
                                else
                                {
                                    intent.putExtra("price",values_0.get(pos).pages);
                                }

                                intent.putExtra("u_id",values_0.get(pos).user_id);
                                intent.putExtra("s_id",values_0.get(pos).shop_id);
                                intent.putExtra("creat",values_0.get(pos).created_on);
                                intent.putExtra("end",values_0.get(pos).finished_on);
                                intent.putExtra("pag",values_0.get(pos).pages);

                                change_format(values_0.get(pos).created_on);
                                intent.putExtra("time",new_date + "   " + new_time);
                                startActivity(intent);




                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });










            }
        });

        return view;
    }


    public void refresh_list_0 ()
    {


        db_reference.child("jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                values_0.clear();
                displayJobs_0.clear();
                display_hostel.clear();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    save_job_information job = single.getValue(save_job_information.class);
                    if (job.finished_on.equals("-99") && job.user_id.equals(firebaseAuth.getCurrentUser().getUid()))
                    {
                        displayJobs_0.add(job.document_name);
                        values_0.add(job);
                        display_hostel.add(job.pages + "pages");
                    }

                    i++;
                }

//                for(i=0;i<displayJobs_0.size();i++){
//                    Log.e("element",displayJobs_0.get(i));
//                }
                lv.setAdapter(adapter_0);
//                lv.setAdapter(adapter_01);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void change_format(String s)
    {
        new_date = "";
        new_time = "";
        String[] elements = s.split("\\s+");
        Log.e("ele",elements[0]);
        Log.e("ele2",elements[1]);

        new_time = elements[1];
        String[] datee = elements[0].split("-");
        String[] timm = elements[1].split(":");

        if (datee[1].equals("01"))
        {
            new_date = datee[2] + " JAN " + datee[0];
        }
        else if (datee[1].equals("02"))
        {
            new_date = datee[2] + " FEB " + datee[0];
        }
        else if (datee[1].equals("03"))
        {
            new_date = datee[2] + " MAR " + datee[0];
        }
        else if (datee[1].equals("04"))
        {
            new_date = datee[2] + " APR " + datee[0];
        }
        else if (datee[1].equals("05"))
        {
            new_date = datee[2] + " MAY " + datee[0];
        }
        else if (datee[1].equals("06"))
        {
            new_date = datee[2] + " JUN " + datee[0];
        }
        else if (datee[1].equals("07"))
        {
            new_date = datee[2] + " JUL " + datee[0];
        }
        else if (datee[1].equals("08"))
        {
            new_date = datee[2] + " AUG " + datee[0];
        }
        else if (datee[1].equals("09"))
        {
            new_date = datee[2] + " SEP " + datee[0];
        }
        else if (datee[1].equals("10"))
        {
            new_date = datee[2] + " OCT " + datee[0];
        }
        else if (datee[1].equals("11"))
        {
            new_date = datee[2] + " NOV " + datee[0];
        }
        else if (datee[1].equals("12"))
        {
            new_date = datee[2] + " DEC " + datee[0];
        }
    }

}
