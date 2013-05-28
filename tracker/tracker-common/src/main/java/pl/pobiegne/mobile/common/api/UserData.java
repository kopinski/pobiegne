package pl.pobiegne.mobile.common.api;

import org.joda.time.DateTime;

public class UserData {

	private String name;

	private int weight;

	private int height;

	private boolean isMale;

	private DateTime birthday;

	public UserData(DateTime birthday, int weight, int height, boolean isMale) {
		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
		this.isMale = isMale;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the isMale
	 */
	public boolean isMale() {
		return isMale;
	}

	/**
	 * @param isMale
	 *            the isMale to set
	 */
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}
}