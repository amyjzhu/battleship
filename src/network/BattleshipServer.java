package network;


import java.net.*;
import java.io.*;

/**
 * Created by gijin on 2018-03-18.
 */
public class BattleshipServer {

    public static final String HOST_NAME = "battleship.zhucchini.ca";
    public static final int PORT_NUMBER = 8888;
    public static final BattleshipProtocol bsProtocol = new BattleshipProtocol();

    public static void startServer() {

        try (ServerSocket socket = new ServerSocket(PORT_NUMBER);
        Socket clientSocket = socket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String reply = bsProtocol.processInput(null);
            out.println(reply);

            String clientResponse;

            while ((clientResponse = in.readLine()) != null) {
                reply = bsProtocol.processInput(clientResponse);
                out.println(reply);
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
