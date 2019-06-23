package com.example.lenovo.geca;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class faq_main extends AppCompatActivity {
    FirebaseDatabase database;

    DatabaseReference myRef;
    MultiLevelListView lv2;
    QuestionAdapter qAdapter;
    private ChildEventListener mChildEventListener;
    ValueEventListener l1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Question> questions = new ArrayList<>();
        lv2 = (MultiLevelListView) findViewById(R.id.lv2);
        qAdapter = new QuestionAdapter(this, R.layout.outerlv_list_item, questions, lv2);
        lv2.setAdapter(qAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference().child("forum");
        // Read from the database
        addlistener();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(faq_main.this);
                alertDialogBuilder.setTitle("New Question");
                alertDialogBuilder.setMessage("Enter Question");
                final EditText input = new EditText(faq_main.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialogBuilder.setView(input);

                alertDialogBuilder.setPositiveButton("UPLOAD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "You clicked " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String cdt = sdf.format(new Date());
                        Question ques = new Question("student", input.getText().toString(), TEST.name, cdt, "question", 3, "", null);
                        myRef.push().setValue(ques);

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }


    public void addlistener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Question ques = dataSnapshot.getValue(Question.class);
                    ques.setKey(dataSnapshot.getKey());
                    qAdapter.add(ques);
                    qAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Question ques = dataSnapshot.getValue(Question.class);
                    ques.setKey(dataSnapshot.getKey());
                    for (int i = 0; i < qAdapter.getCount(); i++) {
                        Question q1 = qAdapter.getItem(i);
                        String k1 = q1.getKey();
                        if (k1 == ques.getKey()) {
                            qAdapter.getItem(i).setAnswers(ques.answers);
                            qAdapter.notifyDataSetChanged();
                        }

                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };

            myRef.addChildEventListener(mChildEventListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        qAdapter.clear();
    }

    public void UploadAnswer(View view) {
        View parentRow = (View) view.getParent();
        final int position = lv2.getPositionForView(parentRow);
        EditText et = (EditText) parentRow.findViewById(R.id.answer_et);
        String answer = et.getText().toString();


        if (answer != null && answer != "" && answer.length() != 0) {
            String key = qAdapter.getItem(position).getKey();
            DatabaseReference answerRef = database.getInstance().getReference().child("forum").child(key).child("answers");

            ArrayList<Answer> answerList = qAdapter.getItem(position).getAnswers();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String cdt = sdf.format(new Date());
            Answer ans = new Answer("student", answer, TEST.name, cdt, "answer", 3);
            answerList.add(ans);
            answerRef.setValue(answerList);
            et.setText("");
            et.clearFocus();
                    } else {
            Toast.makeText(this, "Cannot Submit Empty Answer", Toast.LENGTH_SHORT).show();
        }
    }

}
