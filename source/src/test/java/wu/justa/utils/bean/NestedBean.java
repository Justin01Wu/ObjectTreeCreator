package wu.justa.utils.bean;

public class NestedBean {
	private String name;
	private NestedBean parent;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public NestedBean getParent() {
		return parent;
	}
	public void setParent(NestedBean parent) {
		this.parent = parent;
	}
}
