package it.polimi.ingsw.communicationProtocolTest;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public abstract class Client {

    Socket s;
    DataOutputStream dos;
    BufferedReader br;
    BufferedReader kb;
    String str, str1;
    boolean serverUp;
    smGson smgson;

    public Client(){
        connection();

        smgson= new smGson();
        try {
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        kb = new BufferedReader(new InputStreamReader(System.in));
        serverUp = true;

        receive();
    }

    public void connection() {
        try {
            s = new Socket("localhost", 888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send() {
        try {
            if ((str = kb.readLine()).equals("exit"))
                closeConnection();
            else {
                Gson gson= new Gson();
                ClientFirstMessage message=new ClientFirstMessage(str);
                String text= gson.toJson(message,ClientFirstMessage.class);
                dos.writeBytes(text + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while (serverUp) {
            try {
                str1 = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (str1.equals("exit"))
                serverUp = false;
            else {
                ServerMessage message = smgson.deserialize(str1);
                message.processMessage(this);
            }

        }

        closeConnection();

    }

    public void closeConnection() {
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


