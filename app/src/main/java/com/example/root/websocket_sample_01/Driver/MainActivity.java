package com.example.root.websocket_sample_01.Driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.root.websocket_sample_01.Connection.ConnectionWeb;
import com.example.root.websocket_sample_01.Interface.InterfceListener;
import com.example.root.websocket_sample_01.R;

public class MainActivity extends AppCompatActivity {


    private Button btnConncet, btnSend;
    private EditText edtURl, edtSend;
    private TextView txtConnect, txtMessage;
    private ConnectionWeb connectionWeb;
    private Listener listenerServer = new Listener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtConnect = findViewById(R.id.textStatus);
        txtMessage = findViewById(R.id.textMessage);
        edtURl = findViewById(R.id.editURL);
        edtSend = findViewById(R.id.editSendMessage);
        btnConncet = findViewById(R.id.btnConnect);
        btnSend = findViewById(R.id.btnSendMessage);

        btnConncet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionWeb = new ConnectionWeb(edtURl.getText().toString());
                connectionWeb.connection(listenerServer);

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionWeb.sendMessage(edtSend.getText().toString());
                edtSend.setText("");
            }
        });
    }

    public class Listener implements InterfceListener {

        @Override
        public void onMessage(String message) {
            txtMessage.setText(message);
        }

        @Override
        public void onStatus(ConnectionWeb.ConnectionStatus status) {
            String statusMsg = (status == ConnectionWeb.ConnectionStatus.CONNECTION_STATUS ?
                    getString(R.string.connect) : getString(R.string.disconnect));
            txtConnect.setText(statusMsg);
        }
    }
}
