import org.apache.jmeter.util.JMeterUtils;
import com.datastax.oss.driver.api.core.CqlSession;

Properties properties = JMeterUtils.getJMeterProperties();
CqlSession session = properties.get("session");
session.close();