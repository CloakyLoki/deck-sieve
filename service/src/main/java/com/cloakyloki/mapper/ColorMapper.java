package com.cloakyloki.mapper;

import com.cloakyloki.dto.ManacostDto;
import com.cloakyloki.entity.enumerated.ColorIndicator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ColorMapper {

    private final String COLORED_MANA_REGEX = "\\{([WUBRG])\\}";
    private final String COMMON_MANA_REGEX = "\\{(\\d+)\\}";

    public ManacostDto map(String manacost) {
        List<ColorIndicator> colors = new ArrayList<>();
        String commonMana = "";
        Pattern patternColored = Pattern.compile(COLORED_MANA_REGEX);
        Pattern patternCommon = Pattern.compile(COMMON_MANA_REGEX);
        Matcher matcherColored = patternColored.matcher(manacost);
        Matcher matcherCommon = patternCommon.matcher(manacost);

        while (matcherColored.find()) {
            String colorStr = matcherColored.group(1);
            ColorIndicator color = ColorIndicator.valueOf(colorStr);
            colors.add(color);
        }
        while (matcherCommon.find()) {
            commonMana = matcherCommon.group(1);
        }

        return new ManacostDto(commonMana, colors);
    }
}
