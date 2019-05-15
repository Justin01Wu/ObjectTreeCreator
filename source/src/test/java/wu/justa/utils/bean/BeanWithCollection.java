package wu.justa.utils.bean;

import java.util.Collection;
import java.util.List;

public class BeanWithCollection {
	
	private Collection<User> users;	
	private List<Integer> userIds;

	
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
}