package ro.pub.cs.systems.eim.practicaltest02;

import android.widget.EditText;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    ServerSocket serverSocket = null;
    EditText server_port = null;

    public String EUR_RATE;
    public String USD_RATE;

    boolean isRunning = false;

    public ServerThread(EditText server_port) {
        this.server_port = server_port;
    }

    public void startServer() {
        isRunning = true;

        EUR_RATE = "0.0";
        USD_RATE = "0.0";
        start();
    }

    public void stopServer() throws IOException {
        if (serverSocket != null)
            serverSocket.close();
    }

    @Override
    public void run() {
        try {
            Integer portint = new Integer(Integer.parseInt(server_port.getText().toString()));
            serverSocket = new ServerSocket(portint);
            while (isRunning) {
                Socket socket = serverSocket.accept();

                if (socket != null) {
                    //TODO

                    CommunicationThread comm = new CommunicationThread(socket, this);
                    comm.start();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
