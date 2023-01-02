package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;

    // Constructor that initi   alizes the modulus and public exponent
    public RSA(BigInteger p,BigInteger q, BigInteger e) {
        this.phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        this.n = p.multiply(q);
//        System.out.println("n = "+ n + " phi = "+this.phi);
        this.e = e;
    }

    public BigInteger encrypt(BigInteger message){
//        System.out.println("e = " + this.e + " n = "+this.n+" | m = " + message );
        BigInteger t = message.modPow(this.e,this.n);
        return t;
    }
    public BigInteger decrypt(BigInteger cipher){
//        System.out.println("phi = (p-1)*(q-1) = " + this.phi );
        BigInteger d =  new BigInteger(String.valueOf(modI(this.e,this.phi))) ; //Math.pow(e, -1) % phi;
//        BigInteger d =  new BigInteger(String.valueOf(this.e.modInverse(this.phi))); //Math.pow(e, -1) % phi;
//        System.out.println("d = Math.pow(e, -1) % phi = " + d );
        BigInteger m = cipher.modPow(d,this.n);
//        System.out.println("m = Math.pow(cipher, d) % n = " + m );
        return m;
    }
    public static BigInteger modI(BigInteger m , BigInteger n){
        BigInteger result = new BigInteger("-1");
        BigInteger mod = new BigInteger("-1");
        BigInteger temp_m = m;
        BigInteger temp_n = n;
        List<BigInteger> second_column = new ArrayList<>();
        BigInteger last_mod = new BigInteger("0");
        while(mod.compareTo(BigInteger.valueOf(0)) != 0){
            mod = n.mod(m);
            result =n.subtract(mod).divide(m);
            n = m;
            m = mod;
            second_column.add(result);
            if(mod.compareTo(BigInteger.valueOf(0)) > 0){
                last_mod = mod;
            }
        }
        m = temp_m;
        n = temp_n;
        List<BigInteger> third_column = new ArrayList<>();
        third_column.add(BigInteger.valueOf(0));
        third_column.add(last_mod);
        for(int i = second_column.size() -2 ; i > -1 ; i--){
            BigInteger temp = second_column.get(i).multiply(third_column.get(third_column.size() - 1)).add(third_column.get(third_column.size() - 2)) ;
            third_column.add(temp);
        }
        BigInteger x = third_column.get(third_column.size() - 1);
        BigInteger y = third_column.get(third_column.size() - 2);
        BigInteger ny  = n.multiply( y );
        BigInteger xm  = x.multiply( m );
        if((ny.subtract(xm)).compareTo(BigInteger.valueOf(1)) == 0 ){
            result =  (((x.multiply(BigInteger.valueOf(-1)).mod(n)).add( n)).mod(n));
        }else if((ny.multiply(BigInteger.valueOf(-1).add(xm))).compareTo(BigInteger.valueOf(1)) == 0 ){
            result = x;
        }
        return result;
    }
    // Function to return gcd of a and b
    static BigInteger gcd(BigInteger a, BigInteger b)
    {
        if (a.compareTo(BigInteger.valueOf(0)) == 0)
            return b;
        return gcd(b.mod(a), a);
    }
    public static BigInteger chooseE(BigInteger p , BigInteger q){
        BigInteger phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        BigInteger e = new BigInteger("0");
        while(e.compareTo(BigInteger.valueOf(1)) != 0 || e.compareTo(BigInteger.valueOf(1)) <= 0 || e.compareTo(phi) >= 0){
            e.add(new BigInteger("1")) ;
        }
        return e;
    }
}
