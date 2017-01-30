/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mmoureperez
 */

public class Servidor extends Thread{

    /**
     * @param args the command line arguments
     */
  
    public static void main(String[] args) throws IOException{
       
        Servidor obx=new Servidor();
        //lanzamos el primer hilo
        obx.start();
       
        
    
    }
   //Metodo que hace la operación y devuleve el resultado
    public static int operacion(int numero1,int numero2,int sig){
        int res;
        if (sig==0) {
             res = numero1+numero2;
            System.out.println(res);
        } else if (sig==1) {
             res = numero1-numero2;
            System.out.println(res);
        } else if (sig==2) {
             res = numero1*numero2;
            System.out.println(res);
        } else {
             res = numero1/numero2;
            System.out.println(res);

        }
        return res;

    }
    /*puerto del servidor que irá cambiando para que no este en uso por dos clientes
    en este caso cada vez que creamos un puerto distinto porque si lanzamos los tres 
    al mismo tiempo sin esperar al que el primero acabase habría un fallo de conexión 
    por que el puerto estaría ocupado.
    Si tuvieramos la certeza de que el cliente 2 no se conectara hasta que el último 
    termine su conexión podriamos omitir cambiar de puerto cada vez que se cree otra 
    conexión
    */
     static int puerto=5555;
     
    @Override
        public void run(){
   
        try {
            
            
            
            
            System.out.println("Creando socket servidor");
            
            ServerSocket serverSocket=new ServerSocket();
            
            System.out.println("Realizando el bind");
            
            //Dirección del ip y puerto del servidor
            InetSocketAddress addr=new InetSocketAddress("localhost",puerto);
            //Asignamos la dirección al socket
            serverSocket.bind(addr);
            
            System.out.println("Aceptando conexiones");
            
            //Creamos el socket y iniciamos la aceptacion.
            Socket newSocket= serverSocket.accept();
            
            
            //Cambiamos el puerto para que pueda entrar el proximo cliente sin que se ocupe por varios.
            puerto++;
            
            /*lanzamos este hilo para una nueva conexión hasta que llegue al puerto 
            numero 5557 y no cree más para que no espere nuevas conexiones*/
            if(puerto<=5557){
                new Servidor().start();}
            
            
            System.out.println("Conexion recibida");
            
            //Flujo de salida de datos dirección cliente
            OutputStream os;
            
            os = newSocket.getOutputStream();
            
            
            //abrimos flujo de recogida de datos con cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            int num1;
            int num2;
            int sig;
            //recogemos los datos en variables distintas
            num1=entrada.read();
            System.out.println(num1);
            
            num2=entrada.read();
            System.out.println(num2);
            
            sig=entrada.read();
            
            System.out.println(sig);
            
            
            //calculamos el resultado llamando al método operación
            int resultado=operacion(num1,num2,sig);
            
            //enviamos los datos al cliente
            os.write(resultado);
            
            System.out.println("Cerrando el nuevo socket");
            //cerramos el Scoket
            newSocket.close();
            
            System.out.println("Cerrando el socket servidor");
            //cerramos la conexión
            serverSocket.close();
            
            System.out.println("Terminado");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            }
        }

        
        
        




