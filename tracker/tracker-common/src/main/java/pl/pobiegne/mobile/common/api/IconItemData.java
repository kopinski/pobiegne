package pl.pobiegne.mobile.common.api;

public class IconItemData {

	private int icon;

	private String label;
	
	private int menu;

	public IconItemData() {
	}

	public IconItemData(String label) {
		this.label = label;
	}

	public IconItemData(int icon, String label) {
		this.icon = icon;
		this.label = label;
	}
	
	public IconItemData(int icon, String label, int menu) {
		this.icon = icon;
		this.label = label;
		this.menu = menu;
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

	/**
	 * @return the menu
	 */
	public int getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(int menu) {
		this.menu = menu;
	}
}