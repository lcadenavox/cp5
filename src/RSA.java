import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA {
    // encripta bytes com a chave pública (E, N)
    public static BigInteger encryptBytes(byte[] messageBytes, BigInteger e, BigInteger n) {
        BigInteger m = new BigInteger(1, messageBytes);
        if (m.compareTo(n) >= 0) {
            throw new IllegalArgumentException("Mensagem muito grande para este N. Use blocos menores.");
        }
        return m.modPow(e, n);
    }

    // decripta BigInteger com a chave privada (D, N)
    public static byte[] decryptToBytes(BigInteger cipher, BigInteger d, BigInteger n) {
        BigInteger m = cipher.modPow(d, n);
        byte[] arr = m.toByteArray();
        // se o primeiro byte for zero (sinal), remover
        if (arr.length > 1 && arr[0] == 0) {
            byte[] tmp = new byte[arr.length - 1];
            System.arraycopy(arr, 1, tmp, 0, tmp.length);
            return tmp;
        }
        return arr;
    }

    public static BigInteger encryptString(String text, BigInteger e, BigInteger n) {
        return encryptBytes(text.getBytes(StandardCharsets.UTF_8), e, n);
    }

    public static String decryptToString(BigInteger cipher, BigInteger d, BigInteger n) {
        byte[] bytes = decryptToBytes(cipher, d, n);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}