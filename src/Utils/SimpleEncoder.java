package Utils;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SimpleEncoder {
    MessageDigest md;
    public static SimpleEncoder instance = null;
    public SimpleEncoder() {
        try{
            md = MessageDigest.getInstance("SHA-256");
        }catch(Exception any){
            System.out.println(any.getMessage());
        }
    }

    public static SimpleEncoder getInstance(){
        if(instance == null)
            instance = new SimpleEncoder();

        return instance;
    }
    public byte[] Encode(String string){
        return md.digest(string.getBytes(StandardCharsets.UTF_8));
    }


}
