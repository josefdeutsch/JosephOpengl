package Interface;

import java.util.HashMap;
import java.util.List;

/**
 * Created by philippPC on 31.12.2017.
 */
public interface IReference {

    void readInPath();
    int returnPointer(int counter);
    String[] getTotalAmountofSculptureReferences();
    void setTotalAmountofSculptureReferences(String[] totalAmountofSculptureReferences);
    List<String> getPath();
    void initStringArray();
    String[] getStrings();
    public void initEnviromentmap();
    public List<String> getList();
    public HashMap<Integer,String> getMap();
}
