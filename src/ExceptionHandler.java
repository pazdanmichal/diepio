import java.io.IOException;

public class ExceptionHandler extends Exception{

    public void handleIOException(IOException e) {
        System.err.println("IOException handled: " + e.getMessage());
        // Dodatkowe logika obsługi IOException
    }

    public void handleNumberFormatException(NumberFormatException e) {
        System.err.println("NumberFormatException handled: " + e.getMessage());
        // Dodatkowe logika obsługi NumberFormatException
    }

}