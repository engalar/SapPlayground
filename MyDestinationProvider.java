import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class MyDestinationProvider implements DestinationDataProvider {

  private Properties destinationProperties;

  public MyDestinationProvider() {
    // 初始化目标属性配置
    destinationProperties = new Properties();
    destinationProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.0.1.125");
    destinationProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "02");
    destinationProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "100");
    // destinationProperties.setProperty(DestinationDataProvider.JCO_DEST, "TW3");
    destinationProperties.setProperty(DestinationDataProvider.JCO_USER, "GIRFC");
    destinationProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "ediidoc");
    destinationProperties.setProperty(DestinationDataProvider.JCO_LANG, "EN"); // 可根据需要设置语言
  }

  @Override
  public Properties getDestinationProperties(String destinationName) {
    if (destinationName.equals("myDestination")) {
      return destinationProperties;
    }
    return null;
  }

  @Override
  public void setDestinationDataEventListener(DestinationDataEventListener destinationDataEventListener) {
    // 不需要实现
  }

  @Override
  public boolean supportsEvents() {
    return false;
  }
}