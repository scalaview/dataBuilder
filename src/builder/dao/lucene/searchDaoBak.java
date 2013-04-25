package builder.dao.lucene;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
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
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

import vo.SearchResult;
import vo.SearchPage;

public class searchDaoBak {
//	private static Log log = LogFactory.getLog(searchDaoBak.class);
////	private Log log=Logger.getLogger(searchDao.class.getName());
//    private final String indexpath = "\\SearchIndex";
////            "./searchIndex";
//    private final String[] fields = {"id", "attachmentId","title", "tag", "description", "type"};
//    private final Version LUCENE = Version.LUCENE_35;
//    private final Analyzer analyzer = new IKAnalyzer();
//    private final String preTag = "<font color='red'> ";
//    private final String postTag = "</font>";
//
//    public void buildIndex(List<Document> doclist) {
//        IndexWriterConfig iwc = new IndexWriterConfig(LUCENE, analyzer);
//        iwc.setOpenMode(OpenMode.CREATE);
//        IndexWriter indexWriter = null;
//        try {
//            indexWriter = new IndexWriter(this.getDirectory(), iwc);
//            for (Document doc : doclist) {
//                indexWriter.addDocument(doc);
//                indexWriter.commit();
//            }
//        } catch (Exception e) {
//        	log.error("获取索引失败", e);
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                indexWriter.forceMerge(1, true);
//                indexWriter.close(); 
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public void add(Document doc) {
//        IndexWriterConfig iwc = new IndexWriterConfig(LUCENE, analyzer);
//        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        IndexWriter indexWriter = null;
//        try {
//            indexWriter = new IndexWriter(this.getDirectory(), iwc);
//            indexWriter.addDocument(doc);
//        } catch (Exception e) {
//        	log.error("获取索引失败", e);
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                indexWriter.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void delete(Term term) {
//        IndexWriterConfig iwc = new IndexWriterConfig(LUCENE, analyzer);
//        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        IndexWriter indexWriter = null;
//        try {
//            indexWriter = new IndexWriter(this.getDirectory(), iwc);
//            indexWriter.deleteDocuments(term);
//
//        } catch (Exception e) {
//        	log.error("获取索引失败", e);
//            throw new RuntimeException(e);
//        } finally {
//            try {
////                indexWriter.forceMerge(1, true);
//                indexWriter.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void update(Term term, Document doc) {
//        IndexWriterConfig iwc = new IndexWriterConfig(LUCENE, analyzer);
//        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        IndexWriter indexWriter = null;
//        try {
//            indexWriter = new IndexWriter(this.getDirectory(), iwc);
//            indexWriter.updateDocument(term, doc);
//        } catch (Exception e) {
//        	log.error("获取索引失败", e);
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                indexWriter.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public Map<String, Object> search(Query query, SearchPage page) throws Exception {
//        /**
//         * 读取索引并建一个搜索器
//         */
//        IndexReader r = IndexReader.open(this.getDirectory());
//        IndexSearcher indexSearcher = new IndexSearcher(r);
//        indexSearcher.setSimilarity(new IKSimilarity());
//        
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (page == null) {
//            page = new SearchPage();
//        }
//        //================================================
//        log.info("Query:" + query.toString());
//        System.out.println("Query:" + query.toString());
//        //================================================
//        
//        /**
//         * 查找并返回一个结果集
//         */
//        TopDocs docs = indexSearcher.search(query, page.getCurrentPage() * page.getPageSize());
//        if (page.getTotalHits() == 0) {
//            page.setTotalHits(docs.totalHits);
//            page.setPageSize(page.getPageSize() < docs.totalHits ? page.getPageSize() : docs.totalHits);
//        }
//        
////        System.out.println("search  total : " + page.getTotalHits());
//        map.put("page", page);
//
//        /**
//         * 对结果集进行关键字的加亮处理：
//         * new SimpleHTMLFormatter(preTag, postTag);
//         * 生成一个高亮器
//         */
//        Scorer scorer = new QueryScorer(query);
//        Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
//        Highlighter highlighter = new Highlighter(formatter, scorer);
//
//        Fragmenter fragmenter = new SimpleFragmenter(50);
//        highlighter.setTextFragmenter(fragmenter);
//        /**
//         * 对关键字进行高亮
//         * 并把结果集整理成为mySearchFile的队列
//         * 
//         */
//        ScoreDoc doc = null;
//        List<SearchResult> relist = new ArrayList<SearchResult>();
//        for (int i = (page.getCurrentPage() - 1) * page.getPageSize();
//                i < page.getCurrentPage() * page.getPageSize(); i++) {
//            doc = docs.scoreDocs[i];
//            Document document = indexSearcher.doc(doc.doc);
//            SearchResult myFile = new SearchResult();
//            myFile.setId(document.get(fields[0]));
//            myFile.setAttachmentId(document.get(fields[1]));
//
//            String hc = highlighter.getBestFragment(analyzer, fields[2], document.get(fields[2]));
//            if (hc == null) {
//                hc = document.get(fields[2]);
//            }
//            myFile.setTitle(hc);
//            
//            hc = highlighter.getBestFragment(analyzer, fields[3], document.get(fields[3]));
//            if (hc == null) {
//                hc = document.get(fields[3]);
//            }
//            myFile.setTag(hc);
//      
//
//            hc = highlighter.getBestFragment(analyzer, fields[4], document.get(fields[4]));
//            if (hc == null) {
//                String content = document.get(fields[4]);
//                int endIndex = Math.min(50, content.length());
//                hc = content.substring(0, endIndex);
//            }
//            myFile.setContent(hc);
//
//            hc = highlighter.getBestFragment(analyzer, fields[5], document.get(fields[5]));
//            if (hc == null) {
//                hc = document.get(fields[5]);
//            }
//            myFile.setType(hc);
//            relist.add(myFile);
//            
//        }
//
//        r.close();
//        indexSearcher.close();
//        map.put("relist", relist);
//        return map;
//    }
//    /**
//     * 
//     * 取index 的目录
//     * @throws Exception 
//     */
//    private Directory getDirectory() throws Exception {
//        File indexFile = this.getClassPathFile(searchDaoBak.class);
//
//        System.out.println("created search index in "+java.net.URLDecoder.decode(indexFile.getAbsolutePath(), "UTF-8"));
//
//        if (!indexFile.exists()) {
//            indexFile.mkdir();
//        }
//
//
//        return FSDirectory.open(indexFile);
//    }
//    /**
//     * 
//     * @param 把当前类传入生成当前类所在目录的File
//     * @return 
//     */
//    public File getClassFile(Class clazz) {
//        URL path = clazz.getResource(clazz.getName().substring(
//                clazz.getName().lastIndexOf(".") + 1) + ".classs");
//        if (path == null) {
//            String name = clazz.getName().replaceAll("[.]", "/");
//            path = clazz.getResource("/" + name + ".class");
//        }
//        return new File(path.getFile());
//    }
//
//    public File getClassPathFile(Class clazz) {
//        File file = getClassFile(clazz);
//        for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++) {
//
//            file = file.getParentFile();
//        }
//        if (file.getName().toUpperCase().endsWith(".JAR!")) {
//            file = file.getParentFile();
//        }
//
//        file = new File(file.getParentFile().getAbsolutePath()+indexpath);
//        return file;
//    }
//    /**
//     * 生成对全部类型的查询
//     */
//    public Query getQuery(String queryStr) throws Exception {
//        String[] fields = {"title","tag", "description", "type"};
//        QueryParser queryParser = new MultiFieldQueryParser(LUCENE, fields, analyzer);
//        Query query = queryParser.parse(queryStr);
//
//        return query;
//    }
//    /**
//     *  生成对某个类型的查询
//     * @param 全部类型的查询，需要自定义查询的type的名字
//     */
//    public Query getTypeQuery(Query query1, String queryStr) throws Exception {
//
//        String[] fields = {"title","tag", "description", "type"};
//        QueryParser queryParser = new MultiFieldQueryParser(LUCENE, fields, analyzer);
//        Query query2 = queryParser.parse(queryStr);
//        BooleanQuery boolQuery = new BooleanQuery();
//        
//        boolQuery.add(query1, Occur.MUST);
//        boolQuery.add(query2, Occur.MUST);
//
//        return boolQuery;
//    }
}
