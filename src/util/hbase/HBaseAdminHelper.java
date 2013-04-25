package util.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseAdminHelper {
	private Configuration conf = null;
	private HBaseAdmin admin = null;

	protected HBaseAdminHelper(Configuration conf) throws IOException {
		this.conf = conf;
		this.admin = new HBaseAdmin(conf);
		
	}

	public static HBaseAdminHelper getHelper(Configuration conf) throws IOException {
		return new HBaseAdminHelper(conf);
	}

	public boolean isTableExists(String table) throws IOException {
		return admin.tableExists(table);
	}

	public void createTable(String table, String... colfams) throws IOException {
		createTable(table, null, colfams);
	}

	public void createTable(String table, byte[][] splitKeys, String... colfams)
			throws IOException {
		HTableDescriptor desc = new HTableDescriptor(table);
		for (String cf : colfams) {
			HColumnDescriptor coldef = new HColumnDescriptor(cf);
			desc.addFamily(coldef);
		}
		if (splitKeys != null) {
			admin.createTable(desc, splitKeys);
		} else {
			admin.createTable(desc);
		}
	}

	public void disableTable(String table) throws IOException {
		admin.disableTable(table);
	}

	public void dropTable(String table) throws IOException {
		if (isTableExists(table)) {
			disableTable(table);
			admin.deleteTable(table);
		}
	}
	public void close() throws IOException{
		if(admin!=null){
			admin.close();
		}
	}
	
	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public HBaseAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(HBaseAdmin admin) {
		this.admin = admin;
	}

}
