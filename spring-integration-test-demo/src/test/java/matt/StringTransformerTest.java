package matt;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringTransformerTest {

	@Test
	public void transformerTurnsStringToUppercase() {
		StringTransformer transformer = new StringTransformer();
		
		String input = "test string";
		String expected = "TEST STRING";
		String actual = transformer.transformStringToUppercase(input);
		assertEquals(expected, actual);
	}

}
