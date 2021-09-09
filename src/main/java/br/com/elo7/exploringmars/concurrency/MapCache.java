package br.com.elo7.exploringmars.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.elo7.exploringmars.db.MapRepository;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.exception.NotFoundException;

@Component
public class MapCache {
    
    private Map<Long, MapCacheItem> hashMap;

    @Autowired
    private MapRepository mapRepository;

    public MapCache() {
        hashMap = new HashMap<Long, MapCacheItem>();
    }

    /**
     * Gets the map with the given id, returning either:
     * <ul>
     * <li>An already cached instance of the map, incrementing the number of active references 
     * held for the instance</li>
     * <li>A new map instance that gets cached having one active reference</li>
     * </ul>
     * <strong>Every reference gotten from this method must be released by calling the 
     * {@link #releaseMapReference(long) releaseReference} method.</strong>
     * 
     * @param id
     * @return A cached MapEntity instance
     * @throws NotFoundException
     */
    public synchronized MapEntity getMapReference(long id) throws NotFoundException {
        if (hashMap.containsKey(id)) {
            MapCacheItem cache = hashMap.get(id);
            cache.incrementCounter();
            return cache.getMap();
        }

        Optional<MapEntity> optMap = mapRepository.findById(id);
        if (optMap.isPresent()) {
            MapEntity map = optMap.get();
            MapCacheItem cache = new MapCacheItem(map);
            hashMap.put(id, cache);
            return map;
        }

        throw new NotFoundException("Map not found");
    }

    /**
     * Releases a reference from the given cached map.
     * The map is removed from the cache if the counter of active references is zero.
     * 
     * @param id
     */
    public synchronized void releaseMapReference(long id) {
        if (hashMap.containsKey(id)) {
            MapCacheItem cache = hashMap.get(id);
            cache.decrementCounter();
            if (cache.isCounterZero()) {
                hashMap.remove(id);
            }
        }
    }

    /**
     * Removes the map from the cache, if exists.
     * 
     * @param id
     */
    public synchronized void removeMap(long id) {
        if (hashMap.containsKey(id)) {
            hashMap.remove(id);
        }
    }

}
