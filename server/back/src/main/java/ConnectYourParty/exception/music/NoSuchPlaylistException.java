package ConnectYourParty.exception.music;

public class NoSuchPlaylistException extends Exception {
    public NoSuchPlaylistException(){}

    public NoSuchPlaylistException(String message)
    {
        super(message);
    }
}
