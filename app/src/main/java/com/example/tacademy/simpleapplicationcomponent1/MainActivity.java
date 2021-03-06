package com.example.tacademy.simpleapplicationcomponent1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText inputView;
    private static final int REQUEST_CODE_OTHER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputView = (EditText)findViewById(R.id.edit_input);
        Button btn = (Button)findViewById(R.id.btn_other);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = inputView.getText().toString();
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);

                Person p = new Person();
                p.message = message;
                p.name = "junt";
                p.age = 23;

                intent.putExtra(OtherActivity.EXTRA_PERSON, p); //객체를 인텥트로 넣어서 보낼수도 있음

                startActivityForResult(intent, REQUEST_CODE_OTHER);

            }
        });

        btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("count", 100);
                startService(intent);
            }
        });

        btn = (Button)findViewById(R.id.btn_stop);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);
            }
        });

        btn = (Button)findViewById(R.id.btn_count);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myService != null) {

                    try {
                        int count = 0;
                        count = myService.getCount();
                        Toast.makeText(MainActivity.this, "count : " + count, Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    IMyService myService;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = IMyService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(MyService.ACTION_TEN_ZERO);
        registerReceiver(mReceiver, filter);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int count = intent.getIntExtra("count", 0);
            Toast.makeText(MainActivity.this, "Activity received Count : " + count, Toast.LENGTH_SHORT).show();
            setResultCode(Activity.RESULT_OK);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_OTHER){
            if(resultCode == RESULT_OK){
                String resultMessage = data.getStringExtra(OtherActivity.RESULT_MESSAGE);
                Toast.makeText(this, "result : " + resultMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Result Cancel", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
