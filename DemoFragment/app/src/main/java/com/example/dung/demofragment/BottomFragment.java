package com.example.dung.demofragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Dung on 8/6/2017.
 */

public class BottomFragment extends Fragment {

    TextView textView1;
    TextView textView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        textView1 = (EditText)view.findViewById(R.id.topImageText);
        textView2 = (EditText)view.findViewById(R.id.bottomImageText);

        return  view;
    }

    public void showTextInImage(String textTop, String textBottom){
        textView1.setText(textTop);
        textView2.setText(textBottom);
    }
}
