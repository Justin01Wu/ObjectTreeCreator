package wu.justa.utils.bean;

import java.util.List;

public class ClassGeneric<T> {
    private List<T>  items;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
