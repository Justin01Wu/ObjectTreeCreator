package wu.justa.utils.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ManyDataType {
	private String oneString;
	private Double oneDouble;
	private double oneDouble2;
	private Integer oneInteger;
	private int oneInteger2;
	private Date oneDate;
	
	private Set<Integer> integerSet;
	private List<String> stringList;
	
	private boolean oneBoolean;
	private Boolean oneBoolean2;
	
	private List<Address> addressList;
	private TypeEnum oneEnum;
	
	private List<TypeEnum> typeList;
	
	
	public String getOneString() {
		return oneString;
	}
	public void setOneString(String oneString) {
		this.oneString = oneString;
	}
	public Double getOneDouble() {
		return oneDouble;
	}
	public void setOneDouble(Double oneDouble) {
		this.oneDouble = oneDouble;
	}
	public double getOneDouble2() {
		return oneDouble2;
	}
	public void setOneDouble2(double oneDouble2) {
		this.oneDouble2 = oneDouble2;
	}
	public Integer getOneInteger() {
		return oneInteger;
	}
	public void setOneInteger(Integer oneInteger) {
		this.oneInteger = oneInteger;
	}
	public int getOneInteger2() {
		return oneInteger2;
	}
	public void setOneInteger2(int oneInteger2) {
		this.oneInteger2 = oneInteger2;
	}
	public Date getOneDate() {
		return oneDate;
	}
	public void setOneDate(Date oneDate) {
		this.oneDate = oneDate;
	}
	public Set<Integer> getIntegerSet() {
		return integerSet;
	}
	public void setIntegerSet(Set<Integer> integerSet) {
		this.integerSet = integerSet;
	}
	public List<String> getStringList() {
		return stringList;
	}
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	public boolean isOneBoolean() {
		return oneBoolean;
	}
	public void setOneBoolean(boolean oneBoolean) {
		this.oneBoolean = oneBoolean;
	}
	public Boolean getOneBoolean2() {
		return oneBoolean2;
	}
	public void setOneBoolean2(Boolean oneBoolean2) {
		this.oneBoolean2 = oneBoolean2;
	}
	public List<Address> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	public TypeEnum getOneEnum() {
		return oneEnum;
	}
	public void setOneEnum(TypeEnum oneEnum) {
		this.oneEnum = oneEnum;
	}
	public List<TypeEnum> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<TypeEnum> typeList) {
		this.typeList = typeList;
	}

}
