package ConnectYourParty.exceptions.photo;

public class CannotDeletePhotoException extends Exception{
    public CannotDeletePhotoException() {}

    public CannotDeletePhotoException(String message)
    {
        super(message);
    }
}
