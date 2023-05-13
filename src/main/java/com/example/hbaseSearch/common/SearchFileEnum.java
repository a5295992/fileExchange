package com.example.hbaseSearch.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum SearchFileEnum {
    PIC(Arrays.asList("BMP", "DIB", "PCP", "DIF", "WMF", "GIF", "JPG", "TIF", "EPS", "PSD", "CDR", "IFF", "TGA", "PCD", "MPT", "PNG")),
    VIDEO(Arrays.asList("MPEG", "AVI", "nAVI", "ASF", "MOV", "3GP", "WMV", "DivX", "XviD", "RM", "RMVB", "FLV", "F4V")),
    FILE(Collections.emptyList()),
    ALL(Collections.emptyList());
    private List<String> types;

    public static boolean isImg(String name) {
        for (String type : PIC.getTypes()) {
            if (name.toUpperCase(Locale.ROOT).endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVideo(String name) {
        for (String type : VIDEO.getTypes()) {
            if (name.toUpperCase(Locale.ROOT).endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotVideoAndImg(String name) {
        if (!isImg(name) && !isVideo(name)) {
            return true;
        }
        return false;
    }

    public List<String> getTypes() {
        return types;
    }

    SearchFileEnum(List<String> types) {
        this.types = types;
    }

}
