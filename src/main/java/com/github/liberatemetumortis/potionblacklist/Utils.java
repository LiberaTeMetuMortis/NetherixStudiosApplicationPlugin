package com.github.liberatemetumortis.potionblacklist;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String translateColors(String str) {
        if (str == null) return "";
        String parsedStr = str.replaceAll("\\{(#[0-9A-f]{6})\\}", "&$1");
        Pattern pattern = Pattern.compile("&(#[0-9A-f]{6})");
        Matcher matcher = pattern.matcher(parsedStr);
        while (matcher.find()) {
            parsedStr = parsedStr.replaceFirst(Pattern.quote(matcher.group()), ChatColor.of(matcher.group().substring(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', parsedStr);
    }
}
