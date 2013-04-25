package vo;

public class SearchPage {
	private int totalHits;
	private int currentPage;
	private int PageSize;
	private int pages;
	
	public SearchPage() {
		super();
		this.totalHits = 0;
		this.currentPage = 1;
		PageSize = 3;
	}

	public SearchPage(int pageSize) {
		super();
		this.totalHits = 0;
		PageSize = pageSize;
		currentPage=1;
	}
	
	public int getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
}
