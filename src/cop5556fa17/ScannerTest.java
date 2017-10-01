/**
* /**
* JUunit tests for the Scanner for the class project in COP5556 Programming Language Principles 
* at the University of Florida, Fall 2017.
* 
* This software is solely for the educational benefit of students 
* enrolled in the course during the Fall 2017 semester.  
* 
* This software, and any software derived from it,  may not be shared with others or posted to public web sites,
* either during the course or afterwards.
* 
*  @Beverly A. Sanders, 2017
*/

package cop5556fa17;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.Scanner.Token;

import static cop5556fa17.Scanner.Kind.*;

public class ScannerTest {

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
	 * Retrieves the next token and checks that it is an EOF token. Also checks that
	 * this was the last token.
	 *
	 * @param scanner
	 * @return the Token that was retrieved
	 */

	Token checkNextIsEOF(Scanner scanner) {
		Scanner.Token token = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF, token.kind);
		assertFalse(scanner.hasTokens());
		return token;
	}

	/**
	 * Retrieves the next token and checks that its kind, position, length, line,
	 * and position in line match the given parameters.
	 * 
	 * @param scanner
	 * @param kind
	 * @param pos
	 * @param length
	 * @param line
	 * @param pos_in_line
	 * @return the Token that was retrieved
	 */
	Token checkNext(Scanner scanner, Scanner.Kind kind, int pos, int length, int line, int pos_in_line) {
		Token t = scanner.nextToken();
		assertEquals(scanner.new Token(kind, pos, length, line, pos_in_line), t);
		return t;
	}

	/**
	 * Retrieves the next token and checks that its kind and length match the given
	 * parameters. The position, line, and position in line are ignored.
	 * 
	 * @param scanner
	 * @param kind
	 * @param length
	 * @return the Token that was retrieved
	 */
	Token check(Scanner scanner, Scanner.Kind kind, int length) {
		Token t = scanner.nextToken();
		assertEquals(kind, t.kind);
		assertEquals(length, t.length);
		return t;
	}

	/**
	 * Simple test case with a (legal) empty program
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void testEmpty() throws LexicalException {
		String input = ""; // The input is the empty string. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	/**
	 * Test illustrating how to put a new line in the input program and how to check
	 * content of tokens.
	 * 
	 * Because we are using a Java String literal for input, we use \n for the end
	 * of line character. (We should also be able to handle \n, \r, and \r\n
	 * properly.)
	 * 
	 * Note that if we were reading the input from a file, as we will want to do
	 * later, the end of line character would be inserted by the text editor.
	 * Showing the input will let you check your input is what you think it is.
	 * 
	 * @throws LexicalException
	 */
	@Test
	public void testSemi() throws LexicalException {
		String input = ";;\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 2, 1);
		checkNext(scanner, SEMI, 4, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCarriageReturn() throws LexicalException {
		String input = ";;\r;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 2, 1);
		checkNext(scanner, SEMI, 4, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testCarriageReturnLineFeed() throws LexicalException {
		String input = ";;\r\n;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 4, 1, 2, 1);
		checkNext(scanner, SEMI, 5, 1, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testTab() throws LexicalException {
		String input = ";;\t;;";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, SEMI, 3, 1, 1, 4);
		checkNext(scanner, SEMI, 4, 1, 1, 5);
		checkNextIsEOF(scanner);
	}

	/**
	 * This example shows how to test that your scanner is behaving when the input
	 * is illegal. In this case, we are giving it a String literal that is missing
	 * the closing ".
	 * 
	 * Note that the outer pair of quotation marks delineate the String literal in
	 * this test program that provides the input to our Scanner. The quotation mark
	 * that is actually included in the input must be escaped, \".
	 * 
	 * The example shows catching the exception that is thrown by the scanner,
	 * looking at it, and checking its contents before rethrowing it. If caught but
	 * not rethrown, then JUnit won't get the exception and the test will fail.
	 * 
	 * The test will work without putting the try-catch block around new
	 * Scanner(input).scan(); but then you won't be able to check or display the
	 * thrown exception.
	 * 
	 * @throws LexicalException
	 */
	// @Test
	public void failUnclosedStringLiteral() throws LexicalException {
		String input = "\" greetings  ";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(13, e.getPos());
			throw e;
		}
	}

	@Test
	public void testOperators() throws LexicalException {
		String input = "+*== = == =\n?"; // The input is string of operators. This is legal
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_PLUS, 0, 1, 1, 1);
		checkNext(scanner, OP_TIMES, 1, 1, 1, 2);
		checkNext(scanner, OP_EQ, 2, 2, 1, 3);
		checkNext(scanner, OP_ASSIGN, 5, 1, 1, 6);
		checkNext(scanner, OP_EQ, 7, 2, 1, 8);
		checkNext(scanner, OP_ASSIGN, 10, 1, 1, 11);
		checkNext(scanner, OP_Q, 12, 1, 2, 1);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testOpwithspace() throws LexicalException {
		String input = "   = ==";
		show(input);
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, OP_ASSIGN, 3, 1, 1, 4);
		checkNext(scanner, OP_EQ, 5, 2, 1, 6);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testident1() throws LexicalException {
		String input = "cos definition sin";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, KW_cos, 0, 3, 1, 1);
		checkNext(scanner, IDENTIFIER, 4, 10, 1, 5);
		checkNext(scanner, KW_sin, 15, 3, 1, 16);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testOpIdent() throws LexicalException {
		String input = "abssba=";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 6, 1, 1);
		checkNext(scanner, OP_ASSIGN, 6, 1, 1, 7);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testboolxequalstrue() throws LexicalException {
		String input = "boolean x = true";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, KW_boolean, 0, 7, 1, 1);
		checkNext(scanner, KW_x, 8, 1, 1, 9);
		checkNext(scanner, OP_ASSIGN, 10, 1, 1, 11);
		checkNext(scanner, BOOLEAN_LITERAL, 12, 4, 1, 13);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testDigitBooleanLiteral() throws LexicalException {
		String input = "01234 x = false";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, INTEGER_LITERAL, 0, 1, 1, 1);
		checkNext(scanner, INTEGER_LITERAL, 1, 4, 1, 2);
		checkNext(scanner, KW_x, 6, 1, 1, 7);
		checkNext(scanner, OP_ASSIGN, 8, 1, 1, 9);
		checkNext(scanner, BOOLEAN_LITERAL, 10, 5, 1, 11);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testGE() throws LexicalException {
		String input = "_abc >= ";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 4, 1, 1);
		checkNext(scanner, OP_GE, 5, 2, 1, 6);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiteral() throws LexicalException {
		String input = "\"Dhiraj\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 8, 1, 1);

		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiteralCR() throws LexicalException {
		String input = "\"abcd\refgh\"";
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
			show(input);
		} catch (LexicalException e) {
			show(e);
			assertEquals(5, e.getPos());
			throw e;
		}

	}

	@Test
	public void testStringLiteralTab() throws LexicalException {
		String input = "\"abcd\tefgh\"";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 11, 1, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiteralMultipleBackslash() throws LexicalException {
		String input = "\"abcd\\\"";
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
			show(input);
		} catch (LexicalException e) {
			show(e);
			assertEquals(7, e.getPos());
			throw e;
		}
	}

	@Test
	public void testStringLiteralCRLF() throws LexicalException {
		String input = "abcd\r\nefgh";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, IDENTIFIER, 0, 4, 1, 1);
		checkNext(scanner, IDENTIFIER, 6, 4, 2, 1);
		checkNextIsEOF(scanner);

	}

	@Test
	public void testWhiteSpaces() throws LexicalException {
		String input = "  ";
		Scanner scanner = new Scanner("  ").scan();
		checkNext(scanner, EOF, 2, 0, 1, 3);
		input = "\t";
		scanner = new Scanner(input).scan();
		checkNext(scanner, EOF, 1, 0, 1, 2);
		input = "\f";
		scanner = new Scanner(input).scan();
		checkNext(scanner, EOF, 1, 0, 1, 2);
	}

	@Test
	public void testComments() throws LexicalException {
		String input = "//comment";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNextIsEOF(scanner);
		input = "//comment \n";
		scanner = new Scanner("//comment \n").scan();
		show(input);
		show(scanner);
		checkNextIsEOF(scanner);
		input = "//comment \n\r";
		scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNextIsEOF(scanner);
		input = ";;//abcd\nmno";
		scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, SEMI, 0, 1, 1, 1);
		checkNext(scanner, SEMI, 1, 1, 1, 2);
		checkNext(scanner, IDENTIFIER, 9, 3, 2, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testSeparators() throws LexicalException {
		String input = "() [] ; ,";
		Scanner scanner = new Scanner(input).scan();
		checkNext(scanner, LPAREN, 0, 1, 1, 1);
		checkNext(scanner, RPAREN, 1, 1, 1, 2);
		checkNext(scanner, LSQUARE, 3, 1, 1, 4);
		checkNext(scanner, RSQUARE, 4, 1, 1, 5);
		checkNext(scanner, SEMI, 6, 1, 1, 7);
		checkNext(scanner, COMMA, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testIdentifier() throws LexicalException {
		String input = "var $var _var ___ a0123";
		Scanner scanner = new Scanner(input).scan();
		checkNext(scanner, IDENTIFIER, 0, 3, 1, 1);
		checkNext(scanner, IDENTIFIER, 4, 4, 1, 5);
		checkNext(scanner, IDENTIFIER, 9, 4, 1, 10);
		checkNext(scanner, IDENTIFIER, 14, 3, 1, 15);
		checkNext(scanner, IDENTIFIER, 18, 5, 1, 19);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testKeyWord() throws LexicalException {
		String input = "a A Z x y X Y r R boolean";
		Scanner scanner = new Scanner(input).scan();
		checkNext(scanner, KW_a, 0, 1, 1, 1);
		checkNext(scanner, KW_A, 2, 1, 1, 3);
		checkNext(scanner, KW_Z, 4, 1, 1, 5);
		checkNext(scanner, KW_x, 6, 1, 1, 7);
		checkNext(scanner, KW_y, 8, 1, 1, 9);
		checkNext(scanner, KW_X, 10, 1, 1, 11);
		checkNext(scanner, KW_Y, 12, 1, 1, 13);
		checkNext(scanner, KW_r, 14, 1, 1, 15);
		checkNext(scanner, KW_R, 16, 1, 1, 17);
		checkNext(scanner, KW_boolean, 18, 7, 1, 19);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringLiteralWithEscapeChars() throws LexicalException {
		String input = "\"abc\\\"\"";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, Kind.STRING_LITERAL, 0, 7, 1, 1);
	}

	@Test
	public void testIntegerLiteral() throws LexicalException {
		String input = "111 + 333 + 222";
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		checkNext(scanner, INTEGER_LITERAL, 0, 3, 1, 1);
		checkNext(scanner, OP_PLUS, 4, 1, 1, 5);
		checkNext(scanner, INTEGER_LITERAL, 6, 3, 1, 7);
		checkNext(scanner, OP_PLUS, 10, 1, 1, 11);
		checkNext(scanner, INTEGER_LITERAL, 12, 3, 1, 13);
		checkNextIsEOF(scanner); // Check that the only token is the EOF token.
	}

	@Test
	public void testTokensWithEscapewith() throws LexicalException {
		String input = "DEF_X(\"a\nb\");";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(8, e.getPos());
			throw e;
		}
	}

	@Test
	public void testStringLiteralIdentifier() throws LexicalException {
		String input = "\"abc\"def";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, STRING_LITERAL, 0, 5, 1, 1);
		checkNext(scanner, IDENTIFIER, 5, 3, 1, 6);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testEscapeWithIdentifier() throws LexicalException {
		String input = "foo\b=";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(3, e.getPos());
			throw e;
		}
	}

	@Test
	public void testIntegerLiteralRange() throws LexicalException {
		String input = "9999999999999999999";
		show(input);
		thrown.expect(LexicalException.class);
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(0, e.getPos());
			throw e;
		}
	}

	@Test
	public void testCommentIdentifier() throws LexicalException {
		String input = "Z//comment";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, KW_Z, 0, 1, 1, 1);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testTwoKeywords() throws LexicalException {
		String input = "boolean y";
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		checkNext(scanner, KW_boolean, 0, 7, 1, 1);
		checkNext(scanner, KW_y, 8, 1, 1, 9);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testIllegalBackslash() throws LexicalException {
		String input = "\"ab\\def\"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(4, e.getPos());
			throw e;
		}
	}

	@Test
	public void testLegalIdentifier() throws LexicalException {
		String input = "/*the*/";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, OP_DIV, 0, 1, 1, 1);
		checkNext(scanner, OP_TIMES, 1, 1, 1, 2);
		checkNext(scanner, IDENTIFIER, 2, 3, 1, 3);
		checkNext(scanner, OP_TIMES, 5, 1, 1, 6);
		checkNext(scanner, OP_DIV, 6, 1, 1, 7);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testIllegalEscapeStringLiteral() throws LexicalException {
		String input = "(\"\\a\")";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) {
			show(e);
			assertEquals(3, e.getPos());
			throw e;
		}
	}

	@Test
	public void testBooleanLiteral() throws LexicalException {
		String input = "true false \n truefalse";
		Scanner scanner = new Scanner(input).scan();
		show(input);
		show(scanner);
		checkNext(scanner, BOOLEAN_LITERAL, 0, 4, 1, 1);
		checkNext(scanner, BOOLEAN_LITERAL, 5, 5, 1, 6);
		checkNext(scanner, IDENTIFIER, 13, 9, 2, 2);
		checkNextIsEOF(scanner);
	}

	@Test
	public void testStringException() throws LexicalException {
		String input = "\"String has error because we have included \\ \"";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			Scanner scanner = new Scanner(input).scan();
			show(scanner);
		} catch (LexicalException e) { //
			show(e);
			assertEquals(44, e.getPos());
			throw e;
		}
	}

	@Test
	public void testLFInStringLiteral() throws LexicalException {
		String input = "This example , \"of newline \n is invalid. \" ";
		show(input);
		thrown.expect(LexicalException.class); // Tell JUnit to expect a LexicalException
		try {
			new Scanner(input).scan();
		} catch (LexicalException e) { //
			show(e);
			assertEquals(27, e.getPos());
			throw e;
		}
	}
}
