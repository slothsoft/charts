package de.slothsoft.charts.swing;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseEventBuilder {

	private static int nextId = 31415;

	private final Component source;

	int modifiers;
	int x;
	int y;
	int clickCount;
	boolean popupTrigger;
	int button;
	int scrollType;
	int scrollAmount;
	int wheelRotation;

	public MouseEventBuilder(Component source, int x, int y) {
		this.source = source;
		this.x = x;
		this.y = y;
	}

	public MouseEvent build() {
		return new MouseEvent(this.source, nextId++, System.currentTimeMillis(), this.modifiers, this.x, this.y,
				this.clickCount, this.popupTrigger, this.button);
	}

	public MouseWheelEvent buildForWheel() {
		return new MouseWheelEvent(this.source, nextId++, System.currentTimeMillis(), this.modifiers, this.x, this.y,
				this.clickCount, this.popupTrigger, this.scrollType, this.scrollAmount, this.wheelRotation);
	}

	public int getButton() {
		return this.button;
	}

	public MouseEventBuilder button(int newButton) {
		setButton(newButton);
		return this;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public int getClickCount() {
		return this.clickCount;
	}

	public MouseEventBuilder clickCount(int newClickCount) {
		setClickCount(newClickCount);
		return this;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public int getModifiers() {
		return this.modifiers;
	}

	public MouseEventBuilder modifiers(int newModifiers) {
		setModifiers(newModifiers);
		return this;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public boolean isPopupTrigger() {
		return this.popupTrigger;
	}

	public MouseEventBuilder popupTrigger(boolean newPopupTrigger) {
		setPopupTrigger(newPopupTrigger);
		return this;
	}

	public void setPopupTrigger(boolean popupTrigger) {
		this.popupTrigger = popupTrigger;
	}

	public int getX() {
		return this.x;
	}

	public MouseEventBuilder x(int newX) {
		setX(newX);
		return this;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public MouseEventBuilder y(int newY) {
		setY(newY);
		return this;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScrollAmount() {
		return this.scrollAmount;
	}

	public MouseEventBuilder scrollAmount(int newScrollAmount) {
		setScrollAmount(newScrollAmount);
		return this;
	}

	public void setScrollAmount(int scrollAmount) {
		this.scrollAmount = scrollAmount;
	}

	public int getScrollType() {
		return this.scrollType;
	}

	public MouseEventBuilder scrollType(int newScrollType) {
		setScrollType(newScrollType);
		return this;
	}

	public void setScrollType(int scrollType) {
		this.scrollType = scrollType;
	}

	public int getWheelRotation() {
		return this.wheelRotation;
	}

	public MouseEventBuilder wheelRotation(int newWheelRotation) {
		setWheelRotation(newWheelRotation);
		return this;
	}

	public void setWheelRotation(int wheelRotation) {
		this.wheelRotation = wheelRotation;
	}

}
