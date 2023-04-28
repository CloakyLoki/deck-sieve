package com.cloakyloki.mapper;

import com.cloakyloki.dto.CSetReadDto;
import com.cloakyloki.entity.CSet;

public class CSetReadMapper implements Mapper<CSet, CSetReadDto> {
    @Override
    public CSetReadDto map(CSet object) {
        return new CSetReadDto(
                object.getCode(),
                object.getBaseSetSize(),
                object.getTotalSetSize(),
                object.getName(),
                object.getLanguages(),
                object.getReleaseDate(),
                object.getType(),
                object.getIsFoilOnly() == 1,
                object.getIsOnlineOnly() == 1,
                object.getBlock()
        );
    }
}
