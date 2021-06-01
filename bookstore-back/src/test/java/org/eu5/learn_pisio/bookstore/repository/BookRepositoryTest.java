package org.eu5.learn_pisio.bookstore.repository;

import org.eu5.learn_pisio.bookstore.model.Book;
import org.eu5.learn_pisio.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.lang.annotation.Documented;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {
	
	@Inject
	private BookRepository bookRepository;
	
	@Test(expected = Exception.class)
	public void findWithInvalidId() {
		bookRepository.find(null);
	}
	
	@Test(expected = Exception.class)
	public void createInvalidBook() {
		// Create a book with title null -- we don't want that! So, test it!!!
		Book book = new Book("isbn", null, 12F, 123,
				Language.ENGLISH, new Date(), "http://blahblah", "description");
		bookRepository.create(book);
	}
	
	@Test
	public void create() {
		// Test counting books
		assertEquals(Long.valueOf(0), bookRepository.countAll());
		assertEquals(0, bookRepository.findAll().size());

		// Create a book
		Book book = new Book("isbn", "a title", 12F, 123,
				Language.ENGLISH, new Date(), "http://blahblah", "description");
		book = bookRepository.create(book);
		Long bookId = book.getId();

		// Check created
		assertNotNull(bookId);

		// Find created book
		Book bookFind = bookRepository.find(bookId);

		// Check the found book
		assertEquals("a title", bookFind.getTitle());

		// Test counting books
		assertEquals(Long.valueOf(1), bookRepository.countAll());
		assertEquals(1, bookRepository.findAll().size());

		// Delete the book
		bookRepository.delete(bookId);

		// Test counting books
		assertEquals(Long.valueOf(0), bookRepository.countAll());
		assertEquals(0, bookRepository.findAll().size());
	}
	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(Book.class)
				.addClass(Language.class)
				.addClass(BookRepository.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
	}
}
