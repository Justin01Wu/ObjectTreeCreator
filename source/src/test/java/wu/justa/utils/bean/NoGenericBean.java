package wu.justa.utils.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class NoGenericBean {	
	private Set noGenericSet;
	private List noGenericList;
	private Map noGenericMap;

	public Set getNoGenericSet() {
		return noGenericSet;
	}

	public void setNoGenericSet(Set noGenericSet){
		this.noGenericSet = noGenericSet;
	}

	public List getNoGenericList() {
		return noGenericList;
	}

	public void setNoGenericList(List noGenericList) {
		this.noGenericList = noGenericList;
	}

	public Map getNoGenericMap() {
		return noGenericMap;
	}

	public void setNoGenericMap(Map noGenericMap) {
		this.noGenericMap = noGenericMap;
	}


}
