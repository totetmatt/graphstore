package org.gephi.graph.store;

import org.gephi.graph.api.Node;

/**
 *
 * @author mbastian
 */
public class NodeImpl extends ElementImpl implements Node {

    protected int storeId = NodeStore.NULL_ID;
    protected EdgeImpl[] headOut = new EdgeImpl[EdgeStore.DEFAULT_TYPE_COUNT];
    protected EdgeImpl[] headIn = new EdgeImpl[EdgeStore.DEFAULT_TYPE_COUNT];
    //Degree
    protected int inDegree;
    protected int outDegree;
    protected int mutualDegree;

    public NodeImpl(Object id) {
        super(id);
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int id) {
        this.storeId = id;
    }

    public int getDegree() {
        return inDegree + outDegree;
    }

    public int getInDegree() {
        return inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public int getUndirectedDegree() {
        return inDegree + outDegree - mutualDegree;
    }

    @Override
    PropertyStore getPropertyStore() {
        return graphStore.nodePropertyStore;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodeImpl other = (NodeImpl) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
