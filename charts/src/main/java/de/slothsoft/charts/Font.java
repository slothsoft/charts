package de.slothsoft.charts;

import de.slothsoft.charts.common.Title;

/**
 * The fonts that can be used with the {@link GraphicContext}. The properties this enum
 * has are hints that GUI frameworks can ignore.
 *
 * @author Stef Schulz
 * @since 0.1.0
 */

public enum Font {
	/** The normal font to display everything. */
	NORMAL(10, false),

	/** The font to display titles like the {@link Title}. */
	TITLE(14, true),

	;

	private final int size;
	private final boolean bold;

	private Font(int size, boolean bold) {
		this.size = size;
		this.bold = bold;
	}

	/**
	 * Returns the size hint.
	 * 
	 * @return the size hint
	 */

	public int getSize() {
		return this.size;
	}

	/**
	 * Returns the bold hint.
	 * 
	 * @return the bold hint
	 */

	public boolean isBold() {
		return this.bold;
	}
}
