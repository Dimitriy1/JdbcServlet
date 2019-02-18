package jdbc;

public class MyException extends RuntimeException {
    public MyException(Exception e, String message) {
        System.out.println(message + " " + e.getMessage());
    }
}
