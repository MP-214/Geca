package com.example.lenovo.geca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

public class Layer1 extends Fragment implements View.OnClickListener {
    View my_v;
    Firebase ref;
    Query q;
    String email;
    ImageView i1, i2, i3, i4, i5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.layer1, container, false);
        i1 = (ImageView) my_v.findViewById(R.id.faq);
        i1.setOnClickListener(this);
        i2 = (ImageView) my_v.findViewById(R.id.prediction);
        i2.setOnClickListener(this);
        i3 = (ImageView) my_v.findViewById(R.id.ranking);
        i3.setOnClickListener(this);
        i4 = (ImageView) my_v.findViewById(R.id.chatbot);
        i4.setOnClickListener(this);
        Firebase.setAndroidContext(my_v.getContext());
        select();
        return my_v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faq:
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                int commit = fm.beginTransaction().replace(R.id.content_frame, new forum()).addToBackStack("Layer1").commit();
               Intent it1=new Intent(getContext(),faq_main.class);
               startActivity(it1);
                break;
            case R.id.prediction:
                Intent it= new Intent(Intent.ACTION_VIEW,Uri.parse("http://192.168.43.171:5000/predictorhtml"));
                //progressDialog.dismiss();
                startActivity(it);
                FragmentManager fm4 = getActivity().getSupportFragmentManager();
                int commit4=fm4.beginTransaction().addToBackStack("Layer1").commit();

                break;
            case R.id.ranking:
                Intent it2= new Intent(Intent.ACTION_VIEW,Uri.parse("http://192.168.43.171:5000/ranking"));
                //progressDialog.dismiss();
                startActivity(it2);
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().addToBackStack("Layer1").commit();
                break;
            case R.id.chatbot:
               FragmentManager fm3 = getActivity().getSupportFragmentManager();
                int commit3 = fm3.beginTransaction().replace(R.id.content_frame, new Chatbot()).addToBackStack("Layer1").commit();
                break;
        }
    }
    void select() {
        try {
            email = TEST.email;
             ref = new Firebase("https://gecaproject.firebaseio.com/details");
            q = ref.orderByKey();
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }
}