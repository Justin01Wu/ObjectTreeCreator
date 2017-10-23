package wu.justa.utils.bean;

import java.util.Map;

public class BeanWithMap {
	private Map<Integer, User> userMap;
	
	public Map<Integer, User> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<Integer, User> userMap) {
		this.userMap = userMap;
	}
}
