package com.jdc.id3.model.decision;

public interface NodeAsChild extends Node{

	NodeAsParent parent();
	
	String condition();
}
