package org.eu5.learn_pisio.bookstore.rest;

import org.eu5.learn_pisio.bookstore.model.Book;
import org.eu5.learn_pisio.bookstore.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/books")
public class BookEndpoint {
	
	@Inject
	private BookRepository bookRepository;
	
	// TODO: Implement other methods. Make it to WORK! -> Драган Ћајић
	public Book getBook(Long id) {
		return bookRepository.find(id);
	}
	
	public Book createBook(Book book) {
		return bookRepository.create(book);
	}
	
	public void deleteBook(Long id) {
		bookRepository.delete(id);
	}
	
	@GET
	@Produces(APPLICATION_JSON)
	public Response getBooks() {
		List<Book> books = bookRepository.findAll();
		
		if (books.size() == 0) {
			return Response.noContent().build();
		}
		
		return Response.ok(books).build();
	}
	
	@GET
	@Path("/count")
	public Response countBooks() {
		Long numberOfBooks = bookRepository.countAll();
		
		if (numberOfBooks == 0) { // if database is empty
			return Response.noContent().build();
		}
		
		return Response.ok(numberOfBooks).build();
	}
}
