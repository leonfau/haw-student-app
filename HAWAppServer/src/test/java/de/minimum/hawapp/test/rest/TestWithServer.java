package de.minimum.hawapp.test.rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TestRule;

import de.minimum.hawapp.test.util.ServerStartRule;
@Ignore
public class TestWithServer {
	/*
	 * Die Enviroment Variable TOMCAT_HOME muss gesetzt sein!
	 */
	@ClassRule
	public static TestRule rule=new ServerStartRule();
	
	@Test
	public void test() throws IOException {
		URL  url=new URL ("http://localhost:8080/server/rest/testservice");
		InputStream input=url.openStream();
		int i=input.read();
		while(i!=-1){
			System.out.print((char)i);
			i=input.read();
		}
	}

}
