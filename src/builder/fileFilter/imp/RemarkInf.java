package builder.fileFilter.imp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import builder.fileFilter.BasicFileFilter;

public class RemarkInf implements BasicFileFilter {
	private Logger log = Logger.getLogger(RemarkInf.class.getName());

	@Override
	public Map<String, String> Filter(Element e, Map<String, String> inf) {
		// println("remark ："
		// + e.text().replace(Jsoup.parse("&nbsp;").text(), " ").trim());
		if (e == null || inf == null) {
			return new HashMap<String, String>();
		}
		log.info("===================start table 2======================");
		Map<String, String> map = inf;
		String itmp = e.text().replace(Jsoup.parse("&nbsp;").text(), " ")
				.trim();
		map.put("Remark", itmp);
		log.info("===================end table 2======================");
		return map;
	}

}
