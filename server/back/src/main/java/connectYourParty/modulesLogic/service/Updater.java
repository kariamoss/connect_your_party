package connectYourParty.modulesLogic.service;

import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;

import java.util.*;

public class Updater {
    private Map<Module,Collection<Subscriber>> subs;

    public Updater(){
        this.initMap();
    }

    private void initMap(){
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
        sub.onUnsubscribe();
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

    public void unSubscribeAll(){
        Collection<Collection<Subscriber>> allSubs = this.subs.values();

        for(Collection<Subscriber> i : allSubs){
            for(Subscriber sub : i){
                sub.onUnsubscribe();
            }
        }

        this.initMap();
    }
}
