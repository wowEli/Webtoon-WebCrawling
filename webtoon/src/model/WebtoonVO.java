package model;

public class WebtoonVO {
	private int Wid; //pk
	private String title; // 웹툰제목
	private String img; //이미지 주소값
	private String author; //저자
	private int cnt; //조회수
	
	public int getWid() {
		return Wid;
	}
	public void setWid(int wid) {
		Wid = wid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "WebtoonVO [Wid=" + Wid + ", title=" + title + ", img=" + img + ", author=" + author + ", cnt=" + cnt
				+ "]";
	}
	
}
