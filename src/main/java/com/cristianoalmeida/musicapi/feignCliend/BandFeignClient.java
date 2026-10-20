package com.cristianoalmeida.musicapi.feignCliend;

import com.cristianoalmeida.musicapi.record.BandRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "band-service",
        url = "https://bands-api.vercel.app"
)
public interface BandFeignClient {

    @GetMapping("/api/bands")
    List<BandRecord> findAll();
}