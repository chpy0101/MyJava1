package ccc.Utils;

import org.apache.commons.collections4.*;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Created by Administrator on 2016/7/27.
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

}
