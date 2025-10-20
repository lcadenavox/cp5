import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;
import Connection;
public class ClientApp {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        RSAKeyPair keyPair = new RSAKeyPair();
        System.out.println("Cliente RSA local keys:");
        System.out.println(keyPair);

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Conectado ao servidor " + host + ":" + port);
            Connection conn = new Connection(socket, keyPair.n, keyPair.e, keyPair.d);

            // trocar chaves: cliente envia primeiro
            conn.exchangeKeys(true);
            System.out.println("Chave remota recebida: N=" + conn.getRemoteN() + " E=" + conn.getRemoteE());

            // thread leitor para receber mensagens
            Thread reader = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = conn.receiveDecryptedMessage()) != null) {
                        System.out.println("[Servidor] (decriptado) => " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Leitor finalizado: " + e.getMessage());
                }
            });
            reader.start();

            // writer: ler do console e enviar
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite mensagens para enviar ao servidor (linha vazia para sair).");
            while (true) {
                String line = sc.nextLine();
                if (line == null || line.equals("")) break;
                conn.sendEncryptedMessage(line);
            }

            System.out.println("Cliente encerrando...");
            conn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}