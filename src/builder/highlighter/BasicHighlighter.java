package builder.highlighter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import vo.SearchResult;

public interface BasicHighlighter {
	SearchResult doHighlight(Highlighter highlighter, Analyzer analyzer, Document document)throws IOException, InvalidTokenOffsetsException, IllegalArgumentException, IllegalAccessException, InvocationTargetException ;
}
