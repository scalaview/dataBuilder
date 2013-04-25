/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util.lucene;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

public class Database2Document {
	public final static String[] needStore = { "Title", "URL", "Location",
			"Company", "Industry", "Function", "Description", "Education"};
	public final static String[] needAnalyze = { "Title", "Location",
			"Company", "Industry", "Function", "Description" , "URL"};

	public final static String[] needNumberic = { "PostingDate" };

	//
	// public List<Document> getDocList() throws Exception {
	//
	// List<Document> doclist = new ArrayList<Document>();
	//
	//
	// List<Resource> list1 = dao.query(Resource.class, Cnd.where(null), null);
	// List<Attachment> list2 = dao.query(Attachment.class, Cnd.where(null),
	// null);
	// for (Resource r : list1) {
	// for(Attachment att :list2){
	// if(att.getId()==r.getAttachmentId()){
	// r.setAttachment(att);
	// break;
	// }
	// }
	// doclist.add(this.DocTool(r));
	// }
	// return doclist;
	// }
	public static List<Document> getDocList(List<Map<String, String>> l) throws ParseException {
		List<Document> list = new ArrayList<Document>();
		for (Iterator<Map<String, String>> it = l.iterator(); it.hasNext();) {
			list.add(DocTool(it.next()));
		}
		return list;
	}

	public static Document DocTool(Map<String, String> map) throws ParseException {
		Document doc = new Document();
		// Attachment att = r.getAttachment();
		/*
		 * filename Title URL Company Industry CompanyType CompanySize Remark
		 * PostingDate Location Number Experience Education Function Description
		 * Saraly
		 */
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		List<String> storeList = Arrays.asList(needStore);
		List<String> analyzedList = Arrays.asList(needAnalyze);
		List<String> numbericList = Arrays.asList(needNumberic);
		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
			String key = i.next();
			String val = map.get(key);
			/**
			 * if() doc.add(new
			 * NumericField("PostingDate").setLongValue(value));
			 * 
			 * */
			if (val != null && !val.equals("")) {
				if (!numbericList.contains(key)) {
					doc.add(new Field(key, val,
							storeList.contains(key) ? Store.YES : Store.NO,
							analyzedList.contains(key) ? Index.ANALYZED
									: Index.NOT_ANALYZED));
				} else {
					if(key.equals("PostingDate")){
						Date tVal = formatDate.parse(val);
						doc.add(new NumericField("PostingDate", Store.YES, true).setLongValue(tVal.getTime()));
					}
				}
			}
		}

		// doc.add(new Field("filename",map.get("filename"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Title",map.get("Title"), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("URL",map.get("URL"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Company",map.get("Company"), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("Industry",map.get("Industry"), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("CompanyType",map.get("CompanyType"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("CompanySize",map.get("CompanySize"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Remark",map.get("Remark"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("PostingDate",map.get("PostingDate"), Store.YES,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Location",map.get("Location"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Experience",map.get("Experience"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Education",map.get("Education"), Store.NO,
		// Index.NOT_ANALYZED));
		// doc.add(new Field("Function",map.get("Function"), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("Description",map.get("Description"), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("Saraly",map.get("Saraly"), Store.NO,
		// Index.NOT_ANALYZED));
		return doc;

	}

	public static Query getMultiQuery(Query rootQuery, Query childQuery) {
		BooleanQuery booleanQuery = (BooleanQuery) rootQuery;
		booleanQuery.add(childQuery, Occur.MUST);
		return booleanQuery;
	}
}
