package fr.lernejo.navy_battle.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public Map<String,String> decodeParams(String params) {
        return
            Arrays.stream(params.split("&"))
            .collect(Collectors.toMap(
                param -> param.split("=")[0],
                param -> param.split("=")[1]));
    }
}
