/*
 * Copyright (c) 2018 Nicolas Maltais
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.maltaisn.icondialog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class Label implements Comparable<Object> {

    private static final String TAG = Label.class.getSimpleName();

    final String name;
    @Nullable LabelValue value;
    @Nullable List<LabelValue> aliases;

    @Nullable Label overwritten;

    /**
     * Create new label
     * @param name name of the label
     * @param value label value. Can be null if there are aliases
     *              or if label is a group label
     * @param aliases alias values. Can be null if there are no aliases
     *                or if label is a group label
     */
    Label(String name, @Nullable LabelValue value, @Nullable List<LabelValue> aliases) {
        this.name = name;
        this.value = value;
        this.aliases = aliases;
    }

    /**
     * Overwrite label. Overwritten values can still be found with {@link Label#overwritten}
     * @param value new value
     * @param aliases new aliases
     */
    void overwrite(@Nullable LabelValue value, @Nullable List<LabelValue> aliases) {
        overwritten = new Label(name, this.value, this.aliases);
        this.value = value;
        this.aliases = aliases;
    }

    /**
     * @return the label name, not localized
     */
    public String getName() {
        return name;
    }

    /**
     * Get label localized value
     * @return null if label has no value (group label or label has aliases)
     *         otherwise return the value string
     */
    public @Nullable LabelValue getValue() {
        return value;
    }

    /**
     * Get the localized alias values for this label
     * @return null if label has no aliases
     *         string array of aliases otherwise
     */
    public @Nullable LabelValue[] getAliases() {
        return aliases == null ? null : aliases.toArray(new LabelValue[aliases.size()]);
    }

    /**
     * @return If extra icons are defined and this label has been overwritten, get a label object
     * containing the data of the overwritten label. Otherwise null.
     */
    public @Nullable Label getOverwrittenLabel() {
        return overwritten;
    }

    @Override
    public int compareTo(@NonNull Object label) {
        String name = (label instanceof String ? (String) label : ((Label) label).name);
        return this.name.compareTo(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[name: \"");
        sb.append(name);
        sb.append("\"");
        if (aliases != null) {
            sb.append(", aliases: [");
            if (aliases.size() > 0) {
                sb.append("\"");
                for (LabelValue alias : aliases) {
                    sb.append(alias.getValue());
                    sb.append("\", \"");
                }
                sb.delete(sb.length() - 3, sb.length());
            }
            sb.append("]");
        } else if (value != null) {
            sb.append(", value: \"");
            sb.append(value.value);
            sb.append("\"");
        }
        if (overwritten != null) {
            sb.append(", overwritten");
        }
        sb.append("]");
        return sb.toString();
    }

}
