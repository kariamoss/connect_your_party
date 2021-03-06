package connectYourParty.businessObjects.service;

import connectYourParty.exception.WrongClassBinaryexception;
import connectYourParty.modulesLogic.service.ByteClassLoader;
import connectYourParty.services.IService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ServiceHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    @NotNull
    private Module module;

    @NotNull
    private Class classBin;

    public ServiceHolder(){

    }

    public ServiceHolder(Module module, Class classBin) {
        this.module = module;
        this.classBin = classBin;
    }

    public ServiceHolder(Module module, byte[] classBin) throws WrongClassBinaryexception{
        this.module = module;
        try {
            ByteClassLoader byteLoader = new ByteClassLoader(this.getClass().getClassLoader());
            this.classBin = byteLoader.getClassFromByte(classBin);
        } catch (ClassFormatError e){
            throw new WrongClassBinaryexception();
        }

    }

    public IService getService() throws Exception{
        IService service;


        service = (IService) this.classBin.newInstance();

        return service;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Module getModule() {
        return module;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ServiceHolder &&
                this.id == ((ServiceHolder)obj).id;
    }
}
