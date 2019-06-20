package com.example.sookchat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.net.URL;

public class ChatbotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_chatbot,container,false);
        Button btn0=(Button) v.findViewById(R.id.btn_main);
        ImageButton btn1=(ImageButton) v.findViewById(R.id.btn_1);
        ImageButton btn2=(ImageButton) v.findViewById(R.id.btn_2);
        ImageButton btn3=(ImageButton) v.findViewById(R.id.btn_3);
        ImageButton btn4=(ImageButton) v.findViewById(R.id.btn_4);
        ImageButton btn5=(ImageButton) v.findViewById(R.id.btn_5);

        //toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_main);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        setHasOptionsMenu(true);
        btn0.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                    Intent intent0 = new Intent(getActivity(),WatsonActivity.class);
                    startActivity(intent0);
            }

        });

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Uri uri = Uri.parse("http://www.sookmyung.ac.kr/");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent1);
            }

        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("http://lib.sookmyung.ac.kr/");
                Intent intent2 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent2);
            }

        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("http://snowe.sookmyung.ac.kr/");
                Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent3);
            }

        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("http://portal.sookmyung.ac.kr/irj/portal");
                Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent3);
            }

        });

        btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("https://snowboard.sookmyung.ac.kr/");
                Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent3);
            }

        });



        return v;



    }

    @Override
    public void  onCreateOptionsMenu(Menu menu ,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_maintoolbar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_develop:
                    Intent developIntent = new Intent(getActivity(),developActivity.class);
                    startActivity(developIntent);
                    break;
            case R.id.action_mail:
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"jiho960214@sookmyung.ac.kr "});  //developer 's email
                Email.putExtra(Intent.EXTRA_SUBJECT,
                        "묻고자 하는 주제가 무엇인가요?"); // Email 's Subject
                Email.putExtra(Intent.EXTRA_TEXT, "To developers," + "");  //Email 's Greeting text
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


