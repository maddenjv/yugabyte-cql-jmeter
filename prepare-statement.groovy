import org.apache.jmeter.util.JMeterUtils;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;

def insertMeasureCqlString = 'insert into sensors.measures (bucket , event_time, location_id, metric_id, value) values (  ?, ?, ?, ?, ? ) ;';

Properties properties = JMeterUtils.getJMeterProperties();
CqlSession session = properties.get("session");

PreparedStatement preparedInsertStatement = session.prepare(insertMeasureCqlString);
properties.put("preparedInsertStatement", preparedInsertStatement);