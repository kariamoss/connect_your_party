package ConnectYourParty.exception;

public class PhotoAlreadyExistException extends Exception {
    public PhotoAlreadyExistException() {}

    public PhotoAlreadyExistException(String message)
    {
        super(message);
    }
}
