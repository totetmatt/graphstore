/*
 * Copyright 2012-2013 Gephi Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gephi.graph.api.types;

import org.gephi.graph.api.Estimator;

/**
 * Sorted map where keys are intervals and values string values.
 */
public final class IntervalStringMap extends IntervalMap<String> {

    private String[] values;

    /**
     * Default constructor.
     * <p>
     * The map is empty with zero capacity.
     */
    public IntervalStringMap() {
        super();
        values = new String[0];
    }

    /**
     * Constructor with capacity.
     * <p>
     * Using this constructor can improve performances if the number of timestamps
     * is known in advance as it minimizes array resizes.
     *
     * @param capacity timestamp capacity
     */
    public IntervalStringMap(int capacity) {
        super(capacity);
        values = new String[capacity];
    }

    /**
     * Constructor with an initial interval map.
     * <p>
     * The <code>keys</code> array must be in the same format returned by
     * {@link #getIntervals() }.
     *
     * @param keys initial keys content
     * @param vals initial values content
     */
    public IntervalStringMap(double[] keys, String[] vals) {
        super(keys);
        values = new String[vals.length];
        System.arraycopy(vals, 0, values, 0, vals.length);
    }

    @Override
    public Class<String> getTypeClass() {
        return String.class;
    }

    @Override
    public boolean isSupported(Estimator estimator) {
        return estimator.is(Estimator.FIRST, Estimator.LAST);
    }

    @Override
    protected String getValue(int index) {
        return values[index];
    }

    @Override
    protected Object getValuesArray() {
        return values;
    }

    @Override
    protected void setValuesArray(Object array) {
        values = (String[]) array;
    }
}
