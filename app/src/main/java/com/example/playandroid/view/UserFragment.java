package com.example.playandroid.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.playandroid.R;


import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements IView{

    private static final String ARG_TEXT = "param1";
    private TextView mTextView;
    private View rootView;
    private String mText;
    private ImageButton mImageButton;
    private Button mHistoryButton;
    SharedPreferences sharedPreferences;
    public UserFragment() {
        // Required empty public constructor
    }


    public static UserFragment newInstance(String param1) {  //这个函数专门用于专门构造一个Fragment
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView==null){
            rootView =inflater.inflate(R.layout.fragment_user, container, false);
        }
        mTextView=rootView.findViewById(R.id.user_textview);
        mImageButton=rootView.findViewById(R.id.user_IB);
        mHistoryButton=rootView.findViewById(R.id.user_history);
        sharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("name","");
        if(!userId.isEmpty()){
            mTextView.setText(userId);
        }
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),LoginActivity.class);//给后面开启的活动传值
                startActivity(intent);
                getActivity().finish();
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            BufferedReader buff;
            List <String> strList;
            @Override
            public void onClick(View view) {
                try {
                    InputStream inputStream =getContext().openFileInput("history");
                    buff=new BufferedReader(new InputStreamReader(inputStream));
                    String strLine;
                    strList = new ArrayList<String>();
                    while ((strLine= buff.readLine()) != null) {
                        strList.add(strLine);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        buff.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent=new Intent(getContext(),HistoryActivity.class);//给后面开启的活动传值
                Bundle bundle = new Bundle();
                bundle.putSerializable("HistoryData",(Serializable)strList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;
    }
    @Override
    public void showData(ArrayList<?> list) {

    }
}