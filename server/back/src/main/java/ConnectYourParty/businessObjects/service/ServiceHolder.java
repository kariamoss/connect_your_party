package ConnectYourParty.businessObjects.service;

import ConnectYourParty.modulesLogic.service.ByteClassLoader;
import ConnectYourParty.services.IService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ServiceHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated
    @NotNull
    private Module module;

    @NotNull
    private byte[] classBin;

    public ServiceHolder(Module module, byte[] classBin) {
        this.module = module;
        this.classBin = classBin;
    }

    public IService getService() throws Exception{
        IService service;

        ByteClassLoader byteLoader = new ByteClassLoader(this.getClass().getClassLoader());

        service = (IService) byteLoader.getClassFromByte(this.classBin).newInstance();

        return service;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ServiceHolder &&
                this.id == ((ServiceHolder)obj).id;
    }
}
