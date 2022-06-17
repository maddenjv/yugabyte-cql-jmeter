# yugabyte-cql-jmeter

This is a simple, sample JMeter application to use with Yugabyte Cloud. It is adopted from [Stress-testing Cassandra with JMeter and Groovy](https://medium.com/@alain.rastoul/jmeter-groovy-testing-cassandra-2269a9df6f0e) by Alain Roustoul and references the [Yugabyte Sample Java application](https://docs.yugabyte.com/preview/yugabyte-cloud/cloud-examples/connect-ycql-application/#write-a-sample-java-application).

## Instructions

1. Download and build the [Yugabyte Sample Java application](https://docs.yugabyte.com/preview/yugabyte-cloud/cloud-examples/connect-ycql-application/#write-a-sample-java-application).
2. Copy everything from *target/lib* of the sample app into *lib/ext* in the Apache JMeter installation location.
3. Download **yugabyte-cql-jmeter** into a folder and open *SampleTest.jmx* with JMeter.
4. Modify **User Defined Variables** and set the following.
* *clusterNodes* to your Yugabyte cloud cluster hostname.
* *localDc* to your Yugabyte cloud local datacenter name.
* *user* to your Yugabyte cloud username.
* *password* to the password for the user set in the previous parameter.
* *cert* to the path to the root.crt file downloaded from Yugabyte Cloud for your cluster.
5. Download and extract Yugabyte Cloud CLI. Connect to your cluster and execute *create-keyspace-and-table.cql*.
> SSL_CERTFILE=/path/to/root.crt ycqlsh <CLUSTER HOSTNAME> 9042 -u <USER> -p <PASSWORD> --ssl -f create-keyspace-and-table.cql
6. Run the test in JMeter.

For details on the additional parameters (duration, nlocations, nmetrics), refer to Alain's blog post above.

Tested with [Apache JMeter](https://jmeter.apache.org/) version 5.4.3 and [Groovy](https://groovy-lang.org/) version 2.5.9.