package builder.fileFilter.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import builder.fileFilter.BasicFileFilter;

public class CompanyEssentialInfo implements BasicFileFilter {
	private Logger log = Logger.getLogger(CompanyEssentialInfo.class.getName());

	@Override
	public Map<String, String> Filter(Element e, Map<String, String> inf) {
		if (e == null || inf == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> map = inf;
		log.info("====================start table 1===============================");
		// title
		log.info("title : " + e.select("td[class=sr_bt]").text().trim());
		map.put("Title", e.select("td[class=sr_bt]").text().trim());
		log.info("company : "
				+ e.select("a[href*=search.51job.com]").get(0).text().trim());
		map.put("Company", e.select("a[href*=search.51job.com]").get(0).text()
				.trim());
		for (Iterator<Node> tn = e.select("td[valign=bottom]").get(0)
				.previousElementSibling().childNodes().iterator(); tn.hasNext();) {
			Node d = tn.next();
			String tmp = d.toString();
			if (tn.hasNext()) {
				if (-1 != tmp.indexOf(">公司行业：<")) {
					// log.info("公司行业："
					// + tn.next().toString().replace("&nbsp;", " ")
					// .trim());
					map.put("Industry",
							tn.next().toString().replace("&nbsp;", " ").trim());
				} else if (-1 != tmp.indexOf(">公司性质：<")) {
					// log.info("公司性质："
					// + tn.next().toString().replace("&nbsp;", " ")
					// .trim());
					map.put("CompanyType",
							tn.next().toString().replace("&nbsp;", " ").trim());
				} else if (-1 != tmp.indexOf(">公司规模：<")) {
					// log.info("公司规模："
					// + tn.next().toString().replace("&nbsp;", " ")
					// .trim());
					map.put("CompanySize",
							tn.next().toString().replace("&nbsp;", " ").trim());
				}
			}
		}
		log.info("====================end table 1==============================");
		return map;
	}
}
