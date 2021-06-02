package org.eu5.learn_pisio.bookstore.rest;

import org.eu5.learn_pisio.bookstore.model.Book;
import org.eu5.learn_pisio.bookstore.model.Language;
import org.eu5.learn_pisio.bookstore.repository.BookRepository;
import org.eu5.learn_pisio.bookstore.util.IsbnGenerator;
import org.eu5.learn_pisio.bookstore.util.NumberGenerator;
import org.eu5.learn_pisio.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {
	
	@Test
	public void createBook(@ArquillianResource(BookEndpoint.class)WebTarget webTarget) {
		
		// Test counting books
		Response response = webTarget.path("count").request().get();
		assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		// Test find all
		response = webTarget.request(APPLICATION_JSON).get();
		assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
		
		// Creates a book
		Book book = new Book("isbn", "a  title", 12F, 123,
				Language.ENGLISH, new Date(), "http://blahblah", "description");
		response = webTarget.request(APPLICATION_JSON).post(Entity.entity(book, APPLICATION_JSON));
		assertEquals(CREATED.getStatusCode(), response.getStatus());
	}
	
	@Deployment(testable = false)
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(BookEndpoint.class)
				.addClass(Language.class)
				.addClass(BookRepository.class)
				.addClass(TextUtil.class)
				.addClass(NumberGenerator.class)
				.addClass(IsbnGenerator.class)
				.addClass(BookEndpoint.class)
				.addClass(JAXRSConfiguration.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("META-INF/test-persistence.xml","test-persistence.xml" );
	}
}
