package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.ColorIndicator;
import lombok.Value;

import java.util.List;

@Value
public class ManacostDto {

    String common;
    List<ColorIndicator> colorList;
}
