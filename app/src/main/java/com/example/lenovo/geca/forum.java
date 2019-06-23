package com.example.lenovo.geca;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class forum extends Fragment implements View.OnClickListener {
    View my_v;
    Firebase ref;
    TextView tv;
    EditText e1,e4;
    Button b,b1;
    Query q;
    details ob=new details();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v= inflater.inflate(R.layout.forum, container, false);
        Toast.makeText(my_v.getContext(), "phoneno"+TEST.phone, Toast.LENGTH_SHORT).show();
// recyclerview= (RecyclerView) my_v.findViewById(R.id.rview1);
        Firebase.setAndroidContext(my_v.getContext());

        e1=(EditText)my_v.findViewById(R.id.question);
        b=(Button)my_v.findViewById(R.id.post);
        b1=(Button)my_v.findViewById(R.id.view);
        b.setOnClickListener(this);
      b1.setOnClickListener(this);


        return my_v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {


            case R.id.post:

                String comments = ob.setComments(e1.getText().toString());

                ref = new Firebase("https://gecaproject.firebaseio.com/");

                ref.child("Question").child(TEST.phone).child(ref.push().getKey()).setValue(comments);
                break;
            case R.id.view:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                int commit = fm.beginTransaction().replace(R.id.content_frame, new Comments() ).commit();
                break;

        }

    }
}
