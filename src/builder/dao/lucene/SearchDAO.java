package builder.dao.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

import vo.SearchPage;
import vo.SearchResult;
import builder.highlighter.BasicHighlighter;

@Component("searchDao")
public class SearchDAO {
	static String[] fields = { "Title", "URL", "Company", "Industry",
			"CompanyType", "CompanySize", "Remark", "PostingDate", "Location",
			"Number", "Experience", "Education", "Function", "Description",
			"Salary" };
	private static Logger log = Logger.getLogger(SearchDAO.class);
	private static final String prefix = "<span class=\"keyword\">";
	private static final String suffix = "</span>";
	private static final int FRAGMENTESIZE = 100;

	private final Version LUCENE = Version.LUCENE_35;
	private final Analyzer analyzer = new IKAnalyzer();

//	private String filePath = System.getProperty("user.dir") + "\\index";

	private String filePath = "D:\\My Documents\\index";

	// D:\My Documents

	public void buildIndex(List<Document> doclist) {
		IndexWriterConfig iwc = new IndexWriterConfig(LUCENE, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(this.getDirectory(), iwc);
			for (Document doc : doclist) {
				indexWriter.addDocument(doc);
				indexWriter.commit();
			}
		} catch (Exception e) {
			log.error("获取索引失败", e);
			throw new RuntimeException(e);
		} finally {
			try {
				indexWriter.forceMerge(1, true);
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Map<String, Object> search(String query, SearchPage pager,
			BasicHighlighter highlighter) throws Exception {
		return this.search(this.getQuery(query, fields), pager, highlighter,
				false);
	}

	public Map<String, Object> search(String query, String[] fields,
			 SearchPage pager, BasicHighlighter highlighter) throws Exception {
		return this.search(this.getQuery(query, fields), pager, highlighter,
				false);
	}

	public Map<String, Object> search(Query query, SearchPage pager,
			BasicHighlighter highlighter, boolean needScore) throws Exception {
		/**
		 * 读取索引并建一个搜索器
		 */
		int pageIndex = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		IndexReader r = IndexReader.open(this.getDirectory());
		IndexSearcher indexSearcher = new IndexSearcher(r);
		indexSearcher.setSimilarity(new IKSimilarity());

		Map<String, Object> map = new HashMap<String, Object>();
		// ================================================
		log.info("Query:" + query.toString());
		// System.out.println("Query:" + query.toString());
		// ================================================

		/**
		 * 查找并返回一个结果集
		 */
		TopDocs docs = indexSearcher.search(query, pageIndex * pageSize);
		int count = docs.totalHits;
		pager.setTotalHits(count);

		ScoreDoc doc = null;
		List<SearchResult> relist = new ArrayList<SearchResult>();
		for (int i = ((pageIndex - 1) > 0 ? (pageIndex - 1) : 0) * pageSize; i < pageIndex
				* pageSize
				&& i < count; i++) {
			doc = docs.scoreDocs[i];
			Document document = indexSearcher.doc(doc.doc);
			relist.add(highlighter.doHighlight(this.getHighlighter(query),
					analyzer, document));
			// for (String fie : fields) {
			// println(fie + ":" + document.get(fie));
			// }
			 for (String fie : fields) {
						 println(fie + ":" + document.get(fie));
			}
		}
		println("count :" + count);
		map.put("resultlst", relist);
		return map;
	}

	/**
	 * 生成对全部类型的查询
	 */
	public Query getQuery(String queryStr, String[] fields) throws Exception {
		QueryParser queryParser = new MultiFieldQueryParser(LUCENE, fields,
				analyzer);
		Query query = queryParser.parse(queryStr);
		return query;
	}

	public BooleanQuery getBooleanQuery() {
		return new BooleanQuery();
	}

	public Query getMultiQuery(Query query, BooleanQuery boolQuery,
			String queryStr, String[] fields) throws ParseException {
		QueryParser queryParser = new MultiFieldQueryParser(LUCENE, fields,
				analyzer);
		Query extraQuery = queryParser.parse(queryStr);
		boolQuery.add(query, Occur.MUST);
		boolQuery.add(extraQuery, Occur.MUST);

		return boolQuery;
	}


    public Query getExtraQuery(String queryStr, String[] fields) throws ParseException{
       QueryParser queryParser = new MultiFieldQueryParser(LUCENE, fields, analyzer);
       return queryParser.parse(queryStr);
    } 
	/**
	 * 对结果集进行关键字的加亮处理： new SimpleHTMLFormatter(prefix, suffix); 生成一个高亮器
	 */
	public Highlighter getHighlighter(Query query) {
		Scorer scorer = new QueryScorer(query);
		Formatter formatter = new SimpleHTMLFormatter(prefix, suffix);
		Highlighter highlighter = new Highlighter(formatter, scorer);
		Fragmenter fragmenter = new SimpleFragmenter(FRAGMENTESIZE);
		highlighter.setTextFragmenter(fragmenter);
		return highlighter;
	}

	public Directory getDirectory() throws Exception {
		File indexFile = new File(filePath);

		println("created search index in " + indexFile);

		if (!indexFile.exists()) {
			indexFile.mkdir();
		}

		return FSDirectory.open(indexFile);
	}

	public void setDirectory(String filePath) {
		this.filePath = filePath;
	}

	public static void println(String s) {
		System.out.println(s);

	}
}
