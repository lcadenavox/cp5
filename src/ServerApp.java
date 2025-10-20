import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) {
        int port = 5000;
        RSAKeyPair keyPair = new RSAKeyPair();
        System.out.println("Servidor RSA local keys:");
        System.out.println(keyPair);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor ouvindo na porta " + port + " ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getRemoteSocketAddress());

            Connection conn = new Connection(clientSocket, keyPair.n, keyPair.e, keyPair.d);

            // trocar chaves: neste exemplo, servidor recebe primeiro então envia
            conn.exchangeKeys(false);
            System.out.println("Chave remota recebida: N=" + conn.getRemoteN() + " E=" + conn.getRemoteE());

            // start thread de leitura de mensagens recebidas
            Thread reader = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = conn.receiveDecryptedMessage()) != null) {
                        System.out.println("[Cliente] (decriptado) => " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("Leitor finalizado: " + e.getMessage());
                }
            });
            reader.start();

            // writer: ler do console e enviar criptografado
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite mensagens para enviar ao cliente (linha vazia para sair).");
            while (true) {
                String line = sc.nextLine();
                if (line == null || line.equals("")) break;
                conn.sendEncryptedMessage(line);
            }

            System.out.println("Servidor encerrando...");
            conn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}