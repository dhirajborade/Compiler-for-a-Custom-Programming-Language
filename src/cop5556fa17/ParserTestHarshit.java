package cop5556fa17;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.AST.*;

import cop5556fa17.Parser.SyntaxException;

import static cop5556fa17.Scanner.Kind.*;

public class ParserTestHarshit {

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
	 * Simple test case with an empty program. This test expects an exception
	 * because all legal programs must have at least an identifier
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void testEmpty() throws LexicalException, SyntaxException {
		String input = ""; // The input is the empty string. Parsing should fail
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and
														// initialize it
		show(scanner); // Display the tokens
		Parser parser = new Parser(scanner); // Create a parser
		thrown.expect(SyntaxException.class);
		try {
			parser.parse(); // Parse the program, which should throw an exception
		} catch (SyntaxException e) {
			show(e); // catch the exception and show it
			throw e; // rethrow for Junit
		}
	}

	@Test
	public void testNameOnly() throws LexicalException, SyntaxException {
		String input = "prog"; // Legal program with only a name
		show(input); // display input
		Scanner scanner = new Scanner(input).scan(); // Create scanner and create token list
		show(scanner); // display the tokens
		Parser parser = new Parser(scanner); // create parser
		Program ast = parser.parse(); // parse program and get AST
		show(ast); // Display the AST
		assertEquals(ast.name, "prog"); // Check the name field in the Program object
		assertTrue(ast.decsAndStatements.isEmpty()); // Check the decsAndStatements list in the Program object. It
														// should be empty.
	}

	@Test
	public void testDec1() throws LexicalException, SyntaxException {
		String input = "prog int k;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		// This should have one Declaration_Variable object, which is at position 0 in
		// the decsAndStatements list
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(KW_int, dec.type.kind);
		assertEquals("k", dec.name);
		assertNull(dec.e);
	}

	@Test
	public void testcase1() throws SyntaxException, LexicalException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase2() throws SyntaxException, LexicalException {
		String input = "a bcd";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase3() throws SyntaxException, LexicalException {
		String input = "cart_x cart_y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase4() throws SyntaxException, LexicalException {
		String input = "prog int 2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase5() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,png] imageName <- imagepng"; // Error as there is not semicolon for ending
																		// the statement
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		// Parser.program();
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase6() throws SyntaxException, LexicalException {
		String input = "imageDeclaration image[\"abcd\"] "; // Should fail for image[""]
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase7() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,png] imageName <- imagepng; \n boolean ab=true;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program();
		show(ast);
		assertEquals("prog", ast.name);
		// First Declaration statement
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements.get(0);
		assertEquals(KW_image, dec.firstToken.kind);
		assertEquals("imageName", dec.name);
		Expression_Ident ei = (Expression_Ident) dec.xSize;
		assertEquals("filepng", ei.name);
		ei = (Expression_Ident) dec.ySize;
		assertEquals("png", ei.name);
		Source_Ident s = (Source_Ident) dec.source;
		assertEquals("imagepng", s.name);
		// Second Declaration statement
		Declaration_Variable dec2 = (Declaration_Variable) ast.decsAndStatements.get(1);
		assertEquals("ab", dec2.name);
		assertEquals(KW_boolean, dec2.firstToken.kind);
		Expression_BooleanLit ebi = (Expression_BooleanLit) dec2.e;
		assertEquals(true, ebi.value);
	}

	@Test
	public void testcase8() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,jpg] imageName;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program();
		show(ast);
		assertEquals("prog", ast.name);
		Declaration_Image dec1 = (Declaration_Image) ast.decsAndStatements.get(0);
		assertEquals(dec1.name, "imageName");
		Expression_Ident exi = (Expression_Ident) dec1.xSize;
		Expression_Ident eyi = (Expression_Ident) dec1.ySize;
		assertEquals(exi.name, "filepng");
		assertEquals(eyi.name, "jpg");
		assertNull(dec1.source);
	}

	@Test
	public void testcase10() throws SyntaxException, LexicalException {
		String input = "prog @expr k=12;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "prog");
		assertEquals(ast.decsAndStatements.size(), 0);
	}

	@Test
	public void testcase10parse() throws SyntaxException, LexicalException {
		String input = "prog @expr k=12;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.parse();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase11() throws SyntaxException, LexicalException {
		String input = "prog \"abcded\" boolean a=true;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "prog");
		assertEquals(ast.decsAndStatements.size(), 0);
	}

	@Test
	public void testcase11_parse() throws SyntaxException, LexicalException {
		String input = "prog \"abcded\" boolean a=true;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.parse();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase12() throws SyntaxException, LexicalException {
		String input = "isBoolean boolean ab=true; boolean cd==true; abcd=true ? return true: return false;"; // Should
																												// fail
																												// for
																												// ==
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase13() throws SyntaxException, LexicalException {
		String input = "isBoolean boolean ab=true; boolean cd==true; abcd=true ? return true: return false;"; // Should
																												// fail
																												// for =
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

}