package com.util.common.stringutil;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.stream.IntStream;

/**
 *
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char UNDERLINE = '_';

    /**
     * 将带有下划线的字符串转换成驼峰字符串
     * download_count => downloadCount
     *
     * @param str 需要转换的字符串
     * @return 变成驼峰形式字符串DateUtil.java
     */
    public static String underlineToCamel(String str) {
        if (isEmpty(str)) {
            return EMPTY;
        }
        String temp = str.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将驼峰形式字符串转换成下划线形式
     * downloadCount => download_count
     *
     * @param str 需要转换的字符串
     * @return 变成下划线形式字符串
     */
    public static String camelToUnderline(String str) {
        if (isEmpty(str)) {
            return EMPTY;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 取汉语拼音的首字母
     *
     * @param chinese 汉字
     * @return 首字母
     */
    public static String getPinyinInitial(String chinese) {
        String pinyin = chineseToPinyin(chinese, true);
        return isBlank(pinyin) ? EMPTY : pinyin.substring(0, 1).toUpperCase();
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chinese       汉字
     * @param firstCharOnly 只取首字母
     * @return 拼音
     */
    public static String chineseToPinyin(String chinese, boolean firstCharOnly) {
        if (StringUtils.isBlank(chinese)) {
            return StringUtils.EMPTY;
        }

        char[] chars = chinese.toCharArray();
        HanyuPinyinOutputFormat formatter = new HanyuPinyinOutputFormat();
        formatter.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        formatter.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder pinyinBuilder = new StringBuilder();
        IntStream.range(0, chars.length).forEach(idx -> {
            if (chars[idx] > 128) {
                try {
                    String pinyin = PinyinHelper.toHanyuPinyinStringArray(chars[idx], formatter)[0];
                    pinyinBuilder.append(firstCharOnly ? pinyin.charAt(0) : pinyin);
                } catch (Exception ex) {
                    log.warn(ex.getMessage());
                }
            } else {
                pinyinBuilder.append(chars[idx]);
            }
        });
        return pinyinBuilder.toString();
    }

    /**
     * 每两个字符互换位置
     *
     * @param input 需要处理的字符串
     * @return 互换位置后的字符串
     */
    public static String swapChar(String input) {
        if (input == null) {
            return null;
        }
        char[] original = input.toCharArray();
        char[] changed = new char[original.length];
        for (int i = 0; i < original.length / 2; i++) {
            int start = i * 2;
            int next = i * 2 + 1;
            changed[start] = original[next];
            changed[next] = original[start];
        }
        if (original.length % 2 == 1) {
            changed[original.length - 1] = original[original.length - 1];
        }
        return new String(changed);
    }
}
