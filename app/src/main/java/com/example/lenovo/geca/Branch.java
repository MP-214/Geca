package com.example.lenovo.geca;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Branch extends Fragment implements View.OnClickListener {
    View my_v;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        my_v = inflater.inflate(R.layout.branch, container, false);
        t1=(TextView)my_v.findViewById(R.id.tv);
        t1.setOnClickListener(this);
        t2=(TextView)my_v.findViewById(R.id.tv1);
        t2.setOnClickListener(this);
        t3=(TextView)my_v.findViewById(R.id.tv2);
        t3.setOnClickListener(this);
        t4=(TextView)my_v.findViewById(R.id.tv3);
        t4.setOnClickListener(this);
        t5=(TextView)my_v.findViewById(R.id.tv4);
        t5.setOnClickListener(this);
        t6=(TextView)my_v.findViewById(R.id.tv5);
        t6.setOnClickListener(this);
        t7=(TextView)my_v.findViewById(R.id.tv6);
        t7.setOnClickListener(this);
        t8=(TextView)my_v.findViewById(R.id.tv7);
        t8.setOnClickListener(this);
        t9=(TextView)my_v.findViewById(R.id.tv8);
        t9.setOnClickListener(this);
        t10=(TextView)my_v.findViewById(R.id.tv9);
        t10.setOnClickListener(this);
        t11=(TextView)my_v.findViewById(R.id.tv10);
        t11.setOnClickListener(this);
        t12=(TextView)my_v.findViewById(R.id.tv11);
        t12.setOnClickListener(this);
        t13=(TextView)my_v.findViewById(R.id.tv12);
        t13.setOnClickListener(this);
        t14=(TextView)my_v.findViewById(R.id.tv13);
        t14.setOnClickListener(this);
        t15=(TextView)my_v.findViewById(R.id.tv14);
        t15.setOnClickListener(this);
        t16=(TextView)my_v.findViewById(R.id.tv15);
        t16.setOnClickListener(this);
        t17=(TextView)my_v.findViewById(R.id.tv16);
        t17.setOnClickListener(this);
        t18=(TextView)my_v.findViewById(R.id.tv17);
        t18.setOnClickListener(this);
        return my_v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                int commit = fm.beginTransaction().replace(R.id.content_frame, new Aeronautical()).commit();
                break;
            case R.id.tv1:
                FragmentManager fm1 = getActivity().getSupportFragmentManager();
                int commit1 = fm1.beginTransaction().replace(R.id.content_frame, new Agricultural()).commit();
                break;
            case R.id.tv2:
                FragmentManager fm2 = getActivity().getSupportFragmentManager();
                int commit2 = fm2.beginTransaction().replace(R.id.content_frame, new Automobile()).commit();
                break;
            case R.id.tv3:
                FragmentManager fm3 = getActivity().getSupportFragmentManager();
                int commit3 = fm3.beginTransaction().replace(R.id.content_frame, new BioMedicalAndInstrumentation()).commit();
                break;
            case R.id.tv4:
                FragmentManager fm4 = getActivity().getSupportFragmentManager();
                int commit4 = fm4.beginTransaction().replace(R.id.content_frame, new Chemical()).commit();
                break;
            case R.id.tv5:
                FragmentManager fm5 = getActivity().getSupportFragmentManager();
                int commit5 = fm5.beginTransaction().replace(R.id.content_frame, new Civil()).commit();
                break;
            case R.id.tv6:
                FragmentManager fm6 = getActivity().getSupportFragmentManager();
                int commit6 = fm6.beginTransaction().replace(R.id.content_frame, new ComputerScience()).commit();
                break;
            case R.id.tv7:
                FragmentManager fm7 = getActivity().getSupportFragmentManager();
                int commit7 = fm7.beginTransaction().replace(R.id.content_frame, new Electrical()).commit();
                break;
            case R.id.tv8:
                FragmentManager fm8 = getActivity().getSupportFragmentManager();
                int commit8 = fm8.beginTransaction().replace(R.id.content_frame, new Entc()).commit();
                break;
            case R.id.tv9:
                FragmentManager fm9 = getActivity().getSupportFragmentManager();
                int commit9 = fm9.beginTransaction().replace(R.id.content_frame, new FibreandTextile()).commit();
                break;
            case R.id.tv10:
                FragmentManager fm10 = getActivity().getSupportFragmentManager();
                int commit10 = fm10.beginTransaction().replace(R.id.content_frame, new FoodAndTech()).commit();
                break;
            case R.id.tv11:
                FragmentManager fm11 = getActivity().getSupportFragmentManager();
                int commit11 = fm11.beginTransaction().replace(R.id.content_frame, new InformationTech()).commit();
                break;
            case R.id.tv12:
                FragmentManager fm12 = getActivity().getSupportFragmentManager();
                int commit12 = fm12.beginTransaction().replace(R.id.content_frame, new Instrumentation()).commit();
                break;
            case R.id.tv13:
                FragmentManager fm13 = getActivity().getSupportFragmentManager();
                int commit13 = fm13.beginTransaction().replace(R.id.content_frame, new Mechanical()).commit();
                break;
            case R.id.tv14:
                FragmentManager fm14 = getActivity().getSupportFragmentManager();
                int commit14 = fm14.beginTransaction().replace(R.id.content_frame, new OilAndPaintsTech()).commit();
                break;
            case R.id.tv15:
                FragmentManager fm15 = getActivity().getSupportFragmentManager();
                int commit15 = fm15.beginTransaction().replace(R.id.content_frame, new Plastic()).commit();
                break;
            case R.id.tv16:
                FragmentManager fm16 = getActivity().getSupportFragmentManager();
                int commit16 = fm16.beginTransaction().replace(R.id.content_frame, new Polymer()).commit();
                break;
            case R.id.tv17:
                FragmentManager fm17 = getActivity().getSupportFragmentManager();
                int commit17 = fm17.beginTransaction().replace(R.id.content_frame, new Production()).commit();
                break;
        }
    }
}