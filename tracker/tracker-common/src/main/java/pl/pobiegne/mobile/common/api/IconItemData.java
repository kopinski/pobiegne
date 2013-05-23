package pl.pobiegne.mobile.common.api;

public class IconItemData {

	private int icon;

	private String label;
	
	private int item;

	public IconItemData() {
	}

	public IconItemData(String label) {
		this.label = label;
	}

	public IconItemData(int icon, String label) {
		this.icon = icon;
		this.label = label;
	}
	
	public IconItemData(int icon, String label, int item) {
		this.icon = icon;
		this.label = label;
		this.item = item;
	}

	/**
	 * @return the icon
	 */
	public int getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
}