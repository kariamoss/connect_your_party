package connectYourParty.exceptions.photo;

public class RetrievePhotoErrorException extends Exception{
    public RetrievePhotoErrorException() {}

    public RetrievePhotoErrorException(String message)
    {
        super(message);
    }
}
