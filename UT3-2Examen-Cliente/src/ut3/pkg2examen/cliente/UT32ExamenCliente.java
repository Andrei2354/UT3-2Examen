package ut3.pkg2examen.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UT32ExamenCliente {

    public static void main(String[] args) {
        
        if (args.length < 2) {
            System.out.println("Error");
            System.exit(1);
        }
        
        // Dirección y puerto del servidor
        String host = args[0];
        int port = Integer.parseInt(args[1]);
          
        
        try {
            Socket conexion = new Socket(host, port);            
            BufferedReader input = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            PrintWriter output = new PrintWriter(conexion.getOutputStream(), true);
            
            
            // Comunicacion con el servidor
            // Envio
            boolean continuar = true;
            while (continuar) {             

                String answer = input.readLine();
                output.println("La respuesta del servidor es: " + answer); 
                if ("Acceso permitido.".equals(answer)) {
                    continuar = false;
                    output.println("Conexión finalizada");
                }
                if ("Acceso denegado.".equals(answer)) {
                    output.println("Acceso denegado.");
                }

            }
            output.flush();
            input.close();
            
        } catch (IOException ex) {
            
        }
    }
    
}
