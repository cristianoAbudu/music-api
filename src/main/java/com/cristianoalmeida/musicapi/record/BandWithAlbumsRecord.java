package com.cristianoalmeida.musicapi.record;

import java.util.List;

public record BandWithAlbumsRecord(
        String name,
        String image,
        String genre,
        String biography,
        long numPlays,
        String id,
        List<AlbumRecord> albums
) {
}