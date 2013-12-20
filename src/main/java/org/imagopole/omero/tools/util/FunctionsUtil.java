/**
 *
 */
package org.imagopole.omero.tools.util;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import pojos.DatasetData;
import pojos.FileAnnotationData;
import pojos.TagAnnotationData;

/**
 * @author seb
 *
 */
public final class FunctionsUtil {

    /**
     * Private constructor.
     */
    private FunctionsUtil() {
        super();
    }

    public static final Function<String, Long> toLongOrNull = new Function<String, Long>() {

        @Override
        @Nullable
        public Long apply(@Nullable String input) {
            return ParseUtil.parseLongOrNull(input);
        }

    };

    public static final Function<DatasetData, String> toDatasetName =
                    new Function<DatasetData, String>() {

        @Override
        @Nullable
        public String apply(@Nullable DatasetData input) {
            String result = null;

            if (null != input) {
                result = input.getName();
            }

            return result;
        }

    };

    public static final Function<DatasetData, Long> toDatasetId =
                    new Function<DatasetData, Long>() {

        @Override
        @Nullable
        public Long apply(@Nullable DatasetData input) {
            Long result = null;

            if (null != input) {
                result = input.getId();
            }

            return result;
        }

    };

    public static final Function<TagAnnotationData, String> toTagValue =
                    new Function<TagAnnotationData, String>() {

        @Override
        @Nullable
        public String apply(@Nullable TagAnnotationData input) {
            String result = null;

            if (null != input) {
                result = input.getTagValue();
            }

            return result;
        }

    };

    public static final Function<FileAnnotationData, String> toAnnotationFileName =
                    new Function<FileAnnotationData, String>() {

        @Override
        @Nullable
        public String apply(@Nullable FileAnnotationData input) {
            String result = null;

            if (null != input) {
                result = input.getFileName();
            }

            return result;
        }

    };

    public static final Maps.EntryTransformer<String, String, List<String>> asTuplesMultimap =
                    new Maps.EntryTransformer<String, String, List<String>>() {

        @Override
        public List<String> transformEntry(@Nullable String key, @Nullable String value) {
            List<String> result = Collections.emptyList();

            if (!isNullOrEmpty(key) && !isNullOrEmpty(value)) {

                result = Lists.newArrayList(key, value);

            }

            return result;
        }

    };

}