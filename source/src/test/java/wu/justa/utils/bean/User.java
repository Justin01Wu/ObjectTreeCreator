package wu.justa.utils.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	private String desc;	
	private Integer id;	
	private String name;	
	private TypeEnum type;
	private String password;	
	private Date birthDate;
	private List<String> emails = new ArrayList<String>();
	
	private int x;
	
	private Address homeAddress;
	
	public User(){		
	}
	public User(Integer id, String name){
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emailList) {
		this.emails = emailList;
	}
	public void addEmails(String email) {
		this.emails.add(email);
	}
	
	public Address getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public String getPassword() {
		return password;
	}	
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public TypeEnum getType() {
		return type;
	}
	public void setType(TypeEnum type) {
		this.type = type;
	}

}
