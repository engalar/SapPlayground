
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.*;

public class SAPRFCExample {

    public static void main(String[] args) throws UnsupportedEncodingException {
        JCoDestination destination;

        // 注册自定义的目标提供者
        MyDestinationProvider provider = new MyDestinationProvider();
        Environment.registerDestinationDataProvider(provider);

        try {
            destination = JCoDestinationManager.getDestination("myDestination");
            JCoRepository repository = destination.getRepository();

            JCoFunctionTemplate functionTemplate = repository.getFunctionTemplate("Z_MMW1003_PR");
            JCoFunction function = functionTemplate.getFunction();

            // 285141，285179，284781
            String formatPrNo = String.format("%010d", Integer.valueOf("285141"));
            function.getImportParameterList().setValue("S_BANFN", formatPrNo);

            function.execute(destination);

            // 获取函数模块的表格参数
            JCoParameterList tableParams = function.getTableParameterList();

            // 遍历表格参数列表
            for (JCoFieldIterator iter = tableParams.getFieldIterator(); iter.hasNextField();) {
                JCoField field = iter.nextField();
                String paramName = field.getName();
                JCoTable table = field.getTable();

                // 遍历表格参数的行
                for (int i = 0; i < table.getNumRows(); i++) {
                    table.setRow(i);

                    // 打印表格参数的键值
                    System.out.println("Parameter: " + paramName + ", Row: " + i);

                    // 遍历表格参数的列
                    for (JCoField tableField : table) {
                        String columnName = tableField.getName();
                        String columnType = tableField.getTypeAsString();
                        Object columnValue = tableField.getValue();

                        if(columnType=="CHAR"){
                            columnValue = new String((columnValue.toString()).getBytes("iso-8859-1"), "Big5");
                        }
                        System.out.println("Column: " + columnName + ", Value: " + columnValue+ ", Type: " + columnType);
                    }
                }
            }

        } catch (JCoException ex) {
            ex.printStackTrace();
        }
    }
}