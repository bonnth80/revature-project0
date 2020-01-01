package com.ui.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ui.Menu;

public class MenuTest {
	@Test
	void testStringPadder() {
		assertEquals("Hello   ", Menu.padStringRight("Hello", 8));
		assertEquals("Hello...", Menu.padStringRight("Hello, World!", 8));
	}
}
