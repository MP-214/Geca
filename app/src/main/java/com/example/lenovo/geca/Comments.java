package com.example.lenovo.geca;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.View;

import java.util.ArrayList;
import java.util.Map;

public class Comments extends Fragment {
    android.view.View my_v;
    Firebase ref;
    Query q;
    ListView lv;

    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.comment, container, false);
        Firebase.setAndroidContext(my_v.getContext());
        lv = (ListView) my_v.findViewById(R.id.lv);
        select();

        return my_v;
    }

    String fmobnumber = "", ques = "";
    ArrayList<String> ar=new ArrayList<>();
    void select() {

        try {
            ref = new Firebase("https://gecaproject.firebaseio.com/Question/");
            q = ref.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long totalSum = 0;
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {

                        fmobnumber = sn.getKey();
                        Toast.makeText(my_v.getContext(), "" + fmobnumber, Toast.LENGTH_SHORT).show();
                        for (DataSnapshot sn1 : sn.getChildren()) {
                            try {
                                {
                                    ques = (String) sn1.getValue();
                                    ar.add(ques);
                                    Log.d("ques", "" + ques);

                                }
                            } catch (Exception e) {
                            }

                            String[] arr=new String[ar.size()];
                            for (int i=0;i<ar.size();i++){
                                arr[i]=ar.get(i);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(my_v.getContext(), android.R.layout.simple_list_item_1, arr);
                            lv.setAdapter(adapter);
                            // Log.d("Mydata",""+sorted);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });


        } catch (Exception e) {
        }
    }

}




