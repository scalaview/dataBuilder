package builder.fileFilter;

import java.util.Map;

import org.jsoup.nodes.Element;

public interface BasicFileFilter {
	public abstract Map<String, String> Filter(Element e,
			Map<String, String> inf);
}
