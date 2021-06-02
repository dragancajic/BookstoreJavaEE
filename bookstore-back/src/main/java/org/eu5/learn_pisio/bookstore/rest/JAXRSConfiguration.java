package org.eu5.learn_pisio.bookstore.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// Everything under API ("api") is a JAX-RS endpoint
@ApplicationPath("api") // arbitrary string -- "api"
public class JAXRSConfiguration extends Application {
}
