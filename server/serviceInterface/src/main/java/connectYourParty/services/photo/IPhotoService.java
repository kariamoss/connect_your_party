package connectYourParty.services.photo;

import connectYourParty.exceptions.photo.AddPhotoErrorException;
import connectYourParty.exceptions.photo.CannotDeletePhotoException;
import connectYourParty.exceptions.photo.RetrievePhotoErrorException;
import connectYourParty.objects.TokenService;
import connectYourParty.services.IService;

import java.util.Optional;

/**
 * Implements this interface to develop a service for the photo module
 */
public interface IPhotoService extends IService {
    /**
     * Add a given photo
     * @param photo The photo
     * @param path The path where to stock the photo (ie ConnectYourParty/Event/photo.png)
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @throws AddPhotoErrorException
     */
    void addPhoto(byte[] photo, String path, Optional<TokenService> token) throws AddPhotoErrorException;

    /**
     * Retrieve a photo by its path
     * @param path The path where the photo was stocked (ie ConnectYourParty/Event/photo.png)
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @return The photo
     * @throws RetrievePhotoErrorException
     */
    byte[] getPhoto(String path, Optional<TokenService> token) throws RetrievePhotoErrorException;

    /**
     * Remove a photo by its path
     * @param path The path where the photo was stocked (ie ConnectYourParty/Event/photo.png)
     * @param token If the service is using oAuth2.0 (by implementing {@link connectYourParty.services.IServiceOAuth}),
     *              the token will contain information about authentication
     * @throws CannotDeletePhotoException
     */
    void removePhoto(String path, Optional<TokenService> token) throws CannotDeletePhotoException;
}
