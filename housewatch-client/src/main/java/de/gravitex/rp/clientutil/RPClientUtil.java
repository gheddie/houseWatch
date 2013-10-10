package de.gravitex.rp.clientutil;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RPClientUtil {

	@SuppressWarnings("unchecked")
	public static <T> T lookup(String moduleName, String beanName, Class<?> remoteClass) throws NamingException {
		final Context context = getInitialContext();
		String lookUp = "ejb:/"+moduleName+"/"+beanName+"!" + remoteClass.getName();
		System.out.println("looking up : "+lookUp+".");
		return (T) context.lookup(lookUp);
	}

	private static Context getInitialContext() throws NamingException {
		final Properties jndiProperties = new Properties();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		return context;
	}
}
