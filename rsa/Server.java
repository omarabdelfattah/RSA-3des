package rsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static rsa.RSA.chooseE;


public class Server {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        // Initialize the modulus and public exponent
//        System.out.println("Enter p");
//        int p = sc.nextInt();
//        System.out.println("Enter q");
//        int q = sc.nextInt();


        BigInteger p = new BigInteger("806077621");
        BigInteger q = new BigInteger("718962931");

        BigInteger e = new BigInteger("229");
        RSA rsa = new RSA(p,q, e);

        String  k1 = "1234567891231232";
        BigInteger c_k1 = rsa.encrypt(new BigInteger(k1));

        String  k2 = "6789567893636353";
        BigInteger c_k2 = rsa.encrypt(new BigInteger(k2));

        String  k3 = "9876567897567577";
        BigInteger c_k3 = rsa.encrypt(new BigInteger(k3));

        DES des = new DES();
        String m = "0777500007775000";
        String c_m1 = des.encrypt(m, k1);
        String c_m2 = des.decrypt(c_m1, k2);
        String c_m3 = des.encrypt(c_m2, k3);

        System.out.println("---------------------------------------");
        System.out.println("Sending Info to client => E = " + e);
        System.out.println("---------------------------------------");
        System.out.println("Sending k1 = " + k1 + " | cipher = "+ c_k1);
        System.out.println("Sending k2 = " + k2 + " | cipher = "+ c_k2);
        System.out.println("Sending k3 = " + k3 + " | cipher = "+ c_k3);
        System.out.println("---------------------------------------");
        System.out.println("3des part");
        System.out.println("---------------------------------------");
        System.out.println("Sending m = " + m + " | c_m1 = "+ c_m1);
        System.out.println("Sending c_m1 = " + c_m1 + " | c_m2 = "+ c_m2);
        System.out.println("Sending c_m2 = " + c_m2 + " | c_m3 = "+ c_m3);
        System.out.println("Sending message = " + m + " | cipher = "+ c_m3);

        ServerSocket serverSocket = new ServerSocket(9090);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send three encrypted messages to the client
        out.println(p);
        out.println(q);
        out.println(e);
        out.println(c_k1);
        out.println(c_k2);
        out.println(c_k3);
        out.println(c_m3);

    }
}
