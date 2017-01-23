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
    
     static int puerto=5555;
     static int CONT=0;
    @Override
        public void run(){
         CONT++;   
        while(CONT<3){       
        try{
			System.out.println("Creando socket servidor");
	
			ServerSocket serverSocket=new ServerSocket();

			System.out.println("Realizando el bind");

			InetSocketAddress addr=new InetSocketAddress("localhost",puerto);
			serverSocket.bind(addr);

			System.out.println("Aceptando conexiones");

			Socket newSocket= serverSocket.accept();
                        puerto++;  
                        
                           
                        new Servidor().start();
                        

			System.out.println("Conexion recibida");

			InputStream is=newSocket.getInputStream();
			OutputStream os=newSocket.getOutputStream();
                        
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
                        
                        
                        
                        int resultado=operacion(num1,num2,sig);
                        os.write(resultado);
    
			System.out.println("Cerrando el nuevo socket");

			newSocket.close();

			System.out.println("Cerrando el socket servidor");

			serverSocket.close();

			System.out.println("Terminado");

			}catch (IOException e) {
			
            
            }}
        
        }
        
}



