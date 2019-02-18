package jdbc.encoder;

public class PasswordEncoder {

    public static String encrypt(String text, String keyWord) {
        byte[] arr = text.getBytes();
        byte[] keyarr = keyWord.getBytes();
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            result += (byte) (arr[i] ^ keyarr[i % keyarr.length]);
        }
        return result;
    }
}
