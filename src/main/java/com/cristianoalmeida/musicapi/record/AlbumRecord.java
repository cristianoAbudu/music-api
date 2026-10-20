package com.cristianoalmeida.musicapi.record;

import java.time.Instant;
import java.util.List;

public record AlbumRecord(
        String name,
        String id,
        Instant releasedDate,
        String image,
        BandRecord band,
        List<MusicRecord> tracks
) {
}