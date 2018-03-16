
public class TestDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] pre,s;
		String str = "x";
		String str2 = "mooner";
		s = str.split(",");
		pre = str2.split(",");
		
		String url = "http://132.224.229.34:18080/getData/syscode001/busicode001/${x}";
		for(int j =0;j<s.length;j++){
			String temp = new String(url);
			System.out.println(temp.replace("${"+s[j]+"}",pre[j]));
			System.out.println(temp);
		}
		//System.out.println(pre);
	}

}
