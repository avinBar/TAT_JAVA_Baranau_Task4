package by.rdtc.library.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.rdtc.library.bean.Book;

public class Validator {

	private static final String TITLE = "[a-zA-Z0-9_]{4,45}";
	private static final String NAME = "[a-zA-Z-]{4,45}";
	private static final String LOGIN = "[a-zA-Z_0-9]{3,16}";
	private static final String PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,10}$";

	public static boolean validate(String... data) {
		for (String str : data) {
			if (null == str || str.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean validate(int... data) {
		for (int arg : data) {
			if (arg < 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateLogin(String login) {
		Matcher matcher;
		matcher = Pattern.compile(LOGIN).matcher(login);
		return matcher.matches();
	}

	public static boolean validatePassword(String password) {
		Matcher matcher;
		matcher = Pattern.compile(PASSWORD).matcher(password);
		return matcher.matches();
	}

	public static boolean validateTitle(String title) {
		Matcher matcher;
		matcher = Pattern.compile(TITLE).matcher(title);
		return matcher.matches();
	}

	public static boolean validateName(String name) {
		Matcher matcher;
		matcher = Pattern.compile(NAME).matcher(name);
		return matcher.matches();
	}

	public static boolean validateBook(Book book) {
		boolean isValid;
		isValid = (book != null) && validate(book.getTitle(), book.getAuthor()) 
				&& validateTitle(book.getTitle()) && validateName(book.getAuthor());
		return isValid;
	}

}
