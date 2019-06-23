package com.example.lenovo.geca;

import android.app.AlertDialog;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class feedback extends Fragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener{
    View my_v;
    EditText e1;
    Button b1;
    Firebase ref;
    RatingBar r;
    //AlertDialog.Builder builder;
    details ob=new details();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.fb, container, false);
        Firebase.setAndroidContext(my_v.getContext());
        e1=(EditText)my_v.findViewById(R.id.editText3);
        b1=(Button)my_v.findViewById(R.id.button);
        r = (RatingBar) my_v.findViewById(R.id.ratingbar);
        b1.setOnClickListener(this);
       // builder = new AlertDialog.Builder(getContext());
        ref = new Firebase("https://gecaproject.firebaseio.com/");
       // Toast.makeText(getContext(), "before rating", Toast.LENGTH_SHORT).show();
        r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getContext(), "after rating", Toast.LENGTH_SHORT).show();
                ref = new Firebase("https://gecaproject.firebaseio.com/");

                Toast.makeText(my_v.getContext(), "Stars: " + (int) rating, Toast.LENGTH_SHORT).show();
            }

        });

        return  my_v;
}

    @Override
    public void onClick(View view) {
//        r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                //Toast.makeText(getContext(), "after rating", Toast.LENGTH_SHORT).show();
//                ref = new Firebase("https://gecaproject.firebaseio.com/");
//                ref.child("feedback").child("Ratings").child(TEST.phone).setValue((int) rating);
//                Toast.makeText(my_v.getContext(), "Stars: " + (int) rating, Toast.LENGTH_SHORT).show();
//            }
//
//        });
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        String comments = ob.setComments(e1.getText().toString());
        ref = new Firebase("https://gecaproject.firebaseio.com/");
        ref.child("feedback").child("Comments").child(TEST.phone).setValue(comments);
       // Toast.makeText(getContext(), "feedback successfully submitted", Toast.LENGTH_SHORT).show();

       int x=(int) r.getRating();
        ref.child("feedback").child("Ratings").child(TEST.phone).setValue(x);
        e1.setText(" ");
        r.setRating(0);
        alertDialog.setMessage("Thanks for your feedback");
        alertDialog.show();

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        String s;
        details ob = new details();
        s = "" + v;

        ref.child("feedback").child("Rating").child(TEST.phone).setValue(ob);

    }
}
