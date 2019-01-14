package com.pvlpech.controller;

import com.pvlpech.model.loader.Loader;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pvlpech on 11.11.2018.
 */
public class SocketController implements PropertyChangeListener, Runnable {

    private Loader loader;
    private static Socket clientDialog;
    DataOutputStream dataOutputStream;


    public SocketController(Loader loader, Socket clientDialog) {
        this.loader = loader;
        SocketController.clientDialog = clientDialog;
        loader.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Response for " + this.hashCode() + " clientSocket");
        JSONObject jsonObject = (JSONObject) evt.getNewValue();
        try {
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            dataOutputStream = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(clientDialog.getInputStream());
            while(!clientDialog.isClosed()){
                String entry = dataInputStream.readUTF();
                System.out.println("Request from client: "  + entry);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("operation", "get");
                jsonObject.put("entity", "group");

                loader.performAction(jsonObject);
    //                dataInputStream.close();
    //                dataOutputStream.close();
    //
    //                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
