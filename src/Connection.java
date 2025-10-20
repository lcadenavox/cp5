import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    // minhas chaves
    private final BigInteger myN;
    private final BigInteger myE;
    private final BigInteger myD;

    // chave remota (do outro lado)
    private BigInteger remoteN;
    private BigInteger remoteE;

    public Connection(Socket socket, BigInteger myN, BigInteger myE, BigInteger myD) throws IOException {
        this.socket = socket;
        this.myN = myN;
        this.myE = myE;
        this.myD = myD;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // envia minha chave pública no formato N|E (uma linha)
    public void sendMyPublicKey() throws IOException {
        String pk = myN.toString() + "|" + myE.toString();
        out.write(pk.length());
        out.newLine();
        out.flush();
    }

    // recebe a chave pública remota (bloco blocking read)
    public void receiveRemotePublicKey() throws IOException {
        String line = in.readLine();
        if (line == null) throw new EOFException("Conexão fechada antes de receber public key");
        String[] parts = line.split("\\|");
        if (parts.length != 2) throw new IOException("Formato de chave inválido");
        remoteN = new BigInteger(parts[0]);
        remoteE = new BigInteger(parts[1]);
    }

    // trocar chaves (me envia e recebe)
    public void exchangeKeys(boolean iSendFirst) throws IOException {
        if (iSendFirst) {
            sendMyPublicKey();
            receiveRemotePublicKey();
        } else {
            receiveRemotePublicKey();
            sendMyPublicKey();
        }
    }

    // envia mensagem (texto). Ela será cifrada com a chave pública remota.
    public void sendEncryptedMessage(String plain) throws IOException {
        BigInteger cipher = RSA.encryptString(plain, remoteE, remoteN);
        String s = cipher.toString();
        out.write(s);
        out.newLine();
        out.flush();
    }

    // recebe e decripta uma linha (bloqueante)
    public String receiveDecryptedMessage() throws IOException {
        String line = in.readLine();
        if (line == null) return null;
        BigInteger cipher = new BigInteger(line.trim());
        String plain = RSA.decryptToString(cipher, myD, myN);
        return plain;
    }

    public void close() {
        try { socket.close(); } catch (IOException ignored) {}
    }

    // getters (opcional)
    public BigInteger getRemoteN() { return remoteN; }
    public BigInteger getRemoteE() { return remoteE; }
}