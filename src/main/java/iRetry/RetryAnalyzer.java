package iRetry;

import java.util.concurrent.ConcurrentHashMap;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private  int retryCount = 0;
    private static final int maxRetryCount = 2; // 1 original + 1 retry
    private static final ConcurrentHashMap<String, Integer> attemptMap = new ConcurrentHashMap<>();

	  @Override
	    public boolean retry(ITestResult result) {
		  String key = result.getMethod().getMethodName() + result.getTestClass().getName();
	        attemptMap.put(key, retryCount);
		  System.out.println("Retrying " + result.getName() + " | Attempt: " + retryCount);
	        return retryCount++ < maxRetryCount;
	        
	    }
	  public static boolean isLastAttempt(ITestResult result) {
	        String key = result.getMethod().getMethodName() + result.getTestClass().getName();
	        Integer count = attemptMap.get(key);
	        return count == null || count >= maxRetryCount;
	    }
}
