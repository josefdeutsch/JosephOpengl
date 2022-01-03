package Builder;

import Interface.ITemp;

/**
 * Created by philippPC on 16.07.2016.
 */
public class Temp implements ITemp {

    private int vaoID;
    private int vertexCount;
    private int numOfInstances;


    public Temp(int vertexCount, int vaoID, int numOfInstances) {

        this.vertexCount = vertexCount;

        this.vaoID = vaoID;

        this.numOfInstances = numOfInstances;

    }

    public int getVertexCOUNT() {
        return vertexCount;
    }

    public int getVAOID() {
        return vaoID;
    }

    public int getTexID() {
        return 0;
    }

    public int getNumofInstances() {
        return numOfInstances;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
