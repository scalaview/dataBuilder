package builder.highlighter.imp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import util.ClassHelper;
import vo.SearchResult;
import builder.highlighter.BasicHighlighter;

public class SearchHighlighter implements BasicHighlighter {

	private String[] fiels;
	private static final int SUBLENGTH = 100;

	public SearchHighlighter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchHighlighter(String[] fiels) {
		super();
		this.fiels = fiels;
	}

	@Override
	public SearchResult doHighlight(Highlighter highlighter, Analyzer analyzer,
			Document document) throws IOException,
			InvalidTokenOffsetsException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		SearchResult sr = new SearchResult();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (String f : fiels) {
			String hc = null;
			String content = document.get(f);
			if (!f.toLowerCase().equals("postingdate")) {
				hc = highlighter.getBestFragment(analyzer, f, content);
			} else {
				hc = sf.format(new Date(Long.valueOf(content)));
			}
			if (hc == null) {
				int endIndex = Math.min(SUBLENGTH, content.length());
				hc = content.substring(0, endIndex);
			}
			ClassHelper.setPropertiesByMethods(sr, f, hc);
		}
		return sr;
	}

	public String[] getFiels() {
		return fiels;
	}

	public void setFiels(String[] fiels) {
		this.fiels = fiels;
	}

}
