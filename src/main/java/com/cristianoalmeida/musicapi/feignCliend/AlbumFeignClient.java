package com.cristianoalmeida.musicapi.feignCliend;

import com.cristianoalmeida.musicapi.record.AlbumRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "album-service",
        url = "https://bands-api.vercel.app"
)
public interface AlbumFeignClient {

    @GetMapping("/api/albums")
    List<AlbumRecord> findAll();
}