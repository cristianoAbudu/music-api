package com.cristianoalmeida.musicapi.controller;

import com.cristianoalmeida.musicapi.record.BandRecord;
import com.cristianoalmeida.musicapi.record.BandWithAlbumsRecord;
import com.cristianoalmeida.musicapi.service.BandService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/bands")
public class BandController {

    private BandService bandService;

    public BandController(BandService bandService){
        this.bandService = bandService;
    }

    @GetMapping
    public Collection<BandRecord> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "name") String sort) {

        return bandService.find(name, sort);
    }

    @GetMapping("/{id}")
    public BandWithAlbumsRecord get(
            @PathVariable String id
    ) {
        return bandService.findbyId(id);
    }

}
