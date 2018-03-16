package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import info.ReqPara;
import info.ServiceInfo;

public class AnalyseService {

	private ReqPara rp;
	private int[][] analyse;
	public AnalyseService(ReqPara rp) {
		super();
		this.rp = rp;
	}
	
	public int[][] getAnalyse() {
		return analyse;
	}
	public int[] getNumArray(int[] time){
		int[] num = new int[10];
		for(int i=0;i<time.length;i++){
			switch(time[i]/20){
			case 0:num[0]++;break;
			case 1:num[1]++;break;
			case 2:num[2]++;break;
			case 3:num[3]++;break;
			case 4:num[4]++;break;
			case 5:num[5]++;break;
			case 6:num[6]++;break;
			case 7:num[7]++;break;
			case 8:num[8]++;break;
			default:if(time[i]<500) num[9]++; break;
			}
		}
		return num;
	}
	

	public ServiceInfo sendGet(String url) throws IOException{
		ServiceInfo service = new ServiceInfo(url);
		long beginTime = System.currentTimeMillis();
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		URL urlName;
		try {
			urlName = new URL(url);
			URLConnection conn = urlName.openConnection();
			Map<String,String> map = rp.getMap();
			for(String s: map.keySet())
				conn.setRequestProperty(s, map.get(s));
			conn.setConnectTimeout(500);
			conn.connect();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while((line=in.readLine())!=null) {
				sb.append(line);
			}
			service.setResposeInfo(sb.toString());
			long endTime = System.currentTimeMillis();
			service.setLastTime((int)(endTime - beginTime));
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return service;
	}
	
	public void threadPoolAccess() throws InterruptedException{
		final int threadCount = rp.getThread_num();
		final int loop = rp.getLoop();
		ExecutorService fixedPool =Executors.newFixedThreadPool(threadCount);
		CountDownLatch cdl = new CountDownLatch(threadCount);
		int[][] resTime = new int[threadCount][]; //存放所有所有线程跑得service的时间
		analyse = new int[threadCount][];//存放服务相应时间的分段
		for(int i=0;i<threadCount;i++){
			resTime[i] = new int[loop];
			final int threadName = i;
			fixedPool.execute(new Runnable(){
				public void run(){
					int size = rp.getContext().size();
					final int indexBegin = (int)Math.round(Math.random()*size);
					System.out.println(indexBegin);
					int count = 0,error = 0;
					int index = threadName;
					ArrayList<String> url = rp.getContext();
					while(count<loop){
						//System.out.println(al.get((indexBegin+count)%size));
						int pos = (indexBegin+count)%size;
						try {
							ServiceInfo service = sendGet(url.get(pos));
							resTime[index][pos] = service.getLastTime();
							if(service.getResposeInfo().length()==0)
								error++;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							error++;
							resTime[index][pos] = 1000;
							//e.printStackTrace();
						}
						
						count++;
					}
					analyse[index] = getNumArray(resTime[index]);
					System.out.println(Thread.currentThread().getName()+"--error:"+error);
					System.out.println(Thread.currentThread().getName()+"--count:"+count);
					cdl.countDown();
				}
			});
		}
		cdl.await();
	}
}

