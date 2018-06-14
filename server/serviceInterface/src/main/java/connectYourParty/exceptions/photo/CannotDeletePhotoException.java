package connectYourParty.exceptions.photo;

/**
 * Throw when an error occurs while deleting a photo
 */
public class CannotDeletePhotoException extends Exception{
    public CannotDeletePhotoException() {}

    public CannotDeletePhotoException(String message)
    {
        super(message);
    }
}
