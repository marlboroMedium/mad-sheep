package casestudy.business.domain;

public class Board {
	private int num;
	private String writer;
	private String title;
	private String contents;
	private String ip;
	private int readCount;
	private String regDate;
	private String modDate;

	public Board() {

	}

	// 상세조회용
	public Board(int num, String writer, String title, String contents,
			String ip, int readCount, String regDate, String modDate) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.readCount = readCount;
		this.regDate = regDate;
		/** 수정 일시 */
		this.modDate = modDate;
	}

	// 조회용
	public Board(int num, String writer, String title, int readCount,
			String regDate) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.readCount = readCount;
		this.regDate = regDate;
	}

	// 게시글용
	public Board(String writer, String title, String contents, String ip) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	// 수정용
	public Board(int num, String writer, String title, String contents,
			String ip) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getRegDate() {
		return regDate.substring(0, 10);
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {  // "2014-09-18" 형태
		return regDate.substring(0, 10);
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	public String getRegDateTime(){ // "2014-09-18 14:14:30"형태
		return regDate.substring(0, 19);
	}
	
	public String getModDateTime(){
		return modDate.substring(0, 19);
	}

	@Override
	public String toString() {
		return "Board [num=" + num + ", writer=" + writer + ", title=" + title
				+ ", contents=" + contents + ", ip=" + ip + ", readCount="
				+ readCount + ", regDate=" + regDate + ", modDate=" + modDate
				+ "]";
	}

}
