package com.cristianoalmeida.musicapi.service;

import com.cristianoalmeida.musicapi.feignCliend.AlbumFeignClient;
import com.cristianoalmeida.musicapi.feignCliend.BandFeignClient;
import com.cristianoalmeida.musicapi.record.AlbumRecord;
import com.cristianoalmeida.musicapi.record.BandRecord;
import com.cristianoalmeida.musicapi.record.BandWithAlbumsRecord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BandService {
    private BandFeignClient bandFeignClient;
    private BandCacheService bandCacheService;

    private AlbumFeignClient albumFeignClient;
    private AlbumCacheService albumCacheService;

    public BandService(
            BandFeignClient bandFeignClient,
            BandCacheService bandCacheService,
            AlbumFeignClient albumFeignClient,
            AlbumCacheService albumCacheService
    )
    {
        this.bandFeignClient = bandFeignClient;
        this.bandCacheService = bandCacheService;
        this.albumFeignClient = albumFeignClient;
        this.albumCacheService = albumCacheService;
    }

    public Collection<BandRecord> find(String name, String sortBy) {
        updateBandCache();
        Collection<BandRecord> all = bandCacheService.findAll();

        List<BandRecord> filtered = all.stream()
                .filter(band -> name == null || name.isBlank()
                        || (band.name() != null
                        && band.name().toLowerCase()
                        .contains(name.trim().toLowerCase())))
                .toList();


        Comparator<BandRecord> comparator = switch (sortBy) {
            case "popularity" -> Comparator.comparingLong(BandRecord::numPlays);
            default -> Comparator.comparing(
                    BandRecord::name,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            );
        };

        if ("popularity".equalsIgnoreCase(sortBy)) {
            comparator = comparator.reversed();
        }

        List<BandRecord> sorted = filtered.stream()
                .sorted(comparator)
                .toList();

        return sorted;
    }

    private void updateBandCache() {
        if(bandCacheService.isExpired()) {
            bandCacheService.refresh(bandFeignClient.findAll());
        }
    }

    private void updateAlbumCache() {
        if(albumCacheService.isExpired()) {
            albumCacheService.refresh(albumFeignClient.findAll());
        }
    }

    public BandWithAlbumsRecord findbyId(String id) {
        updateBandCache();
        BandRecord bandRecord = bandCacheService.findById(id);
        List<AlbumRecord> albums = new ArrayList<>();
        updateAlbumCache();
        for(String albumId : bandRecord.albums()){
            albums.add(albumCacheService.findById(albumId));
        }

        BandWithAlbumsRecord bandWithAlbumsRecord = new BandWithAlbumsRecord(
            bandRecord.name(),
            bandRecord.image(),
            bandRecord.genre(),
            bandRecord.biography(),
            bandRecord.numPlays(),
            bandRecord.id(),
            albums
        );
        return bandWithAlbumsRecord;
    }
}
