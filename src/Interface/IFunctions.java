package Interface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by philippPC on 15.07.2016.
 */
public interface IFunctions {

    public int buildVertexArrayObject();

    public void bindVertexArrayObject(int vertexArrayObject);

    public void bindIndices(int vao, int[] data);

    public void bindtoLocationBindingIndex(int vao, int attrLocationIndex, int attrBindingIndex);

    public void bindtoVertexBufferObject(int vao, int vboid, int offset, int numOfValuesPerVertex);

    public void initAttributeBinding(int vao, int numOfValues, int attrLocationIndex);

    public void allocateInternalBufferStorage(int vboid, int capacity, int modus);
    public int buildVBO();

    public void toAttributeBinding(int vao, float[] data, int numOfValues) throws IOException ;

    public void mapBufferToAttribute(int vboid, int offset, int stride, int modus, float[] data);

    public void unbindVAO();

    public void updateAttributes(int vboid, float[] data);

    public void setAttributeDivisor(int attrLocationIndex, int rate);

    public void toShaderStorageBinding(int bufferindex)throws IOException;

    public int initShaderStorageBlock()throws IOException;

    public void mapBuffertoShaderStorageBlock(int uniformBufferIndex, int capacity, float[] data, long offset, long length) throws IOException;

    public void initArrayTexture(List<String> lis, int textureUnit);

    public void toTexturedQuad(int layerCount);

    public ByteBuffer getTexturedByteBuffer(ByteBuffer textureBuffer, String string);

    public void clearVertexData(int VBOID, ByteBuffer bb);

    public ITemp vaoProcedureImage();

    public ITemp vaoProcedurePlane();

    public ITemp vaoProcedureSculpture();

    public void initCubeTexture(List<String> lis, int modus, int clamping, int textureUnit);

    public void initSingleTexture(String fileName, int modus, int clamping, int textureUnit, int width, int heigth);

    public ByteBuffer getTextureBufferData(String fileName);

    public int buildVertexArrayTexture();

    public void activateTextureUnit(int textureUnit);

    public void bindTexture(int modus, int textureID);

    public void allocateInternalTextureBufferStorage(int modus, int level, int internalformat, int tempWith, int tempHeight);

    public void mapTexureBufferData(int modus, int level, int xoffset, int yoffset, int sizewidth, int sizeheight, int format, int type, ByteBuffer data);

    public void setPixelStoreInteger(int modus, int consecutive);

    public void setTextureParamater(int modus, int pname, int parameter);

    public void setDimensionTexture(int programId, int uniformlocationId, int sizewidth, int sizeheight);

    public int getTempWith();

    public int getTempHeight();
}


