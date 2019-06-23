package com.example.lenovo.geca;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 7/30/2017.
 */

public class QuestionAdapter extends ArrayAdapter<Question> {

    MultiLevelListView lv2;
    Context context;
    public QuestionAdapter(Context context, int resource, List<Question> objects, MultiLevelListView lv2) {
        super(context, resource, objects);
        this.context=context;
        this.lv2=lv2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.outerlv_list_item, parent, false);
        }
        TextView question,name,time;
        question=(TextView)convertView.findViewById(R.id.question);
        name=(TextView)convertView.findViewById(R.id.name);
        time=(TextView)convertView.findViewById(R.id.time);
        Question ques = getItem(position);

        question.setText(ques.getMessage());
        name.setText(ques.getName());
        time.setText(ques.getTime());

        MultiLevelListView lv1=(MultiLevelListView)convertView.findViewById(R.id.lv1);
        AnswerAdapter aAdapter = new AnswerAdapter(context, R.layout.innerlv_list_item,ques.getAnswers());
        lv1.setAdapter(aAdapter);

        lv1.setParentListView(lv2);


        /*String s="";
        ArrayList<Answer> answer=ques.getAnswers();

        for (int i = 0; i <answer.size() ; i++) {
            s+="\n"+answer.get(i).getMessage();
        }
        name.setText(s);*/

        return convertView;
    }
}
