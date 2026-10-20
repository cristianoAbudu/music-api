package com.cristianoalmeida.musicapi.service;

import com.cristianoalmeida.musicapi.record.AlbumRecord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlbumCacheService {
    private Map<String, AlbumRecord> albumCache = null;
    private Date cacheExpiration;

    public boolean isExpired() {
        return (albumCache == null || cacheExpiration.compareTo(new Date())<0);
    }

    public void refresh(List<AlbumRecord> all) {
        albumCache = new HashMap<>();
        for(AlbumRecord albumRecord : all){
            albumCache.put(albumRecord.id(), albumRecord);
        }
        // cache expires after 120 seconds
        cacheExpiration = new Date(System.currentTimeMillis() + 120_000);
    }

    public Collection<AlbumRecord> findAll() {
        return albumCache.values();
    }

    public AlbumRecord findById(String id) {
        return albumCache.get(id);
    }
}
