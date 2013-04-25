package builder.dao.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import util.hbase.HBaseAdminHelper;

public class JobsDAO {

	// private static Logger log = Logger.getLogger(JobsDAO.class);
	private HBaseAdminHelper hbAdmin;
	private HTable jobs;

	public JobsDAO(HBaseAdminHelper hbAdmin, Configuration conf,
			String tableName) throws IOException {
		super();
		this.hbAdmin = hbAdmin;
		this.jobs = new HTable(conf, tableName);
	}

	public Put add(String row, String family, String qualty, Long ts, String val)
			throws IOException {
		Put put = new Put(Bytes.toBytes(row));
		put.add(Bytes.toBytes(family), Bytes.toBytes(qualty), ts,
				Bytes.toBytes(val));
		jobs.put(put);
		return put;
	}

	public Put add(String row, String family, String qualty, String val)
			throws IOException {
		return this.add(row, family, qualty, Long.MAX_VALUE, val);
	}

	public void commit() throws IOException {
		if (jobs != null) {
			this.jobs.flushCommits();
		}

	}

	public void colse() throws IOException {
		if (jobs != null) {
			jobs.close();
		}
	}

	public String getString(String row, String famaily, String qualifier)
			throws IOException {
		return Bytes.toString(getBytes(row, famaily, qualifier));
	}

	public byte[] getBytes(String row, String famaily, String qualifier)
			throws IOException {
		Get get = new Get(Bytes.toBytes(row));
		get.addColumn(Bytes.toBytes(famaily), Bytes.toBytes(qualifier));
		Result result = jobs.get(get);
		return result.value();
	}

	public HTable getJobs() {
		return jobs;
	}

	public void setJobs(HTable jobs) {
		this.jobs = jobs;
	}

	public HBaseAdminHelper getHbAdmin() {
		return hbAdmin;
	}

	public void setHbAdmin(HBaseAdminHelper hbAdmin) {
		this.hbAdmin = hbAdmin;
	}

}
