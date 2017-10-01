package cop5556fa17;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.SimpleParser.SyntaxException;
import cop5556fa17.Scanner.LexicalException;

public class SimpleParserTestExtra {

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
		SimpleParser SimpleParser = new SimpleParser(scanner); // Create a SimpleParser
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.parse(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	/**
	 * Another example. This is a legal program and should pass when your
	 * SimpleParser is implemented.
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
		SimpleParser SimpleParser = new SimpleParser(scanner); //
		SimpleParser.parse();
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
	public void testcase1() throws SyntaxException, LexicalException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		// SimpleParser.program();
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program();
	}

	@Test
	public void testcase8() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,jpg] imageName;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program();
	}

	@Test
	public void testcase9() throws SyntaxException, LexicalException {
		String input = "prog image imageName;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program();
	}

	@Test
	public void testcase10() throws SyntaxException, LexicalException {
		String input = "prog @expr k=12;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.parse(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.parse(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
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
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase14() throws SyntaxException, LexicalException {
		String input = "isUrl url filepng=\"abcd\"; \n hello file world = @expr=12; url awesome=@expr; \n url filepng=abcdefg";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase15() throws SyntaxException, LexicalException {
		String input = "isUrl url filepng=\"abcd\" \n @expr=12; url awesome=@expr; \n url filepng=abcdefg"; // Should
																											// fail for
																											// ; in line
																											// one
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase17() throws SyntaxException, LexicalException {
		String input = "isFile file filepng=\"abcd\" \n @expr=12; url filepng=@expr; \n url filepng=abcdefg"; // Should
																												// fail
																												// for ;
																												// in
																												// line
																												// one
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase18() throws SyntaxException, LexicalException {
		String input = "isurl url urlname;"; // Should fail for url as url can only be initalised
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase19() throws SyntaxException, LexicalException {
		String input = "declaration int xyz;\n boolean zya;\n image imagename;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program(); // Parse the program
	}

	@Test
	public void testcase20() throws SyntaxException, LexicalException {
		String input = "imageProgram image imageName;" + "\n imageName->abcdpng; " + "\n imageName -> SCREEN; "
				+ "\n imageName <- \"awesome\";" + "\n imageName <- @express; \n" + "\n imageName <- abcdpng;"; // Image
																												// related
																												// Test
																												// cases
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program(); // Parse the program
	}

	@Test
	public void testcase21() throws SyntaxException, LexicalException {
		String input = "assign int abc=123456;\n" + "abc[[x,y]]=123456;\n" + "abc[[r,A]]=123244;\n";// Assignment
																									// statement
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.program(); // Parse the program
	}

	@Test
	public void testcase22() throws SyntaxException, LexicalException {
		String input = "assign int abc=123456;\n" + "abc[[x]]=123456;\n" + "abc[[r,A]]=123244;\n"; // Error
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase23() throws SyntaxException, LexicalException {
		String input = "assign int abc=123456;\n" + "abc[[x,y]]=123456;\n" + "abc[[A]]=123244;\n";// Error
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.program(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	// Function Name Testcases
	@Test
	public void testcase24() throws SyntaxException, LexicalException {
		String input = " sin \n cos \n atan \n abs \n cart_x \n cart_y \n polar_a \n polar_r\n";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.functionName(); // Parse the program
	}

	// LhsSelector ::= LSQUARE ( XySelector | RaSelector ) RSQUARE

	@Test
	public void testcase25() throws SyntaxException, LexicalException {
		String input = "[x,y] \n [r,A] \n []";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.lhsSelector(); // Parse the program
	}

	@Test
	public void testcase26() throws SyntaxException, LexicalException {
		String input = "[x,]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.lhsSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}

	}

	@Test
	public void testcase27() throws SyntaxException, LexicalException {
		String input = "[,A]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.lhsSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}

	}

	// XySelector ::= KW_x COMMA KW_y
	@Test
	public void testcase28() throws SyntaxException, LexicalException {
		String input = "x,y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.xySelector();
	}

	@Test
	public void testcase29() throws SyntaxException, LexicalException {
		String input = "x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.lhsSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	// RaSelector ::= KW_r COMMA KW_A
	@Test
	public void testcase30() throws SyntaxException, LexicalException {
		String input = "r,A";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.raSelector();
	}

	@Test
	public void testcase31() throws SyntaxException, LexicalException {
		String input = ",A";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.raSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase32() throws SyntaxException, LexicalException {
		String input = "r,";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.raSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	// Expression
	@Test
	public void testcase33() throws SyntaxException, LexicalException {
		String input = "x y r a X Y Z A R DEF_X DEF_Y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.raSelector(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase34() throws SyntaxException, LexicalException {
		String input = "5+3*4+5%3";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression(); // Parse the program
	}

	/**
	 * UnaryExpression ::= OP_PLUS UnaryExpression | OP_MINUS UnaryExpression |
	 * UnaryExpressionNotPlusMinus
	 * 
	 * UnaryExpressionNotPlusMinus ::= OP_EXCL UnaryExpression | Primary |
	 * IdentOrPixelSelectorExpression | KW_x | KW_y | KW_r | KW_a | KW_X | KW_Y |
	 * KW_Z | KW_A | KW_R | KW_DEF_X | KW_DEF_Y
	 * 
	 * Primary ::= INTEGER_LITERAL | LPAREN Expression RPAREN | FunctionApplication
	 * | BOOLEAN_LITERAL
	 * 
	 * IdentOrPixelSelectorExpression::= IDENTIFIER LSQUARE Selector RSQUARE |
	 * IDENTIFIER
	 * 
	 * FunctionApplication ::= FunctionName LPAREN Expression RPAREN | FunctionName
	 * LSQUARE Selector RSQUARE
	 * 
	 * FunctionName ::= KW_sin | KW_cos | KW_atan | KW_abs | KW_cart_x | KW_cart_y |
	 * KW_polar_a | KW_polar_r
	 * 
	 * Selector ::= Expression COMMA Expression
	 * 
	 */

	@Test
	public void testcase35() throws SyntaxException, LexicalException {
		String input = "x+y+y+r+X-Y-Z-A+R+DEF_X+DEF_Y+!(5+6+(true+false))\n" + " abcd[(x+tyxzsd),(12+123+45)] \n"
				+ " sin(x+y) cos(x+y) atan(x+y) abs(x+y) cart_x(x+y) cart_y(x+y) polar_a(x+y) polar_r(r+a)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression(); // Parse the program
	}

	@Test
	public void testcase36() throws SyntaxException, LexicalException {
		String input = "r = IdentOrPixelSelectorExpression[5,6]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression(); // Parse the program
	}

	@Test
	public void testcase37() throws SyntaxException, LexicalException {
		String input = "sin(x)+cos(x)-atan(x)+cart_x(x)+cart_y(y)+polar_a(a)+polar_r(a)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression(); // Parse the program
	}

	@Test
	public void testcase38() throws SyntaxException, LexicalException {
		String input = "sin(x)+cos(x)-atan(x)+cart_x(x)+cart_y(y)+polar_a(a)+polar_r(a)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression(); // Parse the program
	}

	@Test
	public void testcase39() throws SyntaxException, LexicalException {
		String input = "sin[(x+y),(x+z)]+cos[(x+y),(x+z)]+polar_r[vyz+xx,z+xx]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	/**
	 * OrExpression ::= AndExpression ( OP_OR AndExpression)*
	 * 
	 * AndExpression ::= EqExpression ( OP_AND EqExpression )*
	 * 
	 * EqExpression ::= RelExpression ( (OP_EQ | OP_NEQ ) RelExpression )*
	 * 
	 * RelExpression ::= AddExpression ( ( OP_LT | OP_GT | OP_LE | OP_GE )
	 * AddExpression)*
	 * 
	 * AddExpression ::= MultExpression ( (OP_PLUS | OP_MINUS ) MultExpression )*
	 * 
	 * MultExpression := UnaryExpression ( ( OP_TIMES | OP_DIV | OP_MOD )
	 * UnaryExpression )*
	 * 
	 * UnaryExpression ::= OP_PLUS UnaryExpression | OP_MINUS UnaryExpression |
	 * UnaryExpressionNotPlusMinus
	 * 
	 * UnaryExpressionNotPlusMinus ::= OP_EXCL UnaryExpression | Primary |
	 * IdentOrPixelSelectorExpression | KW_x | KW_y | KW_r | KW_a | KW_X | KW_Y |
	 * KW_Z | KW_A | KW_R | KW_DEF_X | KW_DEF_Y
	 * 
	 * Primary ::= INTEGER_LITERAL | LPAREN Expression RPAREN | FunctionApplication
	 * | BOOLEAN_LITERAL
	 * 
	 * IdentOrPixelSelectorExpression::= IDENTIFIER LSQUARE Selector RSQUARE |
	 * IDENTIFIER
	 * 
	 * FunctionApplication ::= FunctionName LPAREN Expression RPAREN | FunctionName
	 * LSQUARE Selector RSQUARE
	 * 
	 * FunctionName ::= KW_sin | KW_cos | KW_atan | KW_abs | KW_cart_x | KW_cart_y |
	 * KW_polar_a | KW_polar_r
	 * 
	 * Selector ::= Expression COMMA Expression
	 * 
	 */

	@Test
	public void testcase40() throws SyntaxException, LexicalException {
		String input = "y*Y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase41() throws SyntaxException, LexicalException {
		String input = "y*+"; // Should through an error
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.expression(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testcase42() throws SyntaxException, LexicalException {
		String input = "y/(x+Y*Y)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase43() throws SyntaxException, LexicalException {
		String input = "y/(x+Y*Y-sin(x+y)*cos(x+y)%2)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase44() throws SyntaxException, LexicalException {
		String input = "y/(x+Y*Y-sin(x+y)*cos(x+y)%2)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase45() throws SyntaxException, LexicalException {
		String input = "5+5-(x+y)*2%2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase46() throws SyntaxException, LexicalException {
		String input = "(5+10)>(6*2+4+sin(x)-atan(y)-x/y/z)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase47() throws SyntaxException, LexicalException {
		String input = "(5+10)<(6/2/3/4*2)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase48() throws SyntaxException, LexicalException {
		String input = "(6*2/23/4*22*sin(x))<=(abs(6*2*12)+cart_x[x,y]+cart_y[(6/23),(7/23)])+polar_a[6/2/2,2/3/4]+polar_r(z)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	public void testcase49() throws SyntaxException, LexicalException {
		String input = "(6*2/23/4*22*sin(x))<=(abs(6*2*12)+cart_x[x,y]+cart_y[(6/23),(7/23)])+polar_a[6/2/2,2/3/4]+polar_r(z,y,x))"; // Should
																																		// fail
																																		// as
																																		// ()
																																		// can
																																		// hold
																																		// only
																																		// expression
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.expression(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.sourceSinkType()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void programtest1() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input2 = { "@" + EXPinput, "\"STRING_LITERAL\"",
				"d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a" };
		String[] input = { "imgk boolean abc = " + EXPinput + ";int def$1=" + EXPinput + ";int gg;" + "\nboolean gg;"
				+ "\nfile abc = @" + EXPinput + ";url def$1=\"http://google.com/images\"" + ";file abc = @" + EXPinput
				+ "\n;image[" + EXPinput + "," + EXPinput + "] abc;" + "\nimage[" + EXPinput + "," + EXPinput + "] abc;"
				+ "\nimage abc;" + "\nimage[" + EXPinput + "," + EXPinput + "]abc<-@" + EXPinput + ";" + "img1->"
				+ "output;" + "\nimg1->" + "SCREEN;" + "\nimg1<-" + input2[0] + ";\nimg1<-" + input2[1] + ";img1="
				+ EXPinput + ";img1=" + input2[2] + ";\nimg1" + "[[x,y]]=" + EXPinput + ";", "imgk boolean abc;" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			// thrown.expect(SyntaxException.class);
			try {
				SimpleParser.program(); // Call expression directly.
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.sourceSinkType()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void declarationTest1() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "boolean abc = " + EXPinput, "int def$1=" + EXPinput, "int gg", "boolean gg",
				"file abc = @" + EXPinput, "url def$1=\"http://google.com/images\"", "file abc = @" + EXPinput,
				"image[" + EXPinput + "," + EXPinput + "] abc", "image[" + EXPinput + "," + EXPinput + "] abc",
				"image abc", "image[" + EXPinput + "," + EXPinput + "] abc<-@" + EXPinput,
				",int def$1=\"http://google.com/images\"" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			thrown.expect(SyntaxException.class);
			try {
				SimpleParser.declaration();

			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.sourceSinkType()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void variableDeclarationTest1() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "boolean abc = " + EXPinput, "int def$1=" + EXPinput, "int gg", "boolean gg",
				"int def$1=\"http://google.com/images\"" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			thrown.expect(SyntaxException.class);
			try {
				SimpleParser.variableDeclaration();

			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.sourceSinkType()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void sourceSinkDeclaration1() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "file abc = @" + EXPinput, "url def$1=\"http://google.com/images\"",
				"file abc = @" + EXPinput, "urld def$1=\"http://google.com/images\"" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			thrown.expect(SyntaxException.class);
			try {
				SimpleParser.sourceSinkDeclaration();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.sourceSinkType()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void sourceSinkType1() throws LexicalException, SyntaxException {
		String[] input = { "url", "file" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.sourceSinkType();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	@Test
	public void sourceSinkType2() throws LexicalException, SyntaxException {
		String[] input = { "URL", "File", "Url", "FILE" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.statement();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());// throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.imageDeclaration()
	 * 
	 * @throws LexicalException
	 * @throws SyntaxException
	 */
	@Test
	public void imageDeclarationTest1() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "image[" + EXPinput + "," + EXPinput + "] abc",
				"image[" + EXPinput + "," + EXPinput + "] abc", "image abc",
				"image[" + EXPinput + "," + EXPinput + "] abc<-@" + EXPinput };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.imageDeclaration();

			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	@Test
	public void imageDeclarationTest2() throws LexicalException, SyntaxException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "image[" + EXPinput + "," + EXPinput + "] abc",
				"image[" + EXPinput + "," + EXPinput + "] abc", "image abc",
				"image[" + EXPinput + "," + EXPinput + "] abc<-" + EXPinput };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			thrown.expect(SyntaxException.class);
			try {
				SimpleParser.imageDeclaration();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.source()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void statementTest1() throws SyntaxException, LexicalException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input2 = { "@" + EXPinput, "\"STRING_LITERAL\"",
				"d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a" };
		String[] input = { "img1->" + "output", "img1->" + "SCREEN", "img1<-" + input2[0], "img1<-" + input2[1],
				"img1=" + EXPinput, "img1=" + input2[2], "img1" + "[[x,y]]=" + EXPinput };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.statement();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	@Test
	public void statementTest2() throws SyntaxException, LexicalException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input2 = { "@" + EXPinput, "\"STRING_LITERAL\"",
				"d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a" };
		String[] input = { "img1->" + "output", "img1->" + "SCREEN", "img1<-" + input2[0], "img1<-" + input2[1],
				"img1=" + EXPinput, "img1=" + input2[2], "img1," + "[[x,y]]=" + EXPinput };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			thrown.expect(SyntaxException.class);
			try {
				SimpleParser.statement();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.source()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void sourceTest1() throws SyntaxException, LexicalException {
		String k = "2*2+5+x!=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "@" + EXPinput, "\"STRING_LITERAL\"",
				"d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.source();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.assignmentStatement()
	 */
	@Test
	public void assignmentStatementTest1() throws SyntaxException, LexicalException {
		String k = "2*2+5+x>2*2+5+x=,=d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a>=2*2+5+x!=2*2+5+x<=2*2+5+x|2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x";
		String EXPinput = k + "?" + k + ":" + k;
		String[] input = { "AKSHAY=" + EXPinput, "akshay[[x,y]]=" + EXPinput, "kk[[r,A]]=" + EXPinput,
				"m[[r,A]]=" + EXPinput };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.assignmentStatement();
			} catch (SyntaxException e) {
				System.out.println(e.getMessage());
				throw e;
			}
		}
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
		String input = "2*2+5+x>2*2+5+x==d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a>=2*2+5+x!=2*2+5+x<=2*2+5+x|2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.expression();
	}

	@Test
	/**
	 * PLEASE DELETE THIS TEST CASE AFTER TESTING YOUR PROGRAM
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 * @author AkshaySharma
	 */
	public void expressionTest3() throws SyntaxException, LexicalException {
		String k = "2*2+5+x>2*2+5+x==d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a>=2*2+5+x!=2*2+5+x<=2*2+5+x|2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x";
		String[] input = { k, k + "?" + k + ":" + k };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.orExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Do NOT SHARE WITH ANYONE or I am going to kick your ass when I find out.
	 * cheers, Akshay Sharma these test cases invoke SimpleParser.orExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void orExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = {
				"2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x|2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.orExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.andExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void andExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.andExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.eqExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void eqExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x", "2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.eqExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.addExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void relExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "2*2+5+x>2*2+5+x", "2*2+5+x<2*2+5+x", "2*2+5+x>=2*2+5+x", "2*2+5+x<=2*2+5+x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.relExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void relExpressionTest2() throws SyntaxException, LexicalException {
		String[] input = { "2*2+>5+x" };
		thrown.expect(SyntaxException.class);
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.relExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.addExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void addExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "2*2+5-x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.addExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void addExpressionTest2() throws SyntaxException, LexicalException {
		String[] input = { "2*2+>5+x" };
		thrown.expect(SyntaxException.class);
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.addExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.multExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void multExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "++--!!variable[k,l]*+x/-y%!r" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.multExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void multExpressionTest2() throws SyntaxException, LexicalException {
		String[] input = { "++,--!!variable[k,l]*+x/-y%!r" };
		thrown.expect(SyntaxException.class);
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.multExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.unaryExpression()
	 * 
	 * @throws SyntaxException
	 * @throws LexicalException
	 */
	@Test
	public void unaryExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "+x", "-y", "!r", "+a", "+X", "!Y", "!Z", "--A", "++++123"/*
																						 * integer literal
																						 */, "(ij)"
				/*
				 * dummy expression
				 */, "cos(2)", "atan(x)"
				/*
				 * Function Applications
				 */, "++--!!chakka[k,l]" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.unaryExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void unaryExpressionTest2() throws SyntaxException, LexicalException {
		String[] input = { "++--!!chakka[k,l,]" };
		thrown.expect(SyntaxException.class);
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.unaryExpression();
			}
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * these test cases invoke SimpleParser.primary()
	 */
	@Test
	public void primaryTest1() throws SyntaxException, LexicalException {
		String[] input = { "123"/*
								 * integer literal
								 */, "(ij)"
				/*
				 * dummy expression
				 */, "cos(2)", "atan(x)"
				/*
				 * Function Applications
				 */ };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			SimpleParser.primary();
		}
	}

	@Test
	public void primaryTest2() throws SyntaxException, LexicalException {
		String[] input = { "x" };
		try {
			for (String x : input) {
				show(x);
				Scanner scanner = new Scanner(x).scan();
				show(scanner);
				SimpleParser SimpleParser = new SimpleParser(scanner);
				SimpleParser.primary();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * these test cases invoke SimpleParser.lhs()
	 */
	@Test
	public void lhstest1() throws SyntaxException, LexicalException {
		String[] input = { "AKSHAY", "akshay[[x,y]]", "kk[[r,A]]", "m[[r,A]]" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.lhs();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Test
	public void lhstest2() throws SyntaxException, LexicalException {
		String[] input = { "Sharma", "cos[56]" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.lhs();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.FunctionApplication()
	 */
	@Test
	public void FunctionApplicationtest1() throws SyntaxException, LexicalException {
		String[] input = { "cos(2)", "atan(x)", "polar_a(6)", "sin[4,3]", "abs[x,y]" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			SimpleParser.functionApplication();
		}
	}

	@Test
	public void FunctionApplicationtest2() throws SyntaxException, LexicalException {
		String[] input = { "x", "cos[5]", "abs[5]" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.functionApplication();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.IdentOrPixelSelectorExpression()
	 */
	@Test
	public void identOrPixelSelectorExpressionTest1() throws SyntaxException, LexicalException {
		String[] input = { "akshay", "i[i,j]34" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			SimpleParser.identOrPixelSelectorExpression();
		}
	}

	/**
	 * these test cases invoke SimpleParser.functionName()
	 */
	@Test
	public void FunctionNametest1() throws SyntaxException, LexicalException {
		String[] input = { "sin", "cos", "atan", "cart_x", "cart_y", "polar_a", "polar_r", "abs" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			SimpleParser.functionName();
		}
	}

	@Test
	public void FunctionNametest2() throws SyntaxException, LexicalException {
		String[] input = { "x" };
		for (String x : input) {
			show(x);
			Scanner scanner = new Scanner(x).scan();
			show(scanner);
			SimpleParser SimpleParser = new SimpleParser(scanner);
			try {
				SimpleParser.functionName();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * these test cases invoke SimpleParser.lhsSelector()
	 */
	@Test
	public void lhsSelectorTest1() throws SyntaxException, LexicalException {
		String input = "[x,y]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.lhsSelector();
	}

	@Test
	public void lhsSelectorTest2() throws SyntaxException, LexicalException {
		String input = "[r,A]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.lhsSelector();
	}

	@Test
	public void lhsSelectorTest3() throws SyntaxException, LexicalException {
		String input = "[rA]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		try {
			SimpleParser.lhsSelector();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * this positive test case invokes the method for SimpleParser.XySelector
	 * directly
	 */
	@Test
	public void PositiveXySelectorTest() throws SyntaxException, LexicalException {
		String input = "x,y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.xySelector();
	}

	/**
	 * this negative test case invokes the method for SimpleParser.XySelector
	 * directly
	 */
	@Test
	public void NegativeXySelectorTest() throws SyntaxException, LexicalException {
		String input = "xy,";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		try {
			SimpleParser.xySelector();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * this positive test case invokes the method for SimpleParser.RaSelector
	 * directly
	 */
	@Test
	public void PositiveRaSelectorTest() throws SyntaxException, LexicalException {
		String input = "r,A";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		SimpleParser.raSelector();
	}

	/**
	 * this negative test case invokes the method for SimpleParser.RaSelector
	 * directly
	 */
	@Test
	public void NegativeRaSelectorTest() throws SyntaxException, LexicalException {
		String input = ",rA";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		try {
			SimpleParser.raSelector();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * tests SimpleParser.Selector only for terminal symbols
	 */
	@Test
	public void selectorTest1() throws SyntaxException, LexicalException {
		String input = "xy";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			SimpleParser.selector();
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void selectorTest2() throws SyntaxException, LexicalException {
		String s = "2*2+5+x>2*2+5+x==d6a09aa6aa42d705e4f7b01eaa57a671f05da686ded99c77294d3135563820a>=2*2+5+x!=2*2+5+x<=2*2+5+x|2*2+5+x>2*2+5+x==2*2+5+x<2*2+5+x&2*2+5+x>=2*2+5+x!=2*2+5+x<=2*2+5+x";
		String[] input1 = { s, s + "?" + s + ":" + s };
		String input = input1[0] + "," + input1[1];
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser SimpleParser = new SimpleParser(scanner);
		try {
			SimpleParser.selector();
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void testDec11() throws LexicalException, SyntaxException {
		String input = "prog int k;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		SimpleParser parser = new SimpleParser(scanner); //
		parser.parse();
	}

	@Test
	public void expression23() throws SyntaxException, LexicalException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression2() throws SyntaxException, LexicalException {
		String input = "harshit int b";
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
	public void expression3() throws SyntaxException, LexicalException {
		String input = "harshit int b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	// checking image declaration
	public void expression4() throws SyntaxException, LexicalException {
		String input = "harshit image pratham;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression5() throws SyntaxException, LexicalException {
		String input = "harshit file ban=\"bans\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression6() throws SyntaxException, LexicalException {
		String input = "harshit image [m+p,b+y] jh;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression7() throws SyntaxException, LexicalException {
		String input = "harshit image [m+p,b+y] jh <- \"Pratham\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression8() throws SyntaxException, LexicalException {
		String input = "harshit int p=a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression9() throws SyntaxException, LexicalException {
		String input = "harshit harshit = a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression10() throws SyntaxException, LexicalException {
		String input = "harshit harshit -> pratham;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression11() throws SyntaxException, LexicalException {
		String input = "harshit harshit <- @123+456;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression12() throws SyntaxException, LexicalException {
		String input = "harshit harshit [[x,y]]=a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.program();
	}

	@Test
	public void expression13() throws SyntaxException, LexicalException {
		String input = "harshit harshit [[x,y]];";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression14() throws SyntaxException, LexicalException {
		String input = "atan(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression18() throws SyntaxException, LexicalException {
		String input = "atan(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression15() throws SyntaxException, LexicalException {
		String input = "log(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.expression(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void expression16() throws SyntaxException, LexicalException {
		String input = "harshit (a+b,m+n)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression(); // Parse the program
	}

	@Test
	public void expression17() throws SyntaxException, LexicalException {
		String input = "harshit ";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression(); // Parse the program
	}

	@Test
	public void expression19() throws SyntaxException, LexicalException {
		String input = "atan(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression20() throws SyntaxException, LexicalException {
		String input = "atan(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression21() throws SyntaxException, LexicalException {
		String input = "2 + 3 * 4/5 != 2 & 2 & 6";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);
		parser.expression();
	}

	@Test
	public void expression22() throws SyntaxException, LexicalException {
		String input = "program ;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		SimpleParser parser = new SimpleParser(scanner);

		parser.expression(); // Parse the program

	}

}
