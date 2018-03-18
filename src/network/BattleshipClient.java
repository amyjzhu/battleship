package network;



import java.io.*;
import java.net.*;

/**
 * Created by gijin on 2018-03-18.
 */
public class BattleshipClient {

    public static final String HOST_NAME = "battleship.zhucchini.ca";
    public static final int PORT_NUMBER = 8888;
    public static final BattleshipProtocol bsProtocol = new BattleshipProtocol();

    public static void startClient() throws IOException {

        try (Socket connection = new Socket(HOST_NAME, PORT_NUMBER);
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

            String serverResponse;

            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
                out.println(bsProtocol.processInput(serverResponse));
            }

        } catch (UnknownHostException e) {
            System.out.println("Is " + HOST_NAME + " actually started?");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Can't connect " + HOST_NAME + ". Computer mode instead?");
            System.exit(1);
        }
    }


}
