package by.rdtc.library.controller.impl.admin;

import java.util.Map;

import org.apache.log4j.Logger;

import by.rdtc.library.bean.Book;
import by.rdtc.library.controller.command.Command;
import by.rdtc.library.service.ServiceFactory;
import by.rdtc.library.service.exception.ServiceException;
import by.rdtc.library.service.iface.BookService;

public class AddBook implements Command{
	private static final String TITLE = "title";
	private static final String AUTHOR = "author";
	private static final int PARAMS_NUMBER = 2;
	
	private static final Logger log = Logger.getLogger(AddBook.class);
	
	@Override
	public String execute(Map<String,String> params) {
		String title=null;
		String author=null;
		
		String response=null;
		
		if(params.size()!=PARAMS_NUMBER){
			response="Wrong number of parameters";
			return response;
		}
		
		ServiceFactory serviceFactory=ServiceFactory.getInstance();
		BookService bookService=serviceFactory.getBookService();
		Book book;
		try {
			title=params.get(TITLE);
			author=params.get(AUTHOR);
			book=new Book(title,author);
			bookService.addBook(book);
			response="Book is added";
		} catch (ServiceException e) {
			log.error(e);
			response="Error during add book procedure";
		}
		return response;
	}
}
