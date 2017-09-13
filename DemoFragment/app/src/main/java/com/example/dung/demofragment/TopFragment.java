package com.example.dung.demofragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Dung on 8/6/2017.
 */

public class TopFragment extends Fragment {

    TextView inputText1;
    TextView inputText2;
    Button button;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        inputText1 = (EditText)view.findViewById(R.id.inputText1);
        inputText2 = (EditText)view.findViewById(R.id.inputText2);
        button = (Button)view.findViewById(R.id.button_apply);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyText();
            }
        });

        return view;
    }

    void applyText(){
        String text1 = this.inputText1.getText().toString();
        String text2 = this.inputText2.getText().toString();

        this.mainActivity.showText(text1, text2);
    }

    // Ham nay duoc goi khi Fragment duoc ghep vao Activity:
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity)context;
        }
    }
}
