package model;

public class RecentViewVO {
	private int rpk; //허울뿐이 숫자만 세주는 pk
	private String title; // 웹툰 제목 (자바에서만 사용할 변수)
	private String ppk; //사람
	private int wpk; //웹툰PK
	public int getRpk() {
		return rpk;
	}
	public void setRpk(int rpk) {
		this.rpk = rpk;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPpk() {
		return ppk;
	}
	public void setPpk(String ppk) {
		this.ppk = ppk;
	}
	public int getWpk() {
		return wpk;
	}
	public void setWpk(int wpk) {
		this.wpk = wpk;
	}
	
	
}
