package com.ebizzone.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ByteUtilsTestCase.class
})
public class MyTestSuite extends TestSuite {
	
}
