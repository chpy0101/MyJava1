package ccc.Utils;


/**
 * Created by chpy on 16/7/24.
 */
public final class CastUtils {

    public static String castString(Object object) {
        return castString(object, "");
    }

    public static String castString(Object object, String defaultVaue) {
        return object != null ? String.valueOf(object) : defaultVaue;
    }

    public static int castInt(Object object) {
        return castInt(object, 0);

    }

    public static int castInt(Object object, int defaultValue) {
        int value = defaultValue;
        if (object != null) {
            String str = castString(object);
            if (StringUtils.isNotEmpty(str)) {
                try {
                    value = Integer.parseInt(str);
                } catch (NumberFormatException ex) {
                }
            }

        }
        return value;
    }
}
