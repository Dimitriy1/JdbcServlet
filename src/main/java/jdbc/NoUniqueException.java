package jdbc;

public class NoUniqueException extends RuntimeException {
    public NoUniqueException(String message) {
        System.out.println(message);
    }
}
