package info;

public class ServiceInfo {
	private int lastTime;
	private String url;
	private String resposeInfo;
	public ServiceInfo(String url) {
		super();
		this.url = url;
	}
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setResposeInfo(String resposeInfo) {
		this.resposeInfo = resposeInfo;
	}
	public int getLastTime() {
		return lastTime;
	}
	public String getUrl() {
		return url;
	}
	public String getResposeInfo() {
		return resposeInfo;
	}
	
	
}
