package com.andypemberton.examples.cloudbees.todo.web;

import org.junit.Test;

import com.saucelabs.selenium.client.factory.SeleniumFactory;
import com.thoughtworks.selenium.Selenium;

public class SeleniumFactoryTest {

	@Test
	public void test() throws InterruptedException {
		Selenium s = SeleniumFactory
				.create("sauce-ondemand:?os=Linux&browser=firefox&username=cb_cloudbees-sa&access-key=0ce882ef-06c4-4439-8b50-09027e4dd6d7",
						"https://m.yahoo.com/");
		s.start();
		s.open("/");
		System.out.println(s.getTitle());
		Thread.sleep(3000); // manually induced delay, or else the window closes
							// too fast
		s.stop();
	}

}
