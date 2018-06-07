package ConnectYourParty.modulesLogic.service;

import ConnectYourParty.businessObjects.service.Module;
import ConnectYourParty.businessObjects.service.ServiceHolder;

import java.util.*;

public class Updater {
    private Map<Module,Collection<Subscriber>> subs;

    public Updater(){
        subs = new HashMap<>();

        for(Module mod : Module.values()){
            subs.put(mod,new HashSet<>());
        }
    }

    public void subscribe(Subscriber sub, Module module){
        subs.get(module).add(sub);
    }

    public void unsubscribe(Subscriber sub, Module module){
        subs.get(module).remove(sub);
        sub.onUnsuscribe();
    }

    public void add(ServiceHolder holder, Module module){
        for(Subscriber sub : subs.get(module)){
            sub.onAdd(holder);
        }
    }

    public void remove(ServiceHolder holder, Module module){
        for(Subscriber sub : subs.get(module)){
            sub.onRemove(holder);
        }
    }
}
