package Builder;

import Helpers.UTIL;
import Interface.IReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by philippPC on 31.12.2017.
 */
public class Reference implements IReference {


    private final int totalAmountofGivenSculptures = 194;
    private final int totalAmountofGivenPictures = 999;
    private String[] totalAmountofPictureReferences;
    private String[] totalAmountofSculptureReferences;
    private List<String> path = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private String[] strings;

    private HashMap<Integer,String> hashMap = new HashMap<>();

    public int min = 0;
    public int max = 4;
    public int maxFirst = 4;

    public Reference(){

        this.totalAmountofPictureReferences = initStringstoRandomValues(new String[totalAmountofGivenPictures],
                UTIL.fillRandomInteger(new int[totalAmountofGivenPictures]));

        this.totalAmountofSculptureReferences = initStringstoRandomValues(new String[totalAmountofGivenSculptures],UTIL.fillRandomInteger(new int[totalAmountofGivenSculptures]));

        initStringArray();
        initEnviromentmap();
        DecimalFormat df = new DecimalFormat("0000");
        for (int i = 0; i <= 194-1 ; i++) {
            hashMap.put(i,df.format(i));
        }
    }
    public HashMap<Integer,String> getMap(){
        return this.hashMap;
    }
    public void readInPath() {

        if (min == 999) min = 0;
        path.add(totalAmountofPictureReferences[min]);
        min++;
        /**
         * for (int i = 0; i < 4; i++) {

         path.add("./res/" + totalAmountofPictureReferences[min] + ".png");
         min++;
         }

         min = max;
         max += maxFirst;**/
    }
    private String[] initStringstoRandomValues(String[] strings, int[] arr) {

        DecimalFormat df = new DecimalFormat("0000");

        for (int i = 0; i < strings.length; i++) {
            strings[arr[i]] = df.format(i);
        }
        return strings;
    }
    public int returnPointer(int counter) {
        return Integer.parseInt(totalAmountofSculptureReferences[counter]);
    }
    public List<String> getPath() {
        return path;
    }
    public List<String> getList() {
        return list;
    }
    public String[] getTotalAmountofSculptureReferences() {
        return totalAmountofSculptureReferences;
    }
    public void setTotalAmountofSculptureReferences(String[] totalAmountofSculptureReferences) {
        this.totalAmountofSculptureReferences = totalAmountofSculptureReferences;

    }
    public void initStringArray() {
        strings = new String[]{

                "0000/0178",
                "0001/0196",
                "0002/0124",
                "0003/0188",
                "0004/0157",
                "0005/0137",
                "0006/0142",
                "0007/0128",
                "0008/0166",
                "0009/0161",
                "0010/0003",
                "0011/0191",
                "0012/0183",
                "0013/0187",
                "0014/0147",
                "0015/0160",
                "0016/0151",
                "0017/0002",
                "0018/0193",
                "0019/0159",
                "0020/0131",
                "0021/0135",
                "0022/0129",
                "0023/0175",
                "0024/0130",
                "0025/0185",
                "0026/0145",
                "0027/0164",
                "0028/0170",
                "0029/0177",
                "0030/0167",
                "0031/0181",
                "0032/0190",
                "0033/0194",
                "0034/0015",
                "0035/0198",
                "0036/0200",
                "0037/0182",
                "0038/0195",
                "0039/0199",
                "0040/0173",
                "0041/0174",
                "0042/0017",
                "0043/0184",
                "0044/0171",
                "0045/0172",
                "0046/0179",
                "0047/0189",
                "0048/0163",
                "0049/0165",
                "0050/0167",
                "0051/0168",
                "0052/0148",
                "0053/0149",
                "0054/0152",
                "0055/0153",
                "0056/0155",
                "0057/0156",
                "0058/0162",
                "0059/0143",
                "0060/0144",
                "0061/0146",
                "0062/0154",
                "0063/0139",
                "0064/0140",
                "0065/0141",
                "0066/0158",
                "0067/0132",
                "0068/0133",
                "0069/0134",
                "0070/0136",
                "0071/0138",
                "0072/0122",
                "0073/0123",
                "0074/0125",
                "0075/0126",
                "0076/0127",
                "0077/0117",
                "0078/0118",
                "0079/0119",
                "0080/0120",
                "0081/0121",
                "0082/0112",
                "0083/0113",
                "0084/0114",
                "0085/0115",
                "0086/0116",
                "0087/0107",
                "0088/0108",
                "0089/0109",
                "0090/0110",
                "0091/0111",
                "0092/0100",
                "0093/0101",
                "0094/0102",
                "0095/0103",
                "0096/0104",
                "0097/0105",
                "0098/0106",
                "0099/0095",
                "0100/0096",
                "0101/0097",
                "0102/0098",
                "0103/0099",
                "0104/0094",
                "0105/0089",
                "0106/0092",
                "0107/0091",
                "0108/0090",
                "0109/0093",
                "0110/0085",
                "0111/0086",
                "0112/0087",
                "0113/0088",
                "0114/0079",
                "0115/0084",
                "0116/0083",
                "0117/0082",
                "0118/0081",
                "0119/0080",
                "0120/0078",
                "0121/0077",
                "0122/0076",
                "0123/0075",
                "0124/0074",
                "0125/0073",
                "0126/0072",
                "0127/0071",
                "0128/0070",
                "0129/0069",
                "0130/0068",
                "0131/0067",
                "0132/0066",
                "0133/0065",
                "0134/0064",
                "0135/0063",
                "0136/0062",
                "0137/0061",
                "0138/0060",
                "0139/0059",
                "0140/0058",
                "0141/0057",
                "0142/0056",
                "0143/0018",
                "0144/0054",
                "0145/0053",
                "0146/0052",
                "0147/0051",
                "0148/0050",
                "0149/0049",
                "0150/0048",
                "0151/0047",
                "0152/0046",
                "0153/0045",
                "0154/0044",
                "0155/0043",
                "0156/0042",
                "0157/0041",
                "0158/0040",
                "0159/0039",
                "0160/0038",
                "0161/0037",
                "0162/0036",
                "0163/0035",
                "0164/0034",
                "0165/0033",
                "0166/0032",
                "0167/0031",
                "0168/0019",
                "0169/0029",
                "0170/0028",
                "0171/0027",
                "0172/0026",
                "0173/0025",
                "0174/0024",
                "0175/0023",
                "0176/0022",
                "0177/0021",
                "0178/0020",
                "0179/0016",
                "0180/0014",
                "0181/0169",
                "0182/0012",
                "0183/0011",
                "0184/0010",
                "0185/0186",
                "0186/0192",
                "0187/0008",
                "0188/0009",
                "0189/0007",
                "0190/0006",
                "0191/0005",
                "0192/0004",
                "0193/0001",
        };

    }
    public String[] getStrings() {
        return strings;
    }
    public void initEnviromentmap() {
        String string = "gold";

      /**  list.add("0001");
        list.add("0002");
        list.add("0003");
        list.add("0004");
        list.add("0005");
        list.add("0006");
**/

        list.add("0001");
        list.add("0002");
        list.add("0003");
        list.add("0004");
        list.add("0005");
        list.add("0006");
    }
}
