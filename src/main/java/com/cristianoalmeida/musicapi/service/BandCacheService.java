package com.cristianoalmeida.musicapi.service;

import com.cristianoalmeida.musicapi.record.BandRecord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BandCacheService {
    private Map<String, BandRecord> bandCache = null;
    private Date cacheExpiration;

    public boolean isExpired() {
        return (bandCache == null || cacheExpiration.compareTo(new Date())<0);
    }

    public void refresh(List<BandRecord> all) {
        bandCache = new HashMap<>();
        for(BandRecord bandRecord : all){
            bandCache.put(bandRecord.id(), bandRecord);
        }
        // cache expires after 120 seconds
        cacheExpiration = new Date(System.currentTimeMillis() + 120_000);
    }

    public Collection<BandRecord> findAll() {
        return bandCache.values();
    }

    public BandRecord findById(String id) {
        return bandCache.get(id);
    }
}
