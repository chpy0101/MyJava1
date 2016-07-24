package ccc.Utils;

/**
 * Created by chpy on 16/7/24.
 */
public final class StringUtils {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
}
