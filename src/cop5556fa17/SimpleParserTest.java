package cop5556fa17;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.SimpleParser.SyntaxException;

public class SimpleParserTest {

	// set Junit to be able to catch exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// To make it easy to print objects and turn this output on and off
	static final boolean doPrint = true;

	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}

	/**
	 * Simple test case with an empty program. This test expects an SyntaxException
	 * because all legal programs must have at least an identifier
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void testEmpty() throws LexicalException, SyntaxException {
		String input = ""; // The input is the empty string. This is not legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		SimpleParser parser = new SimpleParser(scanner); // Create a parser
		thrown.expect(SyntaxException.class);
		try {
			parser.parse(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	/**
	 * Another example. This is a legal program and should pass when your parser is
	 * implemented.
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */

	@Test
	public void testDec1() throws LexicalException, SyntaxException {
		String input = "prog int k;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		SimpleParser parser = new SimpleParser(scanner); //
		parser.parse();
	}

	/**
	 * This example invokes the method for expression directly. Effectively, we are
	 * viewing Expression as the start symbol of a sub-language.
	 * 
	 * Although a compiler will always call the parse() method, invoking others is
	 * useful to support incremental development. We will only invoke expression
	 * directly, but following this example with others is recommended.
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void expression1() throws SyntaxException, LexicalException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression(); // Call expression directly.
	}

	@Test
	public void dhirajTestCase0() throws SyntaxException, LexicalException {
		String input = "var [x,y]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression(); // Call expression directly.
	}

	@Test
	public void dhirajTestCase1() throws LexicalException, SyntaxException {
		String input = "abc";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void dhirajTestCase2() throws LexicalException, SyntaxException {
		String input = "  (3,5) ";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.parse(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void dhirajTestCase3() throws LexicalException, SyntaxException {
		String input = "  (3,) ";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase4() throws LexicalException, SyntaxException {
		String input = "prog0 int index = 0 ;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.parse();
	}

	@Test
	public void dhirajTestCase5() throws LexicalException, SyntaxException {
		String input = "hello;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase6() throws LexicalException, SyntaxException {
		String input = "hello world = 5 - 8;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.parse();
	}

	@Test
	public void dhirajTestCase7() throws LexicalException, SyntaxException {
		String input = "start home = ( cos [90, 10] );";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.parse();
	}

	@Test
	public void dhirajTestCase8() throws LexicalException, SyntaxException {
		String input = "legal url myUrl = page; boolean real = atan [10, 55];";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.parse();
	}

	@Test
	public void dhirajTestCase9() throws LexicalException, SyntaxException {
		String input = "legal url page, boolean real";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase10() throws LexicalException, SyntaxException {
		String input = "";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase11() throws LexicalException, SyntaxException {
		String input = " This is testcase number 7";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase12() throws LexicalException, SyntaxException {
		String input = "begin file fileName = \"inputFile\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.parse();
	}

	@Test
	public void dhirajTestCase13() throws LexicalException, SyntaxException {
		String input = "begin file f1 (;)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase14() throws LexicalException, SyntaxException {
		String input = "starting file f1, boolean boo ([])";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

	@Test
	public void dhirajTestCase15() throws LexicalException, SyntaxException {
		String input = "boolean bool = true";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		parser.parse();
	}

}
