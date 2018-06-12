package connectYourParty.exceptions.music;

/**
 * Throw when a problem occurs in the creation of a playlist
 */
public class CannotCreatePlaylistException extends Exception{
    public CannotCreatePlaylistException(){}

    public CannotCreatePlaylistException(String message){super(message);}
}
