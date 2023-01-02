# Client-server app, key sharing using RSA and messages by 3des

![Run demo](/screenshots/demo.png?raw=true "Run demo")

- N, p,and q predefined.
- Server shares “e”→public key.
- Client receives“e”to useitin decryption.
- Server sends 3 messages encrypted by RSA to the client:
    - K1
    - K2
    - K3
- The client decrypts these 3 messages using the extended euclidean algorithm.
- Server sends a message “M” encrypted in 3des to the client: 
    - `E(D(E(M,K1),K2),K3)`
- The client decrypts the received message “M” using 
    - `D(E(D(M,K3),K2),K1)`
- Print the decrypted message.


## Connection
Server and client is connected using server socket on port `9090`
    
## How to use ?
1- Allow concurrent run for both server and client.   
2- Run Server first.  
3- Run client.
