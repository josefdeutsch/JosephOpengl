package Builder;

import Interface.*;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static Helpers.UTIL.inBounds;
import static Helpers.UTIL.resetBufferOverFlowReminder;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_RECTANGLE;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL41.glProgramUniform2f;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL42.glTexStorage3D;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL44.GL_MAP_PERSISTENT_BIT;
import static org.lwjgl.opengl.GL45.*;


/**
 * Created by philippPC on 15.07.2016.
 */
public class Functions implements IFunctions {

    public IFramebuffers framebuffers;
    public IReference reference;
    public IObjloader objloader;

    private ByteBuffer temp;

    private int totalAmountofShader = 1; //??

    private static int ssboBindingIndex = 0;
    private static int attrBindingIndex = 0;
    private static int attrLocationIndex = 0;

    private float[] vertices,uv,normals;
    private int[] indices;

    public int tempWith = 0;
    public int tempHeight = 0;

    private int modus = GL_MAP_WRITE_BIT | GL_MAP_READ_BIT | GL_MAP_PERSISTENT_BIT;


    public Functions(IFramebuffers framebuffers, IReference reference, IObjloader objloader) {

        // Hint VAO attrb Bindings per VAO, texture sample Bindings are generic ! references ID saved internally 1-x.

        this.framebuffers = framebuffers;
        this.reference = reference;
        this.objloader = objloader;



    }

    public int buildVertexArrayObject() {

        int VAOID = glCreateVertexArrays();
        return VAOID;
    }

    public void bindVertexArrayObject(int vertexArrayObject) {
        glBindVertexArray(vertexArrayObject);
    }

    public void bindIndices(int vao, int[] data) {

        int IBO = glCreateBuffers();
        System.out.println(IBO);
        int capacity = 10485760*10;

        int range = data.length * Integer.BYTES;
        System.out.println(range);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);

        glNamedBufferStorage(IBO, capacity, modus);
        ByteBuffer bb = glMapNamedBufferRange(IBO, 0, data.length * Integer.BYTES, modus, null);

        IntBuffer intbuffer = bb.order(ByteOrder.nativeOrder()).asIntBuffer();
        intbuffer.put(data);
        intbuffer.flip();
        bb.clear();
        glUnmapNamedBuffer(IBO);

    }

    public void bindtoLocationBindingIndex(int vao, int attrLocationIndex, int attrBindingIndex) {

        glVertexArrayAttribBinding(vao, attrLocationIndex, attrBindingIndex);
    }

    public void bindtoVertexBufferObject(int vao, int vboid, int offset, int numOfValuesPerVertex) {

        glVertexArrayVertexBuffer(vao, attrBindingIndex, vboid, offset, numOfValuesPerVertex);
    }

    public void initAttributeBinding(int vao, int numOfValues, int attrLocationIndex) {

        glVertexArrayAttribFormat(vao, attrLocationIndex, numOfValues, GL_FLOAT, false, 0);

    }

    public void allocateInternalBufferStorage(int vboid, int capacity, int modus) {
        glNamedBufferStorage(vboid, capacity, modus);
    }

    public int buildVBO() {
        return glCreateBuffers();
    }

    public void toAttributeBinding(int vao, float[] data, int numOfValues) throws IOException {

        //System.out.println("attrBINDING :" + attrBindingIndex);
        //System.out.println("attrLOCATION :" + attrLocationIndex);
        resetBufferOverFlowReminder();

        int vboid = buildVBO();
        // System.out.println(vboid);
        // Möglichkeit zu Fixwerten:
        int capacity = 10485760;


        int size = numOfValues * Float.BYTES;
        int offset = 0;
        int range = data.length * Float.BYTES;

        System.out.println(range);

        bindtoVertexBufferObject(vao, vboid, offset, size);

        initAttributeBinding(vao, numOfValues, attrLocationIndex);
        allocateInternalBufferStorage(vboid, capacity, modus);

        if (!inBounds(data, capacity))
            throw new IOException("BufferOverFlowException out of memory; reconsider");

        mapBufferToAttribute(vboid, offset, range, modus, data);

        bindtoLocationBindingIndex(vao, attrLocationIndex, attrBindingIndex);

        attrLocationIndex += 1;
        attrBindingIndex += 1;

    }

    public void mapBufferToAttribute(int vboid, int offset, int stride, int modus, float[] data) {

        temp = glMapNamedBufferRange(vboid, offset, stride, modus, null);
        //temp = glMapNamedBuffer(vboid,GL_READ_ONLY | GL_WRITE_ONLY | GL_READ_WRITE,null);

        FloatBuffer floatbuffer = temp.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(data);
        floatbuffer.flip();
        temp.clear();
        glUnmapNamedBuffer(vboid);

    }

    public void unbindVAO() {
        glBindVertexArray(0);
    }

    public void updateAttributes(int vboid, float[] data) {

        int range = data.length * Float.BYTES;

        ByteBuffer temp = glMapNamedBufferRange(vboid, 0, range, modus, null);

        FloatBuffer floatbuffer = temp.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(data);
        floatbuffer.flip();
        temp.clear();

        glUnmapNamedBuffer(vboid);

    }

    public void setAttributeDivisor(int attrLocationIndex, int rate) {
        glVertexAttribDivisor(attrLocationIndex, rate);
    }

    public void toShaderStorageBinding(int bufferindex) throws IOException {

        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, ssboBindingIndex, bufferindex);
        ssboBindingIndex += 1;

    }

    public int initShaderStorageBlock() throws IOException {

        resetBufferOverFlowReminder();

        int buffer = glCreateBuffers();

        glBindBuffer(GL_SHADER_STORAGE_BUFFER, buffer);

        //Möglichkeit zu Fixwerten
        int capacity = 1024;

        glNamedBufferStorage(buffer, capacity, GL_MAP_WRITE_BIT | GL_MAP_READ_BIT | GL_MAP_PERSISTENT_BIT);

        mapBuffertoShaderStorageBlock(buffer, capacity, new float[]{0.2f, 0.4f, 0.5f, 0.8f, 0.7f, 0.1f}, 0, 32);
        mapBuffertoShaderStorageBlock(buffer, capacity, new float[]{0.2f, 0.4f, 0.5f, 0.8f, 0.7f, 0.1f}, 32, 64);

        glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);

        temp.clear();

        return buffer;

    }

    public void mapBuffertoShaderStorageBlock(int uniformBufferIndex, int capacity, float[] data, long offset, long length) throws IOException {

        glBindBuffer(GL_SHADER_STORAGE_BUFFER, uniformBufferIndex);

        if (!inBounds(data, capacity)) throw new IOException("BufferOverFlowException out of memory; reconsider");

        ByteBuffer byteBuffer = glMapNamedBufferRange(uniformBufferIndex, offset, length, GL_MAP_WRITE_BIT | GL_MAP_READ_BIT | GL_MAP_PERSISTENT_BIT, null);

        FloatBuffer floatbuffer = byteBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatbuffer.put(data);
        floatbuffer.flip();

        glUnmapNamedBuffer(uniformBufferIndex);
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
    }

    public void initArrayTexture(List<String> lis, int textureUnit) {

        int width = 256;
        int height = 256;
        int mipLevelCount = 0;
        int texture = glGenTextures();
        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_2D_ARRAY, texture);
        glTexStorage3D(GL_TEXTURE_2D_ARRAY, 1, GL_RGB8, width, height, 4);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        toTexturedQuad(4);


    }

    public void toTexturedQuad(int layerCount) {

        final int texturesize = 1048576;

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(texturesize).order(ByteOrder.nativeOrder());

        reference.readInPath();

        // im path sind 4 Bilder enthalten
        // die Bilder werden randomisiert einzelnt gemappt.
        // das darf in abfolgender Reihenfolge auf die 4 randomwerte von path zugreifen
        // es wird ein randomwert aus 4 benötigt. der immer wieder aufs neue generiert wird sobald er ausgelaufen ist
        // geniere array mit 4 Werten - > swap random - >
        // if 4 mal switch - > new swap

        for (int i = 0; i < layerCount; i++) {
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, i, 256, 256, 1, GL_RGB, GL_UNSIGNED_BYTE, getTexturedByteBuffer(textureBuffer, reference.getPath().get(i)));
        }
        reference.getPath().clear();


    }

    public ByteBuffer getTexturedByteBuffer(ByteBuffer textureBuffer, String string) {

        BufferedImage bufferedimage;
        try {

            bufferedimage = ImageIO.read(new File(string));
            int width = bufferedimage.getWidth();
            int height = bufferedimage.getHeight();


            int[] pixel_arr = bufferedimage.getRGB(0, 0, width, height, null, 0, width);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixel_arr[i * width + j];
                    textureBuffer.put((byte) ((pixel >> 16) & 0xFF));
                    textureBuffer.put((byte) ((pixel >> 8) & 0xFF));
                    textureBuffer.put((byte) ((pixel >> 0) & 0xFF));
                    // if RGBA then :
                    //textureBuffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return (ByteBuffer) textureBuffer.rewind();

    }

    public void clearVertexData(int VBOID, ByteBuffer bb) {

        glClearNamedBufferData(VBOID, GL_RGBA8UI, GL_RGBA, GL_BYTE, bb);

    }

    public ITemp vaoProcedureImage() {

        int vertexArrayObject = buildVertexArrayObject();
        bindVertexArrayObject(vertexArrayObject);
        System.out.println("DATA OF VAO"+vertexArrayObject);
        attrLocationIndex = 0;
        attrBindingIndex = 0;
        bindIndices(vertexArrayObject, objloader.getIndicesArray());
        for (int i = 0; i <objloader.getIndicesArray().length ; i++) {
            System.out.println("IndicesData of Image :"+"   "+objloader.getIndicesArray()[i]);
        }
        try {

            toAttributeBinding(vertexArrayObject, objloader.getVerticesArray(), 3);
            toAttributeBinding(vertexArrayObject, objloader.getTextureArray(), 2);
            // Instanced geomeetry... :
            // toAttributeBinding(vertexArrayObject, offset, 2);
            // toAttributeBinding(vertexArrayObject, random, 1);
            // setAttributeDivisor(2, 1);
            // setAttributeDivisor(3, 1);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetBufferOverFlowReminder();
        }
        //Vector2f ids = UTIL.gainTextureID(totalAmountofShader);
        setDimensionTexture(9, 4, 1024, 768); // dimension of first texture found in the dir: /res4/0000;
        initSingleTexture("res3/background", GL_TEXTURE_RECTANGLE, GL_CLAMP_TO_EDGE, GL_TEXTURE5, 1024, 768);
        initSingleTexture("res3/background", GL_TEXTURE_RECTANGLE, GL_CLAMP_TO_EDGE, GL_TEXTURE6, 1024, 768);
        unbindVAO();
        totalAmountofShader++;

        return new Temp(objloader.getIndicesArray().length, vertexArrayObject, 0);
    }

    public ITemp vaoProcedurePlane() {

        int vertexArrayObject = buildVertexArrayObject();
        bindVertexArrayObject(vertexArrayObject);
        System.out.println("DATA OF VAO"+vertexArrayObject);
        attrLocationIndex = 0;
        attrBindingIndex = 0;
        bindIndices(vertexArrayObject, objloader.getIndicesArray());
        try {

            toAttributeBinding(vertexArrayObject, objloader.getVerticesArray(), 3);
            toAttributeBinding(vertexArrayObject, objloader.getTextureArray(), 2);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetBufferOverFlowReminder();
        }


        framebuffers.initFramebufferProcedure();
        unbindVAO();
        totalAmountofShader++;
        initSingleTexture("res3/black", GL_TEXTURE_2D, GL_CLAMP_TO_EDGE, GL_TEXTURE4, 512, 512);
        return new Temp(objloader.getIndicesArray().length, vertexArrayObject, 0);
    }

    public ITemp vaoProcedureSculpture() {

        int vertexArrayObject = buildVertexArrayObject();
        attrLocationIndex = 0;
        attrBindingIndex = 0;

        bindVertexArrayObject(vertexArrayObject);
        bindIndices(vertexArrayObject, objloader.getIndicesArray());

        try {

            toAttributeBinding(vertexArrayObject, objloader.getVerticesArray(), 3);
            toAttributeBinding(vertexArrayObject, objloader.getTextureArray(), 2);
            toAttributeBinding(vertexArrayObject, objloader.getNormalsArray(), 3);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetBufferOverFlowReminder();
        }

      initSingleTexture2("bronzetexture" ,GL_TEXTURE1);
      initCubeTexture(reference.getList(), GL_TEXTURE_CUBE_MAP, GL_CLAMP_TO_EDGE, GL_TEXTURE2);

        unbindVAO();
        totalAmountofShader++;

        return new Temp(objloader.getIndicesArray().length, vertexArrayObject, 0);
    }

    public void initCubeTexture(List<String> lis, int modus, int clamping, int textureUnit) {

        int textureCubeID = buildVertexArrayTexture();

        activateTextureUnit(textureUnit);
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureCubeID);
       // glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_BASE_LEVEL, 0);
      //  glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAX_LEVEL, 5);
        allocateInternalTextureBufferStorage(modus, 6, GL_RGBA8, 512  ,512);

        for (int i = 0; i <= 5; ++i)
        {
            ByteBuffer data = getTextureBufferData("res3/" + lis.get(i));
            mapTexureBufferData(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, 0, 0, 512,512, GL_RGBA8, GL_UNSIGNED_BYTE, data);
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        glGenerateMipmap(GL_TEXTURE_CUBE_MAP);


    }

    public void initSingleTexture(String fileName, int modus, int clamping, int textureUnit, int width, int heigth) {


        int textureID = buildVertexArrayTexture();

        activateTextureUnit(textureUnit);
        bindTexture(modus, textureID);

        ByteBuffer data = getTextureBufferData(fileName);
        allocateInternalTextureBufferStorage(modus, 1, GL_RGBA8, width, heigth);
        setPixelStoreInteger(GL_UNPACK_ALIGNMENT, 4);
        mapTexureBufferData(modus, 0, 0, 0, width, heigth, GL_RGBA, GL_UNSIGNED_BYTE, data);

        setTextureParamater(modus, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        setTextureParamater(modus, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        setTextureParamater(modus, GL_TEXTURE_WRAP_S, clamping);
        setTextureParamater(modus, GL_TEXTURE_WRAP_T, clamping);

 //  glBindTexture(modus, 0);
    }
    public void initSingleTexture2(String fileName, int textureUnit) {
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream("res2/" + fileName + ".png");
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();

            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();

        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_2D, texId);


        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);


        // Upload the texture data and generate mip maps (for scaling)

        // aufpassen veraltete Funktion! glTexImage ! -> glTexStorage2D !
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, tWidth, tHeight, 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL_TEXTURE_2D);


        // Setup the ST coordinate system
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL_REPEAT);


        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);



    }

    public ByteBuffer getTextureBufferData(String fileName) {

        ByteBuffer buf = null;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(fileName + ".png");
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture

            tempWith = decoder.getWidth();
            tempHeight = decoder.getHeight();

            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

            buf.flip();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return buf;

    }

    public int buildVertexArrayTexture() {
        return glGenTextures();
    }

    public void activateTextureUnit(int textureUnit) {
        glActiveTexture(textureUnit);
    }

    public void bindTexture(int modus, int textureID) {
        glBindTexture(modus, textureID);
    }

    public void allocateInternalTextureBufferStorage(int modus, int level, int internalformat, int tempWith, int tempHeight) {
        glTexStorage2D(modus, level, internalformat, tempWith, tempHeight);
    }

    public void mapTexureBufferData(int modus, int level, int xoffset, int yoffset, int sizewidth, int sizeheight, int format, int type, ByteBuffer data) {
        glTexSubImage2D(modus, level, xoffset, yoffset, sizewidth, sizeheight, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    public void setPixelStoreInteger(int modus, int consecutive) {
        glPixelStorei(modus, consecutive);
    }

    public void setTextureParamater(int modus, int pname, int parameter) {
        glTexParameteri(modus, pname, parameter);
    }

    public void setDimensionTexture(int programId, int uniformlocationId, int sizewidth, int sizeheight) {
        glProgramUniform2f(programId, uniformlocationId, sizewidth, sizeheight);
    }

    public int getTempWith() {
        return tempWith;
    }

    public int getTempHeight() {
        return tempHeight;
    }
}
