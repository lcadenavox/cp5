import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyPair {
    public final BigInteger p;
    public final BigInteger q;
    public final BigInteger n;
    public final BigInteger phi;
    public final BigInteger e;
    public final BigInteger d;

    // Construtor fixo com p = 857 e q = 859 (primos)
    public RSAKeyPair() {
        this.p = BigInteger.valueOf(857);
        this.q = BigInteger.valueOf(859);
        this.n = p.multiply(q); // modulus
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        // usar e = 65537 se for coprimo com phi; se não, procurar outro e pequeno
        BigInteger candidateE = BigInteger.valueOf(65537);
        if (!candidateE.gcd(phi).equals(BigInteger.ONE)) {
            // fallback: buscar um e pequeno ímpar
            candidateE = BigInteger.valueOf(3);
            while (!candidateE.gcd(phi).equals(BigInteger.ONE)) {
                candidateE = candidateE.add(BigInteger.TWO);
            }
        }
        this.e = candidateE;
        this.d = this.e.modInverse(phi);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("p = ").append(p).append("\n");
        sb.append("q = ").append(q).append("\n");
        sb.append("n = ").append(n).append("\n");
        sb.append("phi = ").append(phi).append("\n");
        sb.append("e = ").append(e).append("\n");
        sb.append("d = ").append(d).append("\n");
        return sb.toString();
    }
}