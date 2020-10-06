package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int address;
    private String status;
    private ServerSocket serverSocket;

    public Server(int _address) {
        turnServerOn(_address);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(address);
            System.out.println("Aguardando Conexão");

            while (true) {
                Socket socket = serverSocket.accept();
                treatConnection(socket);
                System.out.println("Server Ligado");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            status = "Desconectado";

            System.out.println("Erro: " + e.getMessage());
            if (e.getMessage().equals("Address already in use: NET_Bind")) {
                // Testar próxima porta
                turnServerOn(address + 1);
            }
        }
    }

    private void turnServerOn(int _address) {
        address = _address;
        status = "Conectado";
        Thread thread = new Thread(this);
        thread.start();
    }

    private void treatConnection(Socket _socket) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(_socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(_socket.getInputStream());

            // TODO tratamento
            System.out.println("Tratado");

            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public int getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
