package util.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTableHelper {

	private Configuration conf = null;
	private HTable table = null;

	protected HBaseTableHelper(Configuration conf, String table)
			throws IOException {
		this.conf = conf;
		this.table = new HTable(conf, table);
	}

	public static HBaseTableHelper getHelper(Configuration conf, String table)
			throws IOException {
		return new HBaseTableHelper(conf, table);
	}

	public void put(String row, String fam, String qual, String val)
			throws IOException {
		Put put = new Put(Bytes.toBytes(row));
		put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), Bytes.toBytes(val));
		table.put(put);
	}

	public void put(String row, String fam, String qual, long ts, String val)
			throws IOException {
		Put put = new Put(Bytes.toBytes(row));
		put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), ts, Bytes.toBytes(val));
		table.put(put);
	}

	public void put(String[] rows, String[] fams, String[] quals, long[] ts,
			String[] vals) throws IOException {
		for (String row : rows) {
			Put put = new Put(Bytes.toBytes(row));
			for (String fam : fams) {
				int v = 0;
				for (String qual : quals) {
					String val = vals[v < vals.length ? v : vals.length - 1];
					long t = ts[v < ts.length ? v : ts.length - 1];
					put.add(Bytes.toBytes(fam), Bytes.toBytes(qual), t,
							Bytes.toBytes(val));
					v++;
				}
			}
			table.put(put);
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
		Result result = table.get(get);
		return result.value();
	}

	public void flush() throws IOException {
		if (table != null) {
			table.flushCommits();
		}
	}

	public void close() throws IOException {
		if (table != null) {
			table.close();
		}
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public HTable getTable() {
		return table;
	}

	public void setTable(HTable table) {
		this.table = table;
	}

}
