package rsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Receive  three encrypted messages from the server and decrypt them
        BigInteger p = new BigInteger(in.readLine());
        BigInteger q = new BigInteger(in.readLine());
        BigInteger e = new BigInteger(in.readLine());
        BigInteger c_k1 = new BigInteger(in.readLine());
        BigInteger c_k2 = new BigInteger(in.readLine());
        BigInteger c_k3 = new BigInteger(in.readLine());
        System.out.println("---------------------------------------");
        System.out.println("Server sent Info : "+ "E = " + e);
        System.out.println("---------------------------------------");
        RSA rsalgo = new RSA(p,q, e);
        BigInteger k1  = rsalgo.decrypt(c_k1);
        BigInteger k2  = rsalgo.decrypt(c_k2);
        BigInteger k3  = rsalgo.decrypt(c_k3);
        System.out.println("[Server] => cipher_k1  = " + c_k1 + " | k1  = " + k1);
        System.out.println("[Server] => cipher_k2  = " + c_k2 + " | k2  = " + k2);
        System.out.println("[Server] => cipher_k3  = " + c_k3 + " | k3  = " + k3);

        // read last message encrypted in 3des
        DES des = new DES();
        String c_m = in.readLine();
        String m3 = des.decrypt(c_m, String.valueOf(k3));
        String m2 = des.encrypt(m3, String.valueOf(k2));
        String m1 = des.decrypt(m2, String.valueOf(k1));
        System.out.println("---------------------------------------");
        System.out.println("3des part");
        System.out.println("---------------------------------------");
        System.out.println("[Server] => c_m  = " + c_m + " | m3  = " + m3);
        System.out.println("[Server] => m3  = " + m3 + " | m2  = " + m2);
        System.out.println("[Server] => m2  = " + m2 + " | m3  = " + m1);
        System.out.println("=> cipher : " + c_m + " | Original message  : " + m1);
    }
}
