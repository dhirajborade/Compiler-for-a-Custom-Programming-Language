package cop5556fa17;

import static cop5556fa17.Scanner.Kind.KW_boolean;
import static cop5556fa17.Scanner.Kind.KW_cos;
import static cop5556fa17.Scanner.Kind.KW_int;
import static cop5556fa17.Scanner.Kind.KW_url;
import static cop5556fa17.Scanner.Kind.KW_x;
import static cop5556fa17.Scanner.Kind.KW_y;
import static cop5556fa17.Scanner.Kind.OP_EXCL;
import static cop5556fa17.Scanner.Kind.OP_PLUS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Parser.SyntaxException;
import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.AST.Declaration_Image;
import cop5556fa17.AST.Declaration_SourceSink;
import cop5556fa17.AST.Declaration_Variable;
import cop5556fa17.AST.Expression;
import cop5556fa17.AST.Expression_Binary;
import cop5556fa17.AST.Expression_Conditional;
import cop5556fa17.AST.Expression_FunctionAppWithExprArg;
import cop5556fa17.AST.Expression_FunctionAppWithIndexArg;
import cop5556fa17.AST.Expression_PixelSelector;
import cop5556fa17.AST.Expression_PredefinedName;
import cop5556fa17.AST.Expression_Unary;
import cop5556fa17.AST.LHS;
import cop5556fa17.AST.Program;
import cop5556fa17.AST.Sink_Ident;
import cop5556fa17.AST.Source_StringLiteral;
import cop5556fa17.AST.Statement_Assign;
import cop5556fa17.AST.Statement_In;
import cop5556fa17.AST.Statement_Out;

public class ParserTestCharan {

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
			parser.parse();
			; // Parse the program, which should throw an exception
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
	public void testDec2() throws LexicalException, SyntaxException {
		String input = "karan file karan = \"karan\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		Declaration_SourceSink var = (Declaration_SourceSink) ast.decsAndStatements.get(0);
		Source_StringLiteral s = (Source_StringLiteral) var.source;
		assertEquals(s.fileOrUrl, "karan");

	}

	@Test
	public void testDec3() throws LexicalException, SyntaxException {
		String input = "chjjban url chajuhn = @ x=3 ? 6 : 12;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			Program ast = parser.program();
			show(ast);
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testDec4() throws LexicalException, SyntaxException {
		String input = "+++------ch";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_Unary ex = (Expression_Unary) parser.expression();
		show(ex);
		assertEquals(ex.op, Kind.OP_PLUS);
	}

	@Test
	public void testDec5() throws LexicalException, SyntaxException {
		String input = "karan [y | cos[x,y],sin[x,z] ] ";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_PixelSelector ex = (Expression_PixelSelector) parser.expression();
		show(ex);
		assertEquals(ex.name, "karan");
	}

	@Test
	public void testDec6() throws LexicalException, SyntaxException {
		String input = "cos(atan(cos(cos[DEF_X,Y])))";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_FunctionAppWithExprArg arg = (Expression_FunctionAppWithExprArg) parser.expression();
		show(arg);
		Expression_FunctionAppWithExprArg ar = (Expression_FunctionAppWithExprArg) arg.arg;
		assertEquals(ar.firstToken.kind, Kind.KW_atan);
	}

	@Test
	public void testDec7() throws LexicalException, SyntaxException {
		String input = "+++------cdfasd";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression ex = parser.expression();
		show(ex);
	}

	@Test
	public void testDec8() throws LexicalException, SyntaxException {
		String input = "y|x&z!=+x%y*+z/-y?cos[x,y]:sin(x)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_Conditional ex = (Expression_Conditional) parser.expression();
		Expression_Binary e = (Expression_Binary) ex.condition;
		assertEquals(e.e0.firstToken.kind, KW_y);
	}

	@Test
	public void testDec9() throws LexicalException, SyntaxException {
		String input = "iden1 image [x|+y%z,k] iden2 <- iden3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Declaration_Image var = (Declaration_Image) p.decsAndStatements.get(0);
		Expression_Binary bin = (Expression_Binary) var.xSize;
		Expression_PredefinedName name = (Expression_PredefinedName) bin.e0;
		assertEquals(name.firstToken.kind, KW_x);
		show(p);
	}

	@Test
	public void testDec10() throws LexicalException, SyntaxException {
		String input = "inde1 int ka = 4; int hc = -4 * x%z;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		show(p);
		Declaration_Variable dec = (Declaration_Variable) p.decsAndStatements.get(1);
		assertEquals(dec.name, "hc");
	}

	@Test
	public void testDec11() throws LexicalException, SyntaxException {
		String input = "inden1 inden2 <- inden3; charan <- karan;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Statement_In dec = (Statement_In) p.decsAndStatements.get(0);
		show(p);
		assertEquals(dec.name, "inden2");
	}

	@Test
	public void testDec12() throws LexicalException, SyntaxException {
		String input = "iden iden1 = cos(atan[x,y]);";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		show(p);
		Statement_Assign var = (Statement_Assign) p.decsAndStatements.get(0);
		assertEquals(var.lhs.name, "iden1");
	}

	@Test
	public void testDec13() throws LexicalException, SyntaxException {
		String input = "x == (*z-y+x%x)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.expression();
		} catch (SyntaxException ex) {
			throw (ex);
		}
	}

	@Test
	public void testDec14() throws LexicalException, SyntaxException {
		String input = "iden1 file inden2 = inden3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		show(p);
		Declaration_SourceSink var = (Declaration_SourceSink) p.decsAndStatements.get(0);
		assertEquals(var.source.toString(), "Source_Ident [name=inden3]");

	}

	@Test
	public void testDec15() throws LexicalException, SyntaxException {
		String input = "x|!!false";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_Binary ex = (Expression_Binary) parser.expression();
		Expression_Unary un = (Expression_Unary) ex.e1;
		assertEquals(un.op, OP_EXCL);
	}

	@Test
	public void testDec16() throws LexicalException, SyntaxException {
		String input = "iden1 iden -> screen;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Statement_Out out = (Statement_Out) p.decsAndStatements.get(0);
		Sink_Ident sin = (Sink_Ident) out.sink;
		assertEquals(sin.name, "screen");
		show(p);
	}

	@Test
	public void testDec17() throws LexicalException, SyntaxException {
		String input = "iden1 iden2 [[x,y]] = +x%cos[z,y];";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Statement_Assign ass = (Statement_Assign) p.decsAndStatements.get(0);
		LHS lhs = (LHS) ass.lhs;
		assertEquals(lhs.name, "iden2");
		show(p);
	}

	@Test
	public void testDec18() throws LexicalException, SyntaxException {
		String input = "+x-y+z-Y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression_Binary ex = (Expression_Binary) parser.expression();
		Expression_Binary ex1 = (Expression_Binary) ex.e0;
		Expression_Binary ex2 = (Expression_Binary) ex1.e0;
		Expression_Unary ex3 = (Expression_Unary) ex2.e0;
		assertEquals(ex3.op, OP_PLUS);
		show(ex);

	}

	@Test
	public void testDec19() throws LexicalException, SyntaxException {
		String input = "inde3 image [cos[x,y], sin[Y,X]] inden3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		show(p);
		Declaration_Image img = (Declaration_Image) p.decsAndStatements.get(0);
		Expression_FunctionAppWithIndexArg arg = (Expression_FunctionAppWithIndexArg) img.xSize;
		assertEquals(arg.function, KW_cos);

	}

	@Test
	public void testDec20() throws LexicalException, SyntaxException {
		String input = "iden1 boolean iden2 = +x*y%x+cos[z,y];";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Declaration_Variable dec = (Declaration_Variable) p.decsAndStatements.get(0);
		assertEquals(dec.type.kind, KW_boolean);
		show(p);
	}

	@Test
	public void testDec21() throws LexicalException, SyntaxException {
		String input = "inde1 int inden2; boolean inden3; int inden3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		show(p);
		Declaration_Variable var = (Declaration_Variable) p.decsAndStatements.get(2);
		assertEquals(var.name, "inden3");
	}

	@Test
	public void testDec22() throws LexicalException, SyntaxException {
		String input = "iden1 url iden2 = iden3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program p = parser.program();
		Declaration_SourceSink dec = (Declaration_SourceSink) p.decsAndStatements.get(0);
		assertEquals(dec.type, KW_url);
		show(p);
	}

}
