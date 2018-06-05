package ConnectYourParty.database.photo;


import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exceptions.photo.AddPhotoErrorException;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PhotoDatabase implements IPhotoDatabase{

    @PersistenceContext private EntityManager entityManager;

    @Override
    public List<Photo> getPhotoList() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Photo> criteria = builder.createQuery(Photo.class);
        Root<Photo> root =  criteria.from(Photo.class);
        criteria.select(root);
        TypedQuery<Photo> query = entityManager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void addPhoto(Photo photo) throws AddPhotoErrorException {
        if(this.getPhotoList().contains(photo)){
            throw new AddPhotoErrorException();
        }
        this.entityManager.persist(photo);
        this.entityManager.flush();
    }

    @Override
    public void removePhoto(Photo photo){
        photo = entityManager.merge(photo);
        entityManager.remove(photo);
    }

    @Override
    public Photo getPhotoFromPath(String path) throws NoSuchPhotoException {
        Photo photo = this.entityManager.find(Photo.class,path);
        if(photo == null){
            throw new NoSuchPhotoException();
        }
        return photo;
    }

    @Override
    public void clean() {
        for(Photo photo : this.getPhotoList()){
            this.removePhoto(photo);
        }
    }


}
