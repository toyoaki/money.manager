package com.simply.functional.mm.entity;

public class MoneyTransactionCategory {

    private int id;
    private String name;
    private String alias;
    private String description;
    private String fontColor;
    private String backgroundColor;

    public MoneyTransactionCategory() {
	super();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAlias() {
	return alias;
    }

    public void setAlias(String alias) {
	this.alias = alias;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getFontColor() {
	return fontColor;
    }

    public void setFontColor(String fontColor) {
	this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
	return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
	this.backgroundColor = backgroundColor;
    }

}
