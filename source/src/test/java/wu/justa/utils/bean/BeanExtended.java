package wu.justa.utils.bean;

import java.util.Calendar;

public class BeanExtended extends BeanWithArray {
	private Integer id;
	private Long myLong;
	private short myShort;
	private byte myByte;
	private float myFloat;
	private Calendar myCalendar;
	private Boolean myBoolean;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMyLong() {
		return myLong;
	}

	public void setMyLong(Long myLong) {
		this.myLong = myLong;
	}

	public short getMyShort() {
		return myShort;
	}

	public void setMyShort(short myShort) {
		this.myShort = myShort;
	}

	public byte getMyByte() {
		return myByte;
	}

	public void setMyByte(byte myByte) {
		this.myByte = myByte;
	}

	public float getMyFloat() {
		return myFloat;
	}

	public void setMyFloat(float myFloat) {
		this.myFloat = myFloat;
	}

	public Calendar getMyCalendar() {
		return myCalendar;
	}

	public void setMyCalendar(Calendar myCalendar) {
		this.myCalendar = myCalendar;
	}

	public Boolean getMyBoolean() {
		return myBoolean;
	}

	public void setMyBoolean(Boolean myBoolean) {
		this.myBoolean = myBoolean;
	}

}
