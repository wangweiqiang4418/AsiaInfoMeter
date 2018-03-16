package info;

import java.util.ArrayList;
import java.util.HashMap;

public class ReqPara {
	//来自网页的参数在本类中进行保存，方便后期使用
//	private String url;
	private ArrayList<String> context;
	private String encode;
	private int loop;
	private int thread_num;
	private String relation;
	private HashMap<String,String> map;
	public HashMap<String, String> getMap() {
		return map;
	}
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
//	public String getUrl() {
//		return url;
//	}
//	public void setUrl(String url) {
//		this.url = url;
//	}
	public ArrayList<String> getContext() {
		return context;
	}
	public void setContext(ArrayList<String> context) {
		this.context = context;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public int getLoop() {
		return loop;
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public int getThread_num() {
		return thread_num;
	}
	public void setThread_num(int thread_num) {
		this.thread_num = thread_num;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public ReqPara(ArrayList<String> context, String encode, int loop, int thread_num, String relation,
			HashMap<String, String> map) {
		super();
//		this.url = url;
		this.context = context;
		this.encode = encode;
		this.loop = loop;
		this.thread_num = thread_num;
		this.relation = relation;
		this.map = map;
	}
	
	
}
