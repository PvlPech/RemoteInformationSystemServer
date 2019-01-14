package com.pvlpech;

import com.pvlpech.controller.SocketController;
import com.pvlpech.model.loader.FileLoader;
import com.pvlpech.model.loader.Loader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static int PORT = 3345;

    public static void main(String[] args) {
        Loader loader = new FileLoader();
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new SocketController(loader, clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
