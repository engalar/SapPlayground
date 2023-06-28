
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Properties;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

public class SAPRFCExample {

    public static void main(String[] args) throws UnsupportedEncodingException {
        JCO.Client mConnection = JCO.createClient("100", "GIRFC", "ediidoc", "EN", "10.0.1.125", "02", null, null);
        JCO.Repository mRepository = new JCO.Repository("ARAsoft", mConnection);

        // justin提供的SAP函数接口
        IFunctionTemplate ft = mRepository.getFunctionTemplate("Z_MMW1003_PR");
        if (ft == null)
            throw new RuntimeException("not found in SAP.");
        JCO.Function jcoFunction = ft.getFunction();
        JCO.ParameterList IN = jcoFunction.getImportParameterList();
        String formatPrNo = String.format("%010d", Integer.valueOf("285141"));
        IN.setValue(formatPrNo, "S_BANFN");
        mConnection.execute(jcoFunction);
        // 传回的结果集
        ParameterList tableParameterList = jcoFunction.getTableParameterList();
        if (tableParameterList != null) {
            Table table = tableParameterList.getTable(2);
            table.setRow(0);
            String s = table.getString("TXT20");
            System.out.println(new String(s.getBytes("iso-8859-1"), "Big5"));
            /*
             * *
             * for (int i = 0; i < tableParameterList.getFieldCount(); i++) {
             * JCO.Table table = tableParameterList.getTable(i);
             * System.out.println("Table Name: " + table.getName());
             * 
             * for (int j = 0; j < table.getNumRows(); j++) {
             * table.setRow(j);
             * System.out.println("Row " + j + ":");
             * 
             * for (int k = 0; k < table.getNumFields(); k++) {
             * System.out.println(table.getName(k) + " = " + table.getString(k));
             * }
             * 
             * System.out.println("------------------------");
             * }
             * }
             */
        }
    }
}