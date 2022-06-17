drop table if exists sensors.measures;
drop keyspace if exists sensors;

create keyspace if not exists sensors with replication = {'class':'SimpleStrategy', 'replication_factor':2};
 
create table sensors.measures(bucket int, event_time timeuuid, location_id text, metric_id text, value double, primary key ((bucket, location_id), metric_id, event_time)) with clustering order by (metric_id ASC, event_time DESC) and DEFAULT_TIME_TO_LIVE = 604800;
