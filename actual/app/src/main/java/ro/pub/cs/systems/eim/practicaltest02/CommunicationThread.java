package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread {

    Socket socket;
    ServerThread serverThread;

    public CommunicationThread(Socket socket, ServerThread server_thread) {
        socket = socket;
        serverThread = server_thread;
    }

    public static BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }


    @Override
    public void run() {
        try {
            BufferedReader reader = getReader(socket);
            PrintWriter writer = getWriter(socket);

            String currency = reader.readLine();

            String rate_float;

            if (currency.equals("EUR")) {
                rate_float = serverThread.EUR_RATE;
            } else {
                rate_float = serverThread.USD_RATE;
            }

            writer.println(rate_float);
            writer.flush();

            socket.close();
        } catch (IOException ioex) {
            Log.e("Practical", ioex.getMessage());
            ioex.printStackTrace();
        }
    }
}
