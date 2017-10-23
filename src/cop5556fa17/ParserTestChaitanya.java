package cop5556fa17;

import static cop5556fa17.Scanner.Kind.IDENTIFIER;
import static cop5556fa17.Scanner.Kind.KW_a;
import static cop5556fa17.Scanner.Kind.KW_boolean;
import static cop5556fa17.Scanner.Kind.KW_file;
import static cop5556fa17.Scanner.Kind.KW_image;
import static cop5556fa17.Scanner.Kind.KW_int;
import static cop5556fa17.Scanner.Kind.KW_url;
import static cop5556fa17.Scanner.Kind.KW_y;
import static cop5556fa17.Scanner.Kind.OP_PLUS;
import static cop5556fa17.Scanner.Kind.OP_TIMES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Parser.SyntaxException;
import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.AST.ASTNode;
import cop5556fa17.AST.Declaration_Image;
import cop5556fa17.AST.Declaration_SourceSink;
import cop5556fa17.AST.Declaration_Variable;
import cop5556fa17.AST.Expression;
import cop5556fa17.AST.Expression_Binary;
import cop5556fa17.AST.Expression_BooleanLit;
import cop5556fa17.AST.Expression_Conditional;
import cop5556fa17.AST.Expression_FunctionAppWithExprArg;
import cop5556fa17.AST.Expression_FunctionAppWithIndexArg;
import cop5556fa17.AST.Expression_Ident;
import cop5556fa17.AST.Expression_IntLit;
import cop5556fa17.AST.Expression_PredefinedName;
import cop5556fa17.AST.Index;
import cop5556fa17.AST.Program;
import cop5556fa17.AST.Sink_Ident;
import cop5556fa17.AST.Source_Ident;
import cop5556fa17.AST.Source_StringLiteral;
import cop5556fa17.AST.Statement_Out;

public class ParserTestChaitanya {

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
			parser.parse();// Parse the program, which should throw an exception
		} catch (SyntaxException e) {
			show(e); // catch the exception and show it
			throw e; // rethrow for Junit
		}
	}

	@Test
	public void testNameOnly() throws LexicalException, SyntaxException {
		String input = "prog"; // Legal program with only a name
		show(input); // display input
		Scanner scanner = new Scanner(input).scan(); // Create scanner and
														// create token list
		show(scanner); // display the tokens
		Parser parser = new Parser(scanner); // create parser
		Program ast = parser.parse(); // parse program and get AST
		show(ast); // Display the AST
		assertEquals(ast.name, "prog"); // Check the name field in the Program
										// object
		assertTrue(ast.decsAndStatements.isEmpty()); // Check the
														// decsAndStatements
														// list in the Program
														// object. It should be
														// empty.
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
		// This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(KW_int, dec.type.kind);
		assertEquals("k", dec.name);
		assertNull(dec.e);
	}

	@Test
	public void testVarDec2() throws LexicalException, SyntaxException {
		String input = "prog int k = ( num1 > num2 ) ? num3 : num4;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		// This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(KW_int, dec.type.kind);
		assertEquals("k", dec.name);
		// assertNull(dec.e);
	}

	@Test
	public void testImageDec1() throws LexicalException, SyntaxException {
		String input = "prog image dhiraj;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testSourceSinkDec1() throws LexicalException, SyntaxException {
		String input = "prog url Maheshbabu = \"spyder\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testImageOutStmnt1() throws LexicalException, SyntaxException {
		String input = "prog Maheshbabu -> SCREEN;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testImageInStmnt1() throws LexicalException, SyntaxException {
		String input = "prog Maheshbabu <- @num1 + num2;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testAssgnStmnt1() throws LexicalException, SyntaxException {
		String input = "prog Maheshbabu[[x,y]] = num1 + num2;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testAssgnStmnt2() throws LexicalException, SyntaxException {
		String input = "prog Maheshbabu = num1 + num2;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		// assertEquals(ast.name, "prog");
		// //This should have one Declaration_Variable object, which is at
		// position 0 in the decsAndStatements list
		// Declaration_Variable dec = (Declaration_Variable)
		// ast.decsAndStatements
		// .get(0);
		// assertEquals(KW_int, dec.type.kind);
		// assertEquals("k", dec.name);
		// //assertNull(dec.e);
	}

	@Test
	public void testExp1() throws LexicalException, SyntaxException {
		String input = "a: b: c";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		ASTNode expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_PredefinedName [name=KW_a]");
	}

	@Test
	public void testExp2() throws LexicalException, SyntaxException {
		String input = "num1 + num2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst.firstToken);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Ident [name=num1], op=OP_PLUS, e1=Expression_Ident [name=num2]]");
	}

	@Test
	public void testExp3() throws LexicalException, SyntaxException {
		String input = "a = (c > d) ? e : f";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_PredefinedName [name=KW_a]");
	}

	@Test
	public void testProRandom1() throws LexicalException, SyntaxException {
		String input = "lo int j = sin [x+y,a+b] ;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=lo, decsAndStatements=[Declaration_Variable [type=[KW_int,int,3,3,1,4], name=j, e=Expression_FunctionAppWithIndexArg [function=KW_sin, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]]]");
	}

	@Test
	public void testExp4() throws LexicalException, SyntaxException {
		String input = "p-+q";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Ident [name=p], op=OP_MINUS, e1=Expression_Unary [op=OP_PLUS, e=Expression_Ident [name=q]]]");
	}

	@Test
	public void testProRandom2() throws LexicalException, SyntaxException {
		String input = "hello boolean i = cos [x+y,a+b] ; hal = 5+--++++9 ; aks <- \"aka\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=hello, decsAndStatements=[Declaration_Variable [type=[KW_boolean,boolean,6,7,1,7], name=i, e=Expression_FunctionAppWithIndexArg [function=KW_cos, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]], Statement_Assign [lhs=name [name=hal, index=null], e=Expression_Binary [e0=Expression_IntLit [value=5], op=OP_PLUS, e1=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=9]]]]]]]]], Statement_In [name=aks, source=Source_StringLiteral [fileOrUrl=aka]]]]");
	}

	@Test
	public void testProRandom3() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN;_abc <- _abc;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_In [name=_abc, source=Source_Ident [name=_abc]]]]");
	}

	@Test
	public void testProRandom4() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN; _abc <- \"_abcnik\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_In [name=_abc, source=Source_StringLiteral [fileOrUrl=_abcnik]]]]");
	}

	@Test
	public void testProRandom5() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN; _abc <- @ true;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_In [name=_abc, source=Source_CommandLineParam [paramNum=Expression_BooleanLit [value=true]]]]]");
	}

	@Test
	public void testProRandom6() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN; _abc <- @ false;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_In [name=_abc, source=Source_CommandLineParam [paramNum=Expression_BooleanLit [value=false]]]]]");
	}

	@Test
	public void testProRandom7() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN; _pqr [[x,y]] = a;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_Assign [lhs=name [name=_pqr, index=Index [e0=Expression_PredefinedName [name=KW_x], e1=Expression_PredefinedName [name=KW_y]]], e=Expression_PredefinedName [name=KW_a]]]]");
	}

	@Test
	public void testProRandom8() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN; _pqr [[x,y]] = a;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]], Statement_Assign [lhs=name [name=_pqr, index=Index [e0=Expression_PredefinedName [name=KW_x], e1=Expression_PredefinedName [name=KW_y]]], e=Expression_PredefinedName [name=KW_a]]]]");
	}

	@Test
	public void testProRandom9() throws LexicalException, SyntaxException {
		String input = "prog k <- \"Maheshbabu spyder\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=prog, decsAndStatements=[Statement_In [name=k, source=Source_StringLiteral [fileOrUrl=Maheshbabu spyder]]]]");
	}

	@Test
	public void testProRandom10() throws LexicalException, SyntaxException {
		String input = "nik image _abc;_abc -> SCREEN;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=nik, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=_abc, source=null], Statement_Out [name=_abc, sink=Sink_SCREEN [kind=KW_SCREEN]]]]");
	}

	@Test
	public void testExp5() throws LexicalException, SyntaxException {
		String input = "a+b < d-c";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]], op=OP_LT, e1=Expression_Binary [e0=Expression_Ident [name=d], op=OP_MINUS, e1=Expression_Ident [name=c]]]");
	}

	@Test
	public void testExp6() throws LexicalException, SyntaxException {
		String input = "when?a?b?c:d:e:f";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Ident [name=when], trueExpression=Expression_Conditional [condition=Expression_PredefinedName [name=KW_a], trueExpression=Expression_Conditional [condition=Expression_Ident [name=b], trueExpression=Expression_Ident [name=c], falseExpression=Expression_Ident [name=d]], falseExpression=Expression_Ident [name=e]], falseExpression=Expression_Ident [name=f]]");
	}

	@Test
	public void testExp7() throws LexicalException, SyntaxException {
		String input = "cos(a+b)]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_FunctionAppWithExprArg [function=KW_cos, arg=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]");
	}

	@Test
	public void testExp8() throws LexicalException, SyntaxException {
		String input = "+-!DEF_X?+-!DEF_Y:Maheshbabu [(a*b),c*d]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_X]]]], trueExpression=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_Y]]]], falseExpression=Expression_PixelSelector [name=Maheshbabu, index=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_TIMES, e1=Expression_Ident [name=b]], e1=Expression_Binary [e0=Expression_Ident [name=c], op=OP_TIMES, e1=Expression_Ident [name=d]]]]]");
	}

	@Test
	public void testExpRan() throws LexicalException, SyntaxException {
		String input = "+x*y/t%a+g-u>2<8>=9<=12==0!=9 & x+y & t+s | f+g ? +x*y/t%a+g-u>2<8>=9<=12==0!=9 & x+y & t+s | f+g:+x*y/t%a+g-u>2<8>=9<=12==0!=9 & x+y & t+s | f+g";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]], op=OP_MOD, e1=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=g]], op=OP_MINUS, e1=Expression_Ident [name=u]], op=OP_GT, e1=Expression_IntLit [value=2]], op=OP_LT, e1=Expression_IntLit [value=8]], op=OP_GE, e1=Expression_IntLit [value=9]], op=OP_LE, e1=Expression_IntLit [value=12]], op=OP_EQ, e1=Expression_IntLit [value=0]], op=OP_NEQ, e1=Expression_IntLit [value=9]], op=OP_AND, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Ident [name=t], op=OP_PLUS, e1=Expression_Ident [name=s]]], op=OP_OR, e1=Expression_Binary [e0=Expression_Ident [name=f], op=OP_PLUS, e1=Expression_Ident [name=g]]], trueExpression=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]], op=OP_MOD, e1=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=g]], op=OP_MINUS, e1=Expression_Ident [name=u]], op=OP_GT, e1=Expression_IntLit [value=2]], op=OP_LT, e1=Expression_IntLit [value=8]], op=OP_GE, e1=Expression_IntLit [value=9]], op=OP_LE, e1=Expression_IntLit [value=12]], op=OP_EQ, e1=Expression_IntLit [value=0]], op=OP_NEQ, e1=Expression_IntLit [value=9]], op=OP_AND, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Ident [name=t], op=OP_PLUS, e1=Expression_Ident [name=s]]], op=OP_OR, e1=Expression_Binary [e0=Expression_Ident [name=f], op=OP_PLUS, e1=Expression_Ident [name=g]]], falseExpression=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]], op=OP_MOD, e1=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=g]], op=OP_MINUS, e1=Expression_Ident [name=u]], op=OP_GT, e1=Expression_IntLit [value=2]], op=OP_LT, e1=Expression_IntLit [value=8]], op=OP_GE, e1=Expression_IntLit [value=9]], op=OP_LE, e1=Expression_IntLit [value=12]], op=OP_EQ, e1=Expression_IntLit [value=0]], op=OP_NEQ, e1=Expression_IntLit [value=9]], op=OP_AND, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Ident [name=t], op=OP_PLUS, e1=Expression_Ident [name=s]]], op=OP_OR, e1=Expression_Binary [e0=Expression_Ident [name=f], op=OP_PLUS, e1=Expression_Ident [name=g]]]]");
	}

	@Test
	public void testExp9() throws LexicalException, SyntaxException {
		String input = "++++x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]]]]]");
	}

	@Test
	public void testExp10() throws LexicalException, SyntaxException {
		String input = "sin(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_FunctionAppWithExprArg [function=KW_sin, arg=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]");
	}

	@Test
	public void testExp11() throws LexicalException, SyntaxException {
		String input = "yu[m,n]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_PixelSelector [name=yu, index=Index [e0=Expression_Ident [name=m], e1=Expression_Ident [name=n]]]");
	}

	@Test
	public void testProRandom11() throws LexicalException, SyntaxException {
		String input = "hello boolean i = cos [x+y,a+b] ; hal = 5+--++++9 ; niks <- \"nik\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=hello, decsAndStatements=[Declaration_Variable [type=[KW_boolean,boolean,6,7,1,7], name=i, e=Expression_FunctionAppWithIndexArg [function=KW_cos, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]], Statement_Assign [lhs=name [name=hal, index=null], e=Expression_Binary [e0=Expression_IntLit [value=5], op=OP_PLUS, e1=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=9]]]]]]]]], Statement_In [name=niks, source=Source_StringLiteral [fileOrUrl=nik]]]]");
	}

	@Test
	public void testExpRanAbc() throws LexicalException, SyntaxException {
		String input = "x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y & x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y & x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y & x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]]]");
	}

	@Test
	public void testProRandom12() throws LexicalException, SyntaxException {
		String input = "ident1 url ident2 = \"me1333\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=ident1, decsAndStatements=[Declaration_SourceSink [type=KW_url, name=ident2, source=Source_StringLiteral [fileOrUrl=me1333]]]]");
	}

	@Test
	public void testProRandom13() throws LexicalException, SyntaxException {
		String input = "idents url abcd = \"this is SSD string literal\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=idents, decsAndStatements=[Declaration_SourceSink [type=KW_url, name=abcd, source=Source_StringLiteral [fileOrUrl=this is SSD string literal]]]]");
	}

	@Test
	public void testProRandom14() throws LexicalException, SyntaxException {
		String input = "hello boolean i = cos [x+y,a+b] ; boolean i = cos [x+y,a+b] ; niks <- \"nik\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=hello, decsAndStatements=[Declaration_Variable [type=[KW_boolean,boolean,6,7,1,7], name=i, e=Expression_FunctionAppWithIndexArg [function=KW_cos, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]], Declaration_Variable [type=[KW_boolean,boolean,34,7,1,35], name=i, e=Expression_FunctionAppWithIndexArg [function=KW_cos, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]], Statement_In [name=niks, source=Source_StringLiteral [fileOrUrl=nik]]]]");
	}

	@Test
	public void testProRandom15() throws LexicalException, SyntaxException {
		String input = "lo int j = sin [x+y,a+b] ;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=lo, decsAndStatements=[Declaration_Variable [type=[KW_int,int,3,3,1,4], name=j, e=Expression_FunctionAppWithIndexArg [function=KW_sin, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]]]");
	}

	@Test
	public void testExp12() throws LexicalException, SyntaxException {
		String input = "_habdhdb_$ int ident = 4*3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Ident [name=_habdhdb_$]");
	}

	@Test
	public void testExp13() throws LexicalException, SyntaxException {
		String input = "p-+q";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Ident [name=p], op=OP_MINUS, e1=Expression_Unary [op=OP_PLUS, e=Expression_Ident [name=q]]]");
	}

	@Test
	public void testProRandom16() throws LexicalException, SyntaxException {
		String input = "abc int def;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=abc, decsAndStatements=[Declaration_Variable [type=[KW_int,int,4,3,1,5], name=def, e=null]]]");
	}

	@Test
	public void testExp14() throws LexicalException, SyntaxException {
		String input = "+x*y/t%a+g-u>2<8>=9<=12==0!=9 & x+y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]], op=OP_MOD, e1=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=g]], op=OP_MINUS, e1=Expression_Ident [name=u]], op=OP_GT, e1=Expression_IntLit [value=2]], op=OP_LT, e1=Expression_IntLit [value=8]], op=OP_GE, e1=Expression_IntLit [value=9]], op=OP_LE, e1=Expression_IntLit [value=12]], op=OP_EQ, e1=Expression_IntLit [value=0]], op=OP_NEQ, e1=Expression_IntLit [value=9]], op=OP_AND, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]]]");
	}

	@Test
	public void testExp15() throws LexicalException, SyntaxException {
		String input = "+x*y/t%a+g-u";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]], op=OP_MOD, e1=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=g]], op=OP_MINUS, e1=Expression_Ident [name=u]]");
	}

	@Test
	public void testExp16() throws LexicalException, SyntaxException {
		String input = "+x*y/t";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]], op=OP_DIV, e1=Expression_Ident [name=t]]");
	}

	@Test
	public void testExp17() throws LexicalException, SyntaxException {
		String input = "(+-!DEF_X)?polar_r(234):+-!cart_y(a+b) 345?atan[a+b,c+d]:abc";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_X]]]], trueExpression=Expression_FunctionAppWithExprArg [function=KW_polar_r, arg=Expression_IntLit [value=234]], falseExpression=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_FunctionAppWithExprArg [function=KW_cart_y, arg=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]]]");
	}

	@Test
	public void testExp18() throws LexicalException, SyntaxException {
		String input = "+-!DEF_X?+-!DEF_Y:+-!c";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_X]]]], trueExpression=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_Y]]]], falseExpression=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_Ident [name=c]]]]]");
	}

	@Test
	public void testExp19() throws LexicalException, SyntaxException {
		String input = "+ban?!a+b:c+2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Unary [op=OP_PLUS, e=Expression_Ident [name=ban]], trueExpression=Expression_Binary [e0=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_a]], op=OP_PLUS, e1=Expression_Ident [name=b]], falseExpression=Expression_Binary [e0=Expression_Ident [name=c], op=OP_PLUS, e1=Expression_IntLit [value=2]]]");
	}

	@Test
	public void testExp20() throws LexicalException, SyntaxException {
		String input = "myProg boolean val = false;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Ident [name=myProg]");
	}

	@Test
	public void testExp21() throws LexicalException, SyntaxException {
		String input = "HelloNik92898_$ int Maheshbabu = 5*3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Ident [name=HelloNik92898_$]");
	}

	@Test
	public void testExp22() throws LexicalException, SyntaxException {
		String input = "+++x = +(+(+x)) = x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]]]]");
	}

	@Test
	public void testExp23() throws LexicalException, SyntaxException {
		String input = "+x=x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]]");
	}

	@Test
	public void testExp24() throws LexicalException, SyntaxException {
		String input = "a=a+-b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_PredefinedName [name=KW_a]");
	}

	@Test
	public void testProRandom17() throws LexicalException, SyntaxException {
		String input = "one boolean sjsurya; int d; int g=0; boolean val=false; file name=sjsurya; url fi=\"him\"; file name=@123; image img;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=one, decsAndStatements=[Declaration_Variable [type=[KW_boolean,boolean,4,7,1,5], name=sjsurya, e=null], Declaration_Variable [type=[KW_int,int,21,3,1,22], name=d, e=null], Declaration_Variable [type=[KW_int,int,28,3,1,29], name=g, e=Expression_IntLit [value=0]], Declaration_Variable [type=[KW_boolean,boolean,37,7,1,38], name=val, e=Expression_BooleanLit [value=false]], Declaration_SourceSink [type=KW_file, name=name, source=Source_Ident [name=sjsurya]], Declaration_SourceSink [type=KW_url, name=fi, source=Source_StringLiteral [fileOrUrl=him]], Declaration_SourceSink [type=KW_file, name=name, source=Source_CommandLineParam [paramNum=Expression_IntLit [value=123]]], Declaration_Image [xSize=null, ySize=null, name=img, source=null]]]");
	}

	@Test
	public void exp6() throws SyntaxException, LexicalException {
		String input = "prog image [m+p,b+y] jh;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements.get(0);
		Expression_Binary ex = (Expression_Binary) dec.xSize;
		Expression_Ident eix0 = (Expression_Ident) ex.e0;
		assertEquals("m", eix0.name);
		assertEquals(OP_PLUS, ex.op);
		Expression_Ident eix1 = (Expression_Ident) ex.e1;
		assertEquals("p", eix1.name);
		Expression_Binary ey = (Expression_Binary) dec.ySize;
		Expression_Ident eiy0 = (Expression_Ident) ey.e0;
		assertEquals("b", eiy0.name);
		assertEquals(OP_PLUS, ey.op);
		Expression_PredefinedName eiy1 = (Expression_PredefinedName) ey.e1;
		assertEquals(KW_y, eiy1.kind);
		assertEquals("jh", dec.name);
		assertNull(dec.source);
	}

	@Test
	public void exp5() throws SyntaxException, LexicalException {
		String input = "Maheshbabu image spyder;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "Maheshbabu");
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements.get(0);
		assertNull(dec.xSize);
		assertNull(dec.ySize);
		assertEquals("spyder", dec.name);
		assertNull(dec.source);
	}

	@Test
	public void exp4() throws SyntaxException, LexicalException {
		String input = "Maheshbabu int b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "Maheshbabu");
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(KW_int, dec.type.kind);
		assertEquals("b", dec.name);
		assertNull(dec.e);
	}

	@Test
	public void testProRandom18() throws LexicalException, SyntaxException {
		String input = "one two=123; three [[r,A]]=123+234*678; sjsurya -> sandip; sjsurya -> SCREEN;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=one, decsAndStatements=[Statement_Assign [lhs=name [name=two, index=null], e=Expression_IntLit [value=123]], Statement_Assign [lhs=name [name=three, index=Index [e0=Expression_PredefinedName [name=KW_r], e1=Expression_PredefinedName [name=KW_A]]], e=Expression_Binary [e0=Expression_IntLit [value=123], op=OP_PLUS, e1=Expression_Binary [e0=Expression_IntLit [value=234], op=OP_TIMES, e1=Expression_IntLit [value=678]]]], Statement_Out [name=sjsurya, sink=Sink_Ident [name=sandip]], Statement_Out [name=sjsurya, sink=Sink_SCREEN [kind=KW_SCREEN]]]]");
	}

	@Test
	public void testProRandom19() throws LexicalException, SyntaxException {
		String input = "one two=123; three [[r,A]]=123+234*678; sjsurya <- sandip; sjsurya <- \"nipa\"; sjsurya <- @123;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=one, decsAndStatements=[Statement_Assign [lhs=name [name=two, index=null], e=Expression_IntLit [value=123]], Statement_Assign [lhs=name [name=three, index=Index [e0=Expression_PredefinedName [name=KW_r], e1=Expression_PredefinedName [name=KW_A]]], e=Expression_Binary [e0=Expression_IntLit [value=123], op=OP_PLUS, e1=Expression_Binary [e0=Expression_IntLit [value=234], op=OP_TIMES, e1=Expression_IntLit [value=678]]]], Statement_In [name=sjsurya, source=Source_Ident [name=sandip]], Statement_In [name=sjsurya, source=Source_StringLiteral [fileOrUrl=nipa]], Statement_In [name=sjsurya, source=Source_CommandLineParam [paramNum=Expression_IntLit [value=123]]]]]");
	}

	@Test
	public void testProRandom20() throws LexicalException, SyntaxException {
		String input = "one two=123; three [[r,A]]=123+234*678;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=one, decsAndStatements=[Statement_Assign [lhs=name [name=two, index=null], e=Expression_IntLit [value=123]], Statement_Assign [lhs=name [name=three, index=Index [e0=Expression_PredefinedName [name=KW_r], e1=Expression_PredefinedName [name=KW_A]]], e=Expression_Binary [e0=Expression_IntLit [value=123], op=OP_PLUS, e1=Expression_Binary [e0=Expression_IntLit [value=234], op=OP_TIMES, e1=Expression_IntLit [value=678]]]]]]");
	}

	@Test
	public void testProRandom21() throws LexicalException, SyntaxException {
		String input = "one image img <- sjsurya; image [a+b,m+n] img <- @123; image img <- \"Bansu\"; image cb;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=one, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=img, source=Source_Ident [name=sjsurya]], Declaration_Image [xSize=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]], ySize=Expression_Binary [e0=Expression_Ident [name=m], op=OP_PLUS, e1=Expression_Ident [name=n]], name=img, source=Source_CommandLineParam [paramNum=Expression_IntLit [value=123]]], Declaration_Image [xSize=null, ySize=null, name=img, source=Source_StringLiteral [fileOrUrl=Bansu]], Declaration_Image [xSize=null, ySize=null, name=cb, source=null]]]");
	}

	@Test
	public void testExp25() throws LexicalException, SyntaxException {
		String input = "2?a+b:s+t";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_IntLit [value=2], trueExpression=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]], falseExpression=Expression_Binary [e0=Expression_Ident [name=s], op=OP_PLUS, e1=Expression_Ident [name=t]]]");
	}

	@Test
	public void testExp26() throws LexicalException, SyntaxException {
		String input = "2 + 3 * 4/5 != 2 & 2 & 6";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_IntLit [value=2], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_IntLit [value=3], op=OP_TIMES, e1=Expression_IntLit [value=4]], op=OP_DIV, e1=Expression_IntLit [value=5]]], op=OP_NEQ, e1=Expression_IntLit [value=2]], op=OP_AND, e1=Expression_IntLit [value=2]], op=OP_AND, e1=Expression_IntLit [value=6]]");
	}

	@Test
	public void testExp27() throws LexicalException, SyntaxException {
		String input = "sjsurya (a+b,m+n)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Ident [name=sjsurya]");
	}

	@Test
	public void testExp28() throws LexicalException, SyntaxException {
		String input = "atan(a+b)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_FunctionAppWithExprArg [function=KW_atan, arg=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]");
	}

	@Test
	public void testProRandom22() throws LexicalException, SyntaxException {
		String input = "sjsurya sjsurya [[x,y]]=123;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Statement_Assign [lhs=name [name=sjsurya, index=Index [e0=Expression_PredefinedName [name=KW_x], e1=Expression_PredefinedName [name=KW_y]]], e=Expression_IntLit [value=123]]]]");
	}

	@Test
	public void testProRandom23() throws LexicalException, SyntaxException {
		String input = "sjsurya sjsurya [[x,y]]=a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Statement_Assign [lhs=name [name=sjsurya, index=Index [e0=Expression_PredefinedName [name=KW_x], e1=Expression_PredefinedName [name=KW_y]]], e=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]");
	}

	@Test
	public void testProRandom24() throws LexicalException, SyntaxException {
		String input = "sjsurya sjsurya <- @123+456;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Statement_In [name=sjsurya, source=Source_CommandLineParam [paramNum=Expression_Binary [e0=Expression_IntLit [value=123], op=OP_PLUS, e1=Expression_IntLit [value=456]]]]]]");
	}

	@Test
	public void testProRandom25() throws LexicalException, SyntaxException {
		String input = "sjsurya sjsurya -> sjsurya;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Statement_Out [name=sjsurya, sink=Sink_Ident [name=sjsurya]]]]");
	}

	@Test
	public void testExp29() throws LexicalException, SyntaxException {
		String input = "x != y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_NEQ, e1=Expression_PredefinedName [name=KW_y]]");
	}

	@Test
	public void testExp30() throws LexicalException, SyntaxException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_IntLit [value=2]");
	}

	@Test
	public void testExp31() throws LexicalException, SyntaxException {
		String input = "f*d++c+d-d-c+b/a";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Ident [name=f], op=OP_TIMES, e1=Expression_Ident [name=d]], op=OP_PLUS, e1=Expression_Unary [op=OP_PLUS, e=Expression_Ident [name=c]]], op=OP_PLUS, e1=Expression_Ident [name=d]], op=OP_MINUS, e1=Expression_Ident [name=d]], op=OP_MINUS, e1=Expression_Ident [name=c]], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Ident [name=b], op=OP_DIV, e1=Expression_PredefinedName [name=KW_a]]]");
	}

	@Test
	public void testProRandom26() throws LexicalException, SyntaxException {
		String input = "idents image [+-x|y,+-x|y] idents <- srcIdents; image idents; image idents<-\"StringLiteral\";stidents->sinkHere;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=idents, decsAndStatements=[Declaration_Image [xSize=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_PredefinedName [name=KW_x]]], op=OP_OR, e1=Expression_PredefinedName [name=KW_y]], ySize=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_PredefinedName [name=KW_x]]], op=OP_OR, e1=Expression_PredefinedName [name=KW_y]], name=idents, source=Source_Ident [name=srcIdents]], Declaration_Image [xSize=null, ySize=null, name=idents, source=null], Declaration_Image [xSize=null, ySize=null, name=idents, source=Source_StringLiteral [fileOrUrl=StringLiteral]], Statement_Out [name=stidents, sink=Sink_Ident [name=sinkHere]]]]");
	}

	@Test
	public void testExp32() throws LexicalException, SyntaxException {
		String input = "a + b / c";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Ident [name=b], op=OP_DIV, e1=Expression_Ident [name=c]]]");
	}

	@Test
	public void testExp33() throws LexicalException, SyntaxException {
		String input = "d>c==f<e";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Ident [name=d], op=OP_GT, e1=Expression_Ident [name=c]], op=OP_EQ, e1=Expression_Binary [e0=Expression_Ident [name=f], op=OP_LT, e1=Expression_Ident [name=e]]]");
	}

	@Test
	public void testProRandom27() throws LexicalException, SyntaxException {
		String input = "prog int k = sin(c+b/2);//comment\nint k = sin(c+b/2);";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=prog, decsAndStatements=[Declaration_Variable [type=[KW_int,int,5,3,1,6], name=k, e=Expression_FunctionAppWithExprArg [function=KW_sin, arg=Expression_Binary [e0=Expression_Ident [name=c], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Ident [name=b], op=OP_DIV, e1=Expression_IntLit [value=2]]]]], Declaration_Variable [type=[KW_int,int,34,3,2,1], name=k, e=Expression_FunctionAppWithExprArg [function=KW_sin, arg=Expression_Binary [e0=Expression_Ident [name=c], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Ident [name=b], op=OP_DIV, e1=Expression_IntLit [value=2]]]]]]]");
	}

	@Test
	public void testExp34() throws LexicalException, SyntaxException {
		String input = "+1*+1/+1%+1++1*+1/+1%+1-+1*+1/+1%+1";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]]");
	}

	@Test
	public void testProRandom28() throws LexicalException, SyntaxException {
		String input = "abcd file idents = \"this is literal\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=abcd, decsAndStatements=[Declaration_SourceSink [type=KW_file, name=idents, source=Source_StringLiteral [fileOrUrl=this is literal]]]]");
	}

	@Test
	public void testProRandom29() throws LexicalException, SyntaxException {
		String input = "abcd image [+-x|y,-+y|x] abcd <- @ +-x|y;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=abcd, decsAndStatements=[Declaration_Image [xSize=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_PredefinedName [name=KW_x]]], op=OP_OR, e1=Expression_PredefinedName [name=KW_y]], ySize=Expression_Binary [e0=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_y]]], op=OP_OR, e1=Expression_PredefinedName [name=KW_x]], name=abcd, source=Source_CommandLineParam [paramNum=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_MINUS, e=Expression_PredefinedName [name=KW_x]]], op=OP_OR, e1=Expression_PredefinedName [name=KW_y]]]]]]");
	}

	@Test
	public void testExp35() throws LexicalException, SyntaxException {
		String input = "x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y & x / y - x * y >= x / y - x * y != x / y - x * y >= x / y - x * y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]], op=OP_GE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_DIV, e1=Expression_PredefinedName [name=KW_y]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_TIMES, e1=Expression_PredefinedName [name=KW_y]]]]]]");
	}

	@Test
	public void testExp36() throws LexicalException, SyntaxException {
		String input = "+x?(true):sin[false,false]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_x]], trueExpression=Expression_BooleanLit [value=true], falseExpression=Expression_FunctionAppWithIndexArg [function=KW_sin, arg=Index [e0=Expression_BooleanLit [value=false], e1=Expression_BooleanLit [value=false]]]]");
	}

	@Test
	public void testProRandom30() throws LexicalException, SyntaxException {
		String input = "sjsurya sjsurya = a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Statement_Assign [lhs=name [name=sjsurya, index=null], e=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]");
	}

	@Test
	public void testProRandom31() throws LexicalException, SyntaxException {
		String input = "sjsurya int p=a+b;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Variable [type=[KW_int,int,8,3,1,9], name=p, e=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]]]]]");
	}

	@Test
	public void testProRandom32() throws LexicalException, SyntaxException {
		String input = "sjsurya image [m+p,b+y] jh <- \"sjsurya\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Image [xSize=Expression_Binary [e0=Expression_Ident [name=m], op=OP_PLUS, e1=Expression_Ident [name=p]], ySize=Expression_Binary [e0=Expression_Ident [name=b], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], name=jh, source=Source_StringLiteral [fileOrUrl=sjsurya]]]]");
	}

	@Test
	public void testProRandom33() throws LexicalException, SyntaxException {
		String input = "sjsurya image [m+p,b+y] jh;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Image [xSize=Expression_Binary [e0=Expression_Ident [name=m], op=OP_PLUS, e1=Expression_Ident [name=p]], ySize=Expression_Binary [e0=Expression_Ident [name=b], op=OP_PLUS, e1=Expression_PredefinedName [name=KW_y]], name=jh, source=null]]]");
	}

	@Test
	public void testProRandom34() throws LexicalException, SyntaxException {
		String input = "sjsurya file ban=\"bans\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_SourceSink [type=KW_file, name=ban, source=Source_StringLiteral [fileOrUrl=bans]]]]");
	}

	@Test
	public void testProRandom35() throws LexicalException, SyntaxException {
		String input = "sjsurya image sjsurya;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=sjsurya, source=null]]]");
	}

	@Test
	public void testExp37() throws LexicalException, SyntaxException {
		String input = "2?1:2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Conditional [condition=Expression_IntLit [value=2], trueExpression=Expression_IntLit [value=1], falseExpression=Expression_IntLit [value=2]]");
	}

	@Test
	public void testExp38() throws LexicalException, SyntaxException {
		String input = "Kw_a";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_Ident [name=Kw_a]");
	}

	@Test
	public void testExp39() throws LexicalException, SyntaxException {
		String input = "1234|cos[(a<b),(g!=h)]&x%k==++DEF_X!=Z------!!!!!k[a+b,a%b]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_IntLit [value=1234], op=OP_OR, e1=Expression_Binary [e0=Expression_FunctionAppWithIndexArg [function=KW_cos, arg=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_LT, e1=Expression_Ident [name=b]], e1=Expression_Binary [e0=Expression_Ident [name=g], op=OP_NEQ, e1=Expression_Ident [name=h]]]], op=OP_AND, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_x], op=OP_MOD, e1=Expression_Ident [name=k]], op=OP_EQ, e1=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_PredefinedName [name=KW_DEF_X]]]], op=OP_NEQ, e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_Z], op=OP_MINUS, e1=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_MINUS, e=Expression_Unary [op=OP_EXCL, e=Expression_Unary [op=OP_EXCL, e=Expression_Unary [op=OP_EXCL, e=Expression_Unary [op=OP_EXCL, e=Expression_Unary [op=OP_EXCL, e=Expression_PixelSelector [name=k, index=Index [e0=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_PLUS, e1=Expression_Ident [name=b]], e1=Expression_Binary [e0=Expression_PredefinedName [name=KW_a], op=OP_MOD, e1=Expression_Ident [name=b]]]]]]]]]]]]]]]]]]");
	}

	@Test
	public void testProRandom36() throws LexicalException, SyntaxException {
		String input = "sjsurya file filename=\"temp\";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_SourceSink [type=KW_file, name=filename, source=Source_StringLiteral [fileOrUrl=temp]]]]");
	}

	@Test
	public void testProRandom37() throws LexicalException, SyntaxException {
		String input = "sjsurya image ImagePNGFile;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Image [xSize=null, ySize=null, name=ImagePNGFile, source=null]]]");
	}

	@Test
	public void testProRandom38() throws LexicalException, SyntaxException {
		String input = "sjsurya boolean b=true;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=sjsurya, decsAndStatements=[Declaration_Variable [type=[KW_boolean,boolean,8,7,1,9], name=b, e=Expression_BooleanLit [value=true]]]]");
	}

	@Test
	public void testExp40() throws LexicalException, SyntaxException {
		String input = "23456";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(), "Expression_IntLit [value=23456]");
	}

	@Test
	public void testProRandom39() throws LexicalException, SyntaxException {
		String input = "program int count=0;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.toString(),
				"Program [name=program, decsAndStatements=[Declaration_Variable [type=[KW_int,int,8,3,1,9], name=count, e=Expression_IntLit [value=0]]]]");
	}

	@Test
	public void testExp41() throws LexicalException, SyntaxException {
		String input = "+1*+1/+1%+1++1*+1/+1%+1-+1*+1/+1%+1<+1*+1/+1%+1++1*+1/+1%+1-+1*+1/+1%+1<=+1*+1/+1%+1++1*+1/+1%+1-+1*+1/+1%+1";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]], op=OP_LT, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]]], op=OP_LE, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_PLUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]], op=OP_MINUS, e1=Expression_Binary [e0=Expression_Binary [e0=Expression_Binary [e0=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_DIV, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]], op=OP_MOD, e1=Expression_Unary [op=OP_PLUS, e=Expression_IntLit [value=1]]]]]");
	}

	@Test
	public void testExp42() throws LexicalException, SyntaxException {
		String input = "true==false==true";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression expAst = parser.expression();
		show(expAst);
		assertEquals(expAst.toString(),
				"Expression_Binary [e0=Expression_Binary [e0=Expression_BooleanLit [value=true], op=OP_EQ, e1=Expression_BooleanLit [value=false]], op=OP_EQ, e1=Expression_BooleanLit [value=true]]");
	}

	@Test
	public void Htest2() throws LexicalException, SyntaxException {
		String input = "prog int k=54;";
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
		assertNotNull(dec.e);
		Expression_IntLit intLit = (Expression_IntLit) dec.e;
		assertEquals(54, intLit.value);
	}

	@Test
	public void test3() throws LexicalException, SyntaxException {
		String input = "prog int z = z + 0;";
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
		assertEquals("z", dec.name);
		assertNotNull(dec.e);
		Expression_Binary expBinary = (Expression_Binary) dec.e;
		Expression_Ident e0 = (Expression_Ident) expBinary.e0;
		assertEquals(e0.name, "z");
		assertEquals(Kind.OP_PLUS, expBinary.op);
		Expression_IntLit intLit = (Expression_IntLit) expBinary.e1;
		assertEquals(0, intLit.value);
	}

	@Test
	public void testAssignment11() throws LexicalException, SyntaxException {
		String input = "prog boolean z = false;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(KW_boolean, dec.type.kind);
		assertEquals("z", dec.name);
		assertNotNull(dec.e);
		Expression_BooleanLit expBinary = (Expression_BooleanLit) dec.e;
		assertEquals(expBinary.value, false);
	}

	@Test
	public void testParseDeclSS1() throws LexicalException, SyntaxException {
		String input = "prog url link = \" https://www.facebook.com \";";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		Declaration_SourceSink dss = (Declaration_SourceSink) ast.decsAndStatements.get(0);
		assertEquals(dss.name, "link");
		assertEquals(dss.type, Kind.KW_url);
		Source_StringLiteral sl = (Source_StringLiteral) dss.source;
		assertEquals(sl.fileOrUrl, " https://www.facebook.com ");
	}

	@Test
	public void testFunctionApp1() throws LexicalException, SyntaxException {
		String input = "sin(45)";
		show(input);
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		Parser parser = new Parser(scanner);
		Expression ast = parser.functionApplication();
		show(ast);
		Expression_FunctionAppWithExprArg ef = (Expression_FunctionAppWithExprArg) ast;
		assertEquals(ef.function, Kind.KW_sin);
		Expression_IntLit arg = (Expression_IntLit) ef.arg;
		assertEquals(arg.value, 45);
	}

	@Test
	public void testFunctionApp2() throws LexicalException, SyntaxException {
		String input = "cart_x[0,1]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression ast = parser.functionApplication();
		show(ast);
		Expression_FunctionAppWithIndexArg ef = (Expression_FunctionAppWithIndexArg) ast;
		assertEquals(ef.function, Kind.KW_cart_x);
		Index index = ef.arg;
		Expression_IntLit e0 = (Expression_IntLit) index.e0;
		assertEquals(e0.value, 0);
		Expression_IntLit e1 = (Expression_IntLit) index.e1;
		assertEquals(e1.value, 1);
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
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "imageProgram");

		// Declaration statement start
		Declaration_Image dv1 = (Declaration_Image) ast.decsAndStatements.get(0);
		assertEquals(dv1.name, "imageName");
		assertNull(dv1.xSize);
		assertNull(dv1.ySize);
		assertNull(dv1.source);

		Statement_Out dv2 = (Statement_Out) ast.decsAndStatements.get(1);
		assertEquals(dv2.name, "imageName");
		Sink_Ident si2 = (Sink_Ident) dv2.sink;
		assertEquals(si2.name, "abcdpng");
	}

	@Test
	public void testcase19() throws SyntaxException, LexicalException {
		String input = "declaration int xyz;\n boolean zya;\n image imagename;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "declaration");
		assertEquals(ast.firstToken.kind, IDENTIFIER);

		// Declaration statements start
		Declaration_Variable dv1 = (Declaration_Variable) ast.decsAndStatements.get(0);
		assertEquals(dv1.name, "xyz");
		assertEquals(dv1.type.kind, KW_int);
		assertNull(dv1.e);

		Declaration_Variable dv2 = (Declaration_Variable) ast.decsAndStatements.get(1);
		assertEquals(dv2.name, "zya");
		assertEquals(dv2.type.kind, KW_boolean);
		assertNull(dv2.e);

		Declaration_Image dv3 = (Declaration_Image) ast.decsAndStatements.get(2);
		assertEquals(dv3.name, "imagename");
		assertNull(dv3.source);
		assertNull(dv3.xSize);
		assertNull(dv3.ySize);

		// Declaration statement end
	}

	@Test
	public void testcase18() throws SyntaxException, LexicalException {
		String input = "isurl url urlname;"; // Should fail for url as url can only be initalised
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
	public void testcase16_parse() throws SyntaxException, LexicalException {
		String input = "isFile file filepng=\"abcd\"; \n @expr=12; url filepng=@expr; \n url filepng=abcdefg";
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
	public void testcase16() throws SyntaxException, LexicalException {
		String input = "isFile file filepng=\"abcd\"; \n @expr=12; url filepng=@expr; \n url filepng=abcdefg";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "isFile");
		assertEquals(ast.decsAndStatements.size(), 1);
		assertEquals(ast.firstToken.kind, IDENTIFIER);

		// Declaration Statements
		Declaration_SourceSink ds = (Declaration_SourceSink) ast.decsAndStatements.get(0);
		assertEquals(ds.type, KW_file);
		assertEquals(ds.name, "filepng");
		Source_StringLiteral s = (Source_StringLiteral) ds.source;
		assertEquals(s.fileOrUrl, "abcd");
		// assertEquals(ast.)
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
	public void testcase14_parse() throws SyntaxException, LexicalException {
		String input = "isUrl url filepng=\"abcd\"; \n @expr=12; url awesome=@expr; \n url filepng=abcdefg";
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
	public void testcase14() throws SyntaxException, LexicalException {
		String input = "isUrl url filepng=\"abcd\"; \n @expr=12; url awesome=@expr; \n url filepng=abcdefg";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "isUrl");
		assertEquals(ast.decsAndStatements.size(), 1);
		Declaration_SourceSink dss = (Declaration_SourceSink) ast.decsAndStatements.get(0);
		assertEquals(dss.name, "filepng");
		assertEquals(dss.type, KW_url);
		Source_StringLiteral s = (Source_StringLiteral) dss.source;
		assertEquals(s.fileOrUrl, "abcd");
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
	public void testprog57() throws LexicalException, SyntaxException {
		String input = "a * a ?2:3";
		show(input);
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and initialize it
		show(scanner); // Display the Scanner
		Parser parser = new Parser(scanner); //
		Expression ast = parser.expression();
		show(ast);
		Expression_Conditional dex = (Expression_Conditional) ast;
		Expression_Binary dex1 = (Expression_Binary) dex.condition;
		Expression_PredefinedName ex = (Expression_PredefinedName) dex1.e0;
		Expression_PredefinedName ex1 = (Expression_PredefinedName) dex1.e1;
		Expression_IntLit ex2 = (Expression_IntLit) dex.trueExpression;
		Expression_IntLit ex3 = (Expression_IntLit) dex.falseExpression;
		assertEquals(KW_a, ex.kind);
		assertEquals(OP_TIMES, dex1.op);
		assertEquals(KW_a, ex1.kind);
		assertEquals(2, ex2.value);
		assertEquals(3, ex3.value);
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
	public void testcase20_aa() throws SyntaxException, LexicalException {
		String input = "imageProgram image imageName;" + "\n imageName->abcdpng; " + "\n imageName -> SCREEN; "
				+ "\n imageName <- \"awesome\";" + "\n imageName <- @express; \n" + "\n imageName <- abcdpng;"; // Image
																												// related
																												// Test
																												// cases
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.program(); // Parse the program
		show(ast);
		assertEquals(ast.name, "imageProgram");

		// Declaration statement start
		Declaration_Image dv1 = (Declaration_Image) ast.decsAndStatements.get(0);
		assertEquals(dv1.name, "imageName");
		assertNull(dv1.xSize);
		assertNull(dv1.ySize);
		assertNull(dv1.source);

		Statement_Out dv2 = (Statement_Out) ast.decsAndStatements.get(1);
		assertEquals(dv2.name, "imageName");
		Sink_Ident si2 = (Sink_Ident) dv2.sink;
		assertEquals(si2.name, "abcdpng");
	}
}