package wu.justa.utils.bean;

public class WrongSetterBean {
	
	public static class NoDefaultConstructor{
		public NoDefaultConstructor(int x){
			
		}
	}
	
	private Integer myInt;
	private NoDefaultConstructor myBean;

	public Integer getMyInt() {
		return myInt;
	}

	public void setMyIntAsString(String myIntStr) {
		
		this.myInt = Integer.parseInt(myIntStr);
	}

	public NoDefaultConstructor getMyBean() {
		return myBean;
	}

	public void setMyBean(NoDefaultConstructor myBean) {
		this.myBean = myBean;
	}
	

}
