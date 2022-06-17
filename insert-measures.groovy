import org.apache.jmeter.util.JMeterUtils;
import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.uuid.Uuids;

//  get the shared Session and Prepared statement 
// from JMeter properties
Properties properties = JMeterUtils.getJMeterProperties();
CqlSession session = properties.get("session");

PreparedStatement preparedInsertStatement = properties.get("preparedInsertStatement");
Date eventTime = new Date(); // in ms since 1/1/70
int bucket = eventTime.getTime() / (24*60*60*1000L); // day bucket

int nMetrics = Integer.parseInt(vars.get("nmetrics"));
Random random = new Random((long)bucket);

// The "location" will be the thread number
int threadNum = ctx.getThreadNum();
String location = "location-T" + threadNum;

for (int metric = 1; metric <= nMetrics; metric++) {
    String metricId = "metric-"+metric.toString();
    double value = random.nextDouble() * 100.0;
    // insert into sensors.measures( bucket, event_date,     
    // location_id, metric_id, value ) ' 
    session.execute(
            preparedInsertStatement.bind(bucket,     
                     Uuids.timeBased(),
                     location, metricId, value )
                   .setConsistencyLevel(ConsistencyLevel.ANY)
    );
}