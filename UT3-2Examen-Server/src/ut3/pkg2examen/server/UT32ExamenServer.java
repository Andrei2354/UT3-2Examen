package ut3.pkg2examen.server;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class UT32ExamenServer {
    static HashMap<String, String> login = new HashMap<>();
       
    public static void main(String[] args) {
        
        int serverPort = 5000;
        
        try{
            login.put("user01", "one.Password");
            login.put("user02", "two.Password");
            login.put("user03", "three.Password");

            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Servidor para generar números iniciado en el puerto " + serverPort + ".");
            
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Se permite 3 errores como máximo \n");

            ServerThread serverThread = new ServerThread(clientSocket);
            new Thread(serverThread).start();
            
             
            
            
        } catch (IOException e){
            
        }
    }

    static class ServerThread implements Runnable {
        
        private Socket socket;
        private BufferedReader input;
        private PrintWriter output;
        
        
        ServerThread(Socket socket){
            this.socket = socket;
        }
                
                
        @Override
        public void run(){
            try {
                System.out.println("Cliente conectado: " + socket.getInetAddress());
                int intentos = 3;
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream());
                while(intentos > 0){
                    output.println("Intento número "+ intentos +" de 3.");                              
                    output.println("Intoduce tu Usuario: ");
                    System.out.println("Intoduce tu Usuario: ");
                    String username = input.readLine();
                    output.println("Intoduce tu Contraseña: ");
                    System.out.println("Intoduce tu Contraseña: ");
                    String password = input.readLine();
                    String respuesta;

                    if ((login.containsKey(username)) &&
                        (login.get(username) != null) &&
                        (login.get(username).equals(password))){
                        respuesta = "Acceso permitido.";
                        System.out.println(respuesta);
                        output.flush();

                    } else{
                        respuesta = "Acceso denegado.";
                        intentos -= 1;
                        System.out.println(respuesta);
                        output.flush();
                        //output
                    }
                }
                if(intentos== 0){
                  // logica de sleep
                  intentos = 3;
                }
            }catch (IOException ex) {
            ex.printStackTrace();
            }                                 
        }            
    }      
}
