package de.minimum.hawapp.test.util;

import org.codehaus.cargo.container.ContainerType;
import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.configuration.ConfigurationType;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.deployable.WAR;
import org.codehaus.cargo.generic.DefaultContainerFactory;
import org.codehaus.cargo.generic.configuration.DefaultConfigurationFactory;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ServerStartRule implements TestRule{
	
	@Override
	public Statement apply(Statement arg0, Description arg1) {
		return new MyStatement(arg0);
	}
	
	private class MyStatement extends Statement{
		private Statement base;
		InstalledLocalContainer container;
		
		MyStatement(Statement base){
			this.base=base;
		}
		@Override
		public void evaluate() throws Throwable {
			startServer();
			base.evaluate();
			stopServer();
			
			
		}
		
		private void startServer(){

	        // (2) Create the Cargo Container instance wrapping our physical container
	        LocalConfiguration configuration = (LocalConfiguration) new DefaultConfigurationFactory().createConfiguration(
	            "tomcat6x", ContainerType.INSTALLED, ConfigurationType.STANDALONE);
	         container =
	            (InstalledLocalContainer) new DefaultContainerFactory().createContainer(
	                "tomcat6x", ContainerType.INSTALLED, configuration);
	         container.setHome(System.getenv().get("TOMCAT_HOME"));
	     // (3) Statically deploy some WAR (optional)
	     WAR deployable = new WAR("./target/server.war");
	     deployable.setContext("SERVER");
	     configuration.addDeployable(deployable);
//	     
//	        // (3) Statically deploy some WAR (optional)
	        configuration.addDeployable(new WAR("./target/server.war"));
//
//	        // (4) Start the container
	        container.start();
		}
		private void stopServer(){
			  container.stop();
		}
		
	}

}
