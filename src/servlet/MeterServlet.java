package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import info.ReqPara;
import utils.AnalyseService;
import utils.TextReader;
@WebServlet("/Upload")
public class MeterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String uploadPath = "D:\\temp"; // 上传文件的目录
	private String tempPath = "d:\\temp\\buffer\\"; // 临时文件目录
	File tempPathFile;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		System.out.println("haha");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 页面所有的数据封装到了HttpServletRequest对象里面
		// 设置编码这行代码放在最前面
		req.setCharacterEncoding("UTF-8");
		System.out.println("haha post");
		String url = "";
		String encode = "";
		String monitor = "";
		String fileName = null;
		int loop = 0, thread_num = 0;
		String relation = "";
		HashMap<String, String> header = new HashMap<String, String>();
		TextReader tr = new TextReader();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		factory.setRepository(tempPathFile);
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
		List<FileItem> items;
		try {
			items = upload.parseRequest(req);
			Iterator<FileItem> it = items.iterator();
			HashMap<String, String> key = new HashMap<String, String>();
			HashMap<String, String> value = new HashMap<String, String>();
			
			while (it.hasNext()) {
				FileItem fi = (FileItem) it.next();
				if (!fi.isFormField()) {
					fileName = fi.getName();
					if (fileName != null) {
						File fullFile = new File(fi.getName());
						File savedFile = new File(uploadPath, fullFile.getName());
						fi.write(savedFile);
						tr.fileRead(savedFile.getAbsolutePath());
					}
				} else {
					String name = fi.getFieldName();

					switch (name) {
					case "url":
						url = fi.getString();
						break;
					case "encode":
						encode = fi.getString();
						break;
					case "monitor":
						monitor = fi.getString();
						break;
					case "loop":
						loop = Integer.parseInt(fi.getString());
						break;
					case "thread_num":
						thread_num = Integer.parseInt(fi.getString());
						break;
					case "relation":
						relation = fi.getString();
						break;
					default:
						if (name.startsWith("key")) {
							key.put(name.substring(3, name.length() - 1), fi.getString());
						} else if (name.startsWith("value")) {
							value.put(name.substring(5, name.length() - 1), fi.getString());
						}
						break;
					}
				}
			}
			for (String s : key.keySet()) {
				header.put(key.get(s), value.get(s));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("size:" +tr.getContext().size());
		getURLList(tr.getContext(),relation,url);
		ReqPara rp = new ReqPara(tr.getContext(),encode,loop,thread_num,relation,header);
		AnalyseService as = new AnalyseService(rp);
		try {
			as.threadPoolAccess();
			
			int[][] analyse = as.getAnalyse();
			int[] resTotal = new int[analyse[0].length];
			for(int i=0;i<analyse[0].length;i++)
				for(int j=0;j<analyse.length;j++)
					resTotal[i]+=analyse[j][i];
			for(int i=0;i<resTotal.length;i++)
				System.out.print(resTotal[i]+"--");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> getURLList(ArrayList<String> list,String relation,String url){
		String[] s = relation.split(",");
		int[] pos = new int[s.length];
//		for(int i=0;i<s.length;i++){
//			pos[i] = url.indexOf("${"+s[i]+"}");
//		}
		String[] pre;
		for(int i=0;i<list.size();i++){
			pre = list.get(i).split(",");
			String temp = url;
			for(int j=0;j<s.length;j++){
				temp = temp.replace("${"+s[j]+"}",pre[j]);
			}
			System.out.println(temp);
			list.set(i, temp);
		}
		
		return list;
		
	}
	

}
