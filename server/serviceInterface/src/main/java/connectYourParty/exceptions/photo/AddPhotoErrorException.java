package connectYourParty.exceptions.photo;

/**
 * Throw when an error occurs while adding a photo
 */
public class AddPhotoErrorException extends Exception {
    public AddPhotoErrorException() {}

    public AddPhotoErrorException(String message)
    {
        super(message);
    }
}
