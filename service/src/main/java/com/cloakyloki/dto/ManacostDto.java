package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.ColorIndicator;
import lombok.Value;

import java.util.List;

@Value
public class ManacostDto {

    Integer common;
    List<ColorIndicator> colorList;
}
