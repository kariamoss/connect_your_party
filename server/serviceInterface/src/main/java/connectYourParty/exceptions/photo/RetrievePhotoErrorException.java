package connectYourParty.exceptions.photo;

/**
 * Throw when an error occurs while retrieving a photo
 */
public class RetrievePhotoErrorException extends Exception{
    public RetrievePhotoErrorException() {}

    public RetrievePhotoErrorException(String message)
    {
        super(message);
    }
}
