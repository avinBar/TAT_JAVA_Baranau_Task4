package by.rdtc.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.rdtc.library.bean.Book;
import by.rdtc.library.dao.connection.SQLDBWorker;
import by.rdtc.library.dao.exception.DAOException;
import by.rdtc.library.dao.iface.BookDAO;

public class SQLBookDAO implements BookDAO {
	private static final String ADD_BOOK = "INSERT INTO book (`b_title`, `b_author`,`b_status`) VALUES(?,?,?)";
	private static final String UPDATE_BOOK = "UPDATE book SET b_title=?, b_author=? WHERE b_id=?";
	private static final String SELECT_BOOK = "SELECT b_id, b_title, b_author, b_status FROM book WHERE b_id=?";
	private static final String SHOW_ALL_BOOKS = "SELECT b_id, b_title, b_author, b_status FROM book";
	private static final String DELETE_BOOK = "UPDATE book SET b_status=? WHERE b_id=? AND b_status='on_shelf'";

	private static final String ON_SHELF = "on_shelf";
	private static final String DELETED = "deleted";
	private static final int ZERO_AFFECTED_ROWS = 0;

	@Override
	public void addBook(Book book) throws DAOException {
		Connection connect = null;
		PreparedStatement state = null;
		
		try {
			connect = SQLDBWorker.getInstance().getConnection();
			state = connect.prepareStatement(ADD_BOOK);
			state.setString(1, book.getTitle());
			state.setString(2, book.getAuthor());
			state.setString(3, ON_SHELF);
			int update = state.executeUpdate();
			if (update > ZERO_AFFECTED_ROWS) {
				return;
			}
			throw new DAOException("Wrong book data");
			
		} catch (SQLException e) {
			throw new DAOException("Add book sql error", e);
		}
	}

	@Override
	public Book getBookById(int idBook) throws DAOException {
		Connection connection = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		try {
			connection = SQLDBWorker.getInstance().getConnection();
			state = connection.prepareStatement(SELECT_BOOK);
			state.setInt(1, idBook);
			rs = state.executeQuery();
			if (!rs.next()) {
				throw new DAOException("No book matching query");
			}
			Book book = new Book();
			book.setId(rs.getInt("b_id"));
			book.setTitle(rs.getString("b_title"));
			book.setAuthor(rs.getString("b_author"));
			book.setStatus(rs.getString("b_status"));
			return book;

		} catch (SQLException e) {
			throw new DAOException("Get book sql error", e);
		}finally {
			SQLDBWorker.getInstance().closeConnection(connection, state, rs);
		}
	}

	@Override
	public void updateBook(Book book) throws DAOException {
		Connection connection = null;
		PreparedStatement state = null;
		
		try {
			connection = SQLDBWorker.getInstance().getConnection();
			state = connection.prepareStatement(UPDATE_BOOK);
			state.setString(1, book.getTitle());
			state.setString(2, book.getAuthor());
			state.setInt(3, book.getId());
			int update = state.executeUpdate();
			if (update > ZERO_AFFECTED_ROWS) {
				return;
			}
			throw new DAOException("Wrong book data");
			
		} catch (SQLException e) {
			throw new DAOException("Add book sql error", e);
		}finally {
			SQLDBWorker.getInstance().closeConnection(connection, state);
		}
	}

	@Override
	public void deleteBook(int idBook) throws DAOException {
		Connection connection = null;
		PreparedStatement state = null;
		
		try {
			connection = SQLDBWorker.getInstance().getConnection();
			state = connection.prepareStatement(DELETE_BOOK);
			state.setString(1, DELETED);
			state.setInt(2, idBook);
			int update = state.executeUpdate();
			if (update > ZERO_AFFECTED_ROWS) {
				return;
			}
			throw new DAOException("Wrong book data");
			
		} catch (SQLException e) {
			throw new DAOException("Delete book sql error");
		}finally {
			SQLDBWorker.getInstance().closeConnection(connection, state);
		}
	}

	@Override
	public List<Book> showAllBooks() throws DAOException {
		Connection connection = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		
		try {
			connection = SQLDBWorker.getInstance().getConnection();
			state = connection.prepareStatement(SHOW_ALL_BOOKS);
			rs = state.executeQuery();
			List<Book> books = new ArrayList<>();
			Book book;
			while (rs.next()) {
				book = new Book();
				book.setId(rs.getInt("b_id"));
				book.setTitle(rs.getString("b_title"));
				book.setAuthor(rs.getString("b_author"));
				book.setStatus(rs.getString("b_status"));
				books.add(book);
			}
			return books;
			
		} catch (SQLException e) {
			throw new DAOException("Get list of book sql error");
		}finally {
			SQLDBWorker.getInstance().closeConnection(connection, state, rs);
		}
	}
}
