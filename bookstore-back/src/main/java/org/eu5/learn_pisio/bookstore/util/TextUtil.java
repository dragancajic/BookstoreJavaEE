package org.eu5.learn_pisio.bookstore.util;

public class TextUtil {
	public String sanitize(String textToSanitize) {
		return textToSanitize.replaceAll("\\s+", " ");
	}
}
