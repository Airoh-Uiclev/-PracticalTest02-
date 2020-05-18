package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest02MainActivity extends AppCompatActivity {

    EditText server_port = null;
    Button server_connect = null;

    EditText client_address = null;
    EditText client_port = null;

    EditText client_currency = null;
    Button client_request = null;

    TextView currency_info = null;

    ServerThread serverThread = null;

    public class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.connect_button:
                    ClientAsyncTask task = new ClientAsyncTask(currency_info);
                    task.execute(client_address.getText().toString(), client_port.getText().toString(), client_currency.getText().toString());
                    break;
                case R.id.request_button:
                    if (serverThread == null) {
                        serverThread = new ServerThread(server_port);
                        serverThread.startServer();
                    }
                    break;
            }
        }
    }

    ButtonListener listener = new ButtonListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        server_port = (EditText)findViewById(R.id.server_port_edit_text);
        server_connect = (Button)findViewById(R.id.connect_button);
        //TODO listener
        server_connect.setOnClickListener(listener);

        client_address = (EditText)findViewById(R.id.client_address_edit_text);
        client_port = (EditText)findViewById(R.id.client_port_edit_text);
        client_currency = (EditText)findViewById(R.id.client_currency_edit_text);
        client_request = (Button) findViewById(R.id.request_button);
        //TODO listener
        client_request.setOnClickListener(listener);

        currency_info = (TextView)findViewById(R.id.currency_info_text_view);
    }
}
