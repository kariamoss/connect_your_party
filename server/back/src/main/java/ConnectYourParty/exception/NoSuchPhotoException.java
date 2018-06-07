package ConnectYourParty.exception;

public class NoSuchPhotoException extends Exception {
    public NoSuchPhotoException(){}

    public NoSuchPhotoException(String message)
    {
        super(message);
    }
}
