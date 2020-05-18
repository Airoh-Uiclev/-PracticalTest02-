package ro.pub.cs.systems.eim.practicaltest02;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {
    TextView currency_info;

    public ClientAsyncTask(TextView currency_info) {
        this.currency_info = currency_info;
    }

    public static BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String address = strings[0],
                    port = strings[1],
                    currency = strings[2];

            if (!currency.equals("USD") && !currency.equals("EUR")) {
                Log.d("Practic", "Invalid Currency");
                publishProgress("Invalid Currency");
                return null;
            }

            Integer portint = new Integer(Integer.parseInt(port));
            Socket socket = new Socket(address, portint);

            BufferedReader bufread = getReader(socket);
            PrintWriter prwr = getWriter(socket);

            prwr.println(currency);
            prwr.flush();
            String value = bufread.readLine();
            publishProgress(value);

            socket.close();
        } catch (Exception e) {
            Log.e("Practic", "Nu a mers");
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... result) {
        currency_info.setText(result[0]);
    }

    protected void onPostExecute(Bitmap result) {
    }
}