package wu.justa.utils.bean;

import java.util.Set;

public class BeanWithWildSet {
	
	private Set mySet;
	private Set<?> mySet2;	

	public Set getMySet() {
		return mySet;
	}

	public void setMySet(Set mySet) {
		this.mySet = mySet;
	}

	public Set<?> getMySet2() {
		return mySet2;
	}

	public void setMySet2(Set<?> mySet2) {
		this.mySet2 = mySet2;
	}
	

}
