package builder.dao.lucene;

import org.springframework.stereotype.Component;

@Component("resourcesDao")
public class ResourcesDAO {

	private String functionData;

	public ResourcesDAO() {
		super();
		// TODO Auto-generated constructor stub
		this.functionData = "["
				+ "{'id':'1','name':'电脑整机'},"
				+ "{'id':'2','name':'笔记本'},"
				+ "{'id':'31','name':'SONY',},"
				+ "{'id':'23','name':'LENOVO'},"
				+ "{'id':'25','name':'IBM'},"
				+ "{'id':'26','name':'宏基'},"
				+ "{'id':'27','name':'联想'},"
				+ "{'id':'28','name':'联想2'},"
				+ "]";
	}

	public String getFunctionData() {
		return functionData;
	}

	public void setFunctionData(String functionData) {
		this.functionData = functionData;
	}

}
