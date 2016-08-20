package com.ebizzone.sql;

public @interface SQLField {
	String name() default "*";
}
