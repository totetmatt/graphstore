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
package org.gephi.graph.impl;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.TimeRepresentation;

public class TimeStore {

    protected final GraphStore graphStore;
    // Lock (optional)
    protected final TableLockImpl lock;
    // Store
    protected TimeIndexStore nodeIndexStore;
    protected TimeIndexStore edgeIndexStore;

    public TimeStore(GraphStore store, boolean indexed) {
        this.graphStore = store;
        this.lock = GraphStoreConfiguration.ENABLE_AUTO_LOCKING ? new TableLockImpl() : null;

        TimeRepresentation timeRepresentation = GraphStoreConfiguration.DEFAULT_TIME_REPRESENTATION;
        if (store != null) {
            timeRepresentation = store.configuration.getTimeRepresentation();
        }
        if (timeRepresentation.equals(TimeRepresentation.INTERVAL)) {
            nodeIndexStore = new IntervalIndexStore<>(Node.class, lock, indexed);
            edgeIndexStore = new IntervalIndexStore<>(Edge.class, lock, indexed);
        } else {
            nodeIndexStore = new TimestampIndexStore<>(Node.class, lock, indexed);
            edgeIndexStore = new TimestampIndexStore<>(Edge.class, lock, indexed);
        }
    }

    protected void resetConfiguration() {
        if (graphStore != null) {
            if (graphStore.configuration.getTimeRepresentation().equals(TimeRepresentation.INTERVAL)) {
                nodeIndexStore = new IntervalIndexStore<>(Node.class, lock, nodeIndexStore.hasIndex());
                edgeIndexStore = new IntervalIndexStore<>(Edge.class, lock, edgeIndexStore.hasIndex());
            } else {
                nodeIndexStore = new TimestampIndexStore<>(Node.class, lock, nodeIndexStore.hasIndex());
                edgeIndexStore = new TimestampIndexStore<>(Edge.class, lock, edgeIndexStore.hasIndex());
            }
        }
    }

    public double getMin(Graph graph) {
        if (nodeIndexStore == null || edgeIndexStore == null) {
            // TODO: Manual calculation
            return Double.NEGATIVE_INFINITY;
        }
        double nodeMin = nodeIndexStore.getIndex(graph).getMinTimestamp();
        double edgeMin = edgeIndexStore.getIndex(graph).getMinTimestamp();
        if (Double.isInfinite(nodeMin)) {
            return edgeMin;
        }
        if (Double.isInfinite(edgeMin)) {
            return nodeMin;
        }
        return Math.min(nodeMin, edgeMin);
    }

    public double getMax(Graph graph) {
        if (nodeIndexStore == null || edgeIndexStore == null) {
            // TODO: Manual calculation
            return Double.POSITIVE_INFINITY;
        }
        double nodeMax = nodeIndexStore.getIndex(graph).getMaxTimestamp();
        double edgeMax = edgeIndexStore.getIndex(graph).getMaxTimestamp();
        if (Double.isInfinite(nodeMax)) {
            return edgeMax;
        }
        if (Double.isInfinite(edgeMax)) {
            return nodeMax;
        }
        return Math.max(nodeMax, edgeMax);
    }

    public boolean isEmpty() {
        return nodeIndexStore.size() == 0 && edgeIndexStore.size() == 0;
    }

    public void clear() {
        nodeIndexStore.clear();
        edgeIndexStore.clear();
    }

    public void clearEdges() {
        edgeIndexStore.clear();
    }

    public int deepHashCode() {
        int hash = 3;
        hash = 79 * hash + (this.nodeIndexStore != null ? this.nodeIndexStore.deepHashCode() : 0);
        hash = 79 * hash + (this.edgeIndexStore != null ? this.edgeIndexStore.deepHashCode() : 0);
        return hash;
    }

    public boolean deepEquals(TimeStore obj) {
        if (obj == null) {
            return false;
        }
        if (this.nodeIndexStore != obj.nodeIndexStore && (this.nodeIndexStore == null || !this.nodeIndexStore
                .deepEquals(obj.nodeIndexStore))) {
            return false;
        }
        if (this.edgeIndexStore != obj.edgeIndexStore && (this.edgeIndexStore == null || !this.edgeIndexStore
                .deepEquals(obj.edgeIndexStore))) {
            return false;
        }
        return true;
    }
}
