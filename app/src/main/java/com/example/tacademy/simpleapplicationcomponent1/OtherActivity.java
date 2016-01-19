package com.example.tacademy.simpleapplicationcomponent1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {


    TextView messageView;
    public static final String EXTRA_MESSAGE = "message"; // 인텐트 받는 곳의 상수는 받는곳에서 정의, 코드안에는 상수값을 넣지말고 정의를 하자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);


        messageView = (TextView)findViewById(R.id.text_message);
        Button btn = (Button)findViewById(R.id.btn_finish);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        messageView.setText(message);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
