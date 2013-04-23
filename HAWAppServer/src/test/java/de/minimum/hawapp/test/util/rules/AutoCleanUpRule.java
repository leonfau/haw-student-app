package de.minimum.hawapp.test.util.rules;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import de.minimum.hawapp.test.util.CleanUpHelper;



public class AutoCleanUpRule implements MethodRule{

	@Override
	public Statement apply(Statement base, FrameworkMethod method, Object target) {
		return new MyStatement(base);
	}
	
	private class MyStatement extends Statement {
		private Statement base;
		
		
		MyStatement(Statement base){
			this.base=base;
		}
		
		@Override
		public void evaluate() throws Throwable {
			try{
				CleanUpHelper.CLEANUPHELPER_INSTANCE.startAutoCleanUp();
				base.evaluate();	
			}finally{
				CleanUpHelper.CLEANUPHELPER_INSTANCE.cleanUpAndStop();
			}
			
			
		}
		
	}
	

}
