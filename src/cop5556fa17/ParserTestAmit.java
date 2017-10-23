package cop5556fa17;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.AST.*;

import cop5556fa17.Parser.SyntaxException;

import static cop5556fa17.Scanner.Kind.*;

public class ParserTestAmit {

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
	public void exp1() throws SyntaxException, LexicalException {
		String input = "Z-old";
		Expression e = null;
		try {
			e = (new Parser(new Scanner(input).scan())).expression();
		} catch (cop5556fa17.Parser.SyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		show(e);
		assertEquals(Expression_Binary.class, e.getClass());
		Expression_Binary ebin = (Expression_Binary) e;
		assertEquals(Expression_PredefinedName.class, ebin.e0.getClass());
		assertEquals(KW_Z, ((Expression_PredefinedName) (ebin.e0)).kind);
		assertEquals(Expression_Ident.class, ebin.e1.getClass());
		assertEquals("old", ((Expression_Ident) (ebin.e1)).name);
		assertEquals(OP_MINUS, ebin.op);
	}

	@Test
	public void expression1() throws SyntaxException, LexicalException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression exp = null;
		try {
			exp = parser.expression();
		} catch (cop5556fa17.Parser.SyntaxException e1) {
			System.out.println("Syntax Exception for input= " + input);
		}
		show("Expression Returned= " + exp);
		assertEquals(Expression_IntLit.class, exp.getClass());
		Expression_IntLit exp_intLit = (Expression_IntLit) exp;
		assertEquals(2, exp_intLit.value);
	}
	
	/**
	 * **************************************************************************************************************************************************
	 * **********************************************     SRISHTI's TESTS (In Progress)      ************************************************************
	 * **************************************************************************************************************************************************
	 * */

	/**
	 * Selector ::= Expression COMMA Expression function first
	 */
	@Test
	public void selector() throws SyntaxException, LexicalException {
		String input = "++++x , ++++x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);

		Index index = parser.selector();

		assertEquals(Expression_Unary.class, index.e0.getClass());
		assertEquals(Expression_Unary.class, index.e1.getClass());
		assertEquals(OP_PLUS, ((Expression_Unary) (index.e0)).op);
		assertEquals(Expression_Unary.class, ((Expression_Unary) (index.e0)).e.getClass());
		assertEquals(KW_x,
				((Expression_PredefinedName) ((Expression_Unary) ((Expression_Unary) ((Expression_Unary) ((Expression_Unary) (index.e0)).e).e).e).e).kind);
	}

	/**
	 * RaSelector ::= KW_r COMMA KW_A
	 */
	@Test
	public void testRaSelector() throws SyntaxException, LexicalException {
		String input = "r , A";
		// String input = "r , AB";
		// String input = "r ; A";
		// String input = "R , A";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);

		Index index = parser.raSelector();

		show(index);
		assertEquals(Expression_PredefinedName.class, index.e0.getClass());
		assertEquals(Expression_PredefinedName.class, index.e1.getClass());
		assertEquals(KW_r, ((Expression_PredefinedName) index.e0).kind);
		assertEquals(KW_A, ((Expression_PredefinedName) index.e1).kind);
	}

	/**
	 * XySelector ::= KW_x COMMA KW_y
	 */
	@Test
	public void xySelector() throws SyntaxException, LexicalException {
		String input = "x,y,x,y";
		// String input = "x , y";
		// String input = "xv,y";
		// String input = "x,yb";
		// String input = "x y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);

		Index index = parser.xySelector();

		show(index);
		assertEquals(Expression_PredefinedName.class, index.e0.getClass());
		assertEquals(Expression_PredefinedName.class, index.e1.getClass());
		assertEquals(KW_x, ((Expression_PredefinedName) index.e0).kind);
		assertEquals(KW_y, ((Expression_PredefinedName) index.e1).kind);
	}

	/**
	 * LhsSelector ::= LSQUARE ( XySelector | RaSelector ) RSQUARE
	 */
	@Test
	public void lhsSelector() throws SyntaxException, LexicalException {
		String input = "[x,y]";
		// String input = "[r,A]";
		// String input = "[r,y]";
		// String input = "[][]";
		// String input = "[x,y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Index index = parser.lhsSelector();
		
		show(index);
		assertEquals(Expression_PredefinedName.class, index.e0.getClass());
		assertEquals(Expression_PredefinedName.class, index.e1.getClass());
		assertEquals(KW_x, ((Expression_PredefinedName) index.e0).kind);
		assertEquals(KW_y, ((Expression_PredefinedName) index.e1).kind);
	}

	/**
	 * FunctionName ::= KW_sin | KW_cos | KW_atan | KW_abs | KW_cart_x | KW_cart_y |
	 * KW_polar_a | KW_polar_r
	 */
	@Test
	public void functionName() throws SyntaxException, LexicalException {
		// String input = "sin";
		// String input = "cos";
		// String input = "atan";
		// String input = "abs";
		// String input = "cart_x";
		// String input = "cart_y";
		// String input = "polar_a";
		// String input = "polar_r";
		String input = "sin x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.functionName();
	}

	/**
	 * FunctionApplication ::= FunctionName LPAREN Expression RPAREN | FunctionName
	 * LSQUARE Selector RSQUARE
	 */
	@Test
	public void functionApplication_WithExprArg() throws SyntaxException, LexicalException {
		String input = "sin(r)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression exp = null;
		try {
			exp = parser.functionApplication();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
		
		show(exp);
		assertEquals(Expression_FunctionAppWithExprArg.class, exp.getClass());
		assertEquals(KW_sin, ((Expression_FunctionAppWithExprArg)exp).function);
		assertEquals(KW_r, ((Expression_PredefinedName)((Expression_FunctionAppWithExprArg)exp).arg).kind);
	}
	
	/**
	 * FunctionApplication ::= FunctionName LPAREN Expression RPAREN | FunctionName
	 * LSQUARE Selector RSQUARE
	 */
	@Test
	public void functionApplication_WithIndexArg() throws SyntaxException, LexicalException {
		String input = "sin[x,x]";
//		String input = "sin[y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression exp = null;
		try {
			exp = parser.functionApplication();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
		
		show(exp);
		assertEquals(Expression_FunctionAppWithIndexArg.class, exp.getClass());
		assertEquals(KW_sin, ((Expression_FunctionAppWithIndexArg)exp).function);
		assertEquals(Expression_PredefinedName.class, ((Index)((Expression_FunctionAppWithIndexArg)exp).arg).e0.getClass());
		assertEquals(KW_x, ((Expression_PredefinedName)((Index)((Expression_FunctionAppWithIndexArg)exp).arg).e0).kind);
	}

	/**
	 * Lhs::= IDENTIFIER ( LSQUARE LhsSelector RSQUARE | e )
	 */
	@Test
	public void testLHS() throws SyntaxException, LexicalException {
		String input = "togepi[[x,y]]";
		// String input = "meowth";
		// String input = "onyx []";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		LHS lhs = parser.lhs();
		
		show(lhs);
		assertEquals("togepi", lhs.name);
		assertEquals(Expression_PredefinedName.class, lhs.index.e0.getClass());
		assertEquals(Expression_PredefinedName.class, lhs.index.e1.getClass());
		assertEquals(KW_x, ((Expression_PredefinedName)lhs.index.e0).kind);
	}

	/**
	 * IdentOrPixelSelectorExpression::= IDENTIFIER LSQUARE Selector RSQUARE |
	 * IDENTIFIER
	 */
	@Test
	public void IdentOrPixelSelectorExpression() throws SyntaxException, LexicalException {
		// String input = "togepi[[xxx,yyyy]]";
//		String input = "meowth";
		// String input = "onyx []";
		String input = "togepi[x,y]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);		
		Parser parser = new Parser(scanner);
		Expression e = parser.identOrPixelSelectorExpression();
		
		show(e);
		assertEquals(Expression_PixelSelector.class, e.getClass());
		assertEquals("togepi",((Expression_PixelSelector)e).name);
		assertEquals(Index.class,((Expression_PixelSelector)e).index.getClass());
		assertEquals(((Expression_PixelSelector)e).index.e0.getClass(),Expression_PredefinedName.class);
		assertEquals(((Expression_PixelSelector)e).index.e1.getClass(),Expression_PredefinedName.class);
	}

	/**
	 * Primary ::= INTEGER_LITERAL | LPAREN Expression RPAREN | FunctionApplication
	 * | BOOLEAN
	 */
	@Test
	public void primary() throws SyntaxException, LexicalException {
		// String input = "1234";
		// String input = "(plp)";
		// String input = "( x x )";
		String input = "true";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Expression e = parser.primary();
		
		show(e);
		assertEquals(Expression_BooleanLit.class, e.getClass());
		assertEquals(((Expression_BooleanLit)e).value,true);
	}
	
	/**
	 * Primary ::= INTEGER_LITERAL | LPAREN Expression RPAREN | FunctionApplication
	 * | BOOLEAN
	 */
	@Test
	public void primary2() throws SyntaxException, LexicalException {
		// String input = "1234";
		// String input = "(plp)";
		// String input = "( x x )";
		String input = "false";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Expression e = parser.primary();
		
		show(e);
		assertEquals(Expression_BooleanLit.class, e.getClass());
		assertEquals(((Expression_BooleanLit)e).value,false);
	}
	
	/**
	 * Primary ::= INTEGER_LITERAL | LPAREN Expression RPAREN | FunctionApplication
	 * | BOOLEAN
	 */
	@Test
	public void primary3() throws SyntaxException, LexicalException {
		String input = "(!R | !DEF_X & 5)";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Expression e = parser.primary();
		
		show(e);
//		Expected output: 
//		Expression_Binary [e0=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_R]], op=OP_OR, e1=Expression_Binary [e0=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_X]], op=OP_AND, e1=Expression_IntLit [value=5]]]

	}

	/**
	 * UnaryExpressionNotPlusMinus ::= OP_EXCL UnaryExpression | Primary |
	 * IdentOrPixelSelectorExpression | KW_x | KW_y | KW_r | KW_a | KW_X | KW_Y |
	 * KW_Z | KW_A | KW_R | KW_DEF_X | KW_DEF_Y
	 */
	@Test
	public void unaryExpressionNotPlusMinus() throws SyntaxException, LexicalException {
		String input = "!-5 ";
		// String input = "1234";
		// String input = "togepi[[xxx,yyyy]]";
		// String input = "Z";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Expression exp = parser.unaryExpressionNotPlusMinus();
		
		show(exp);
		assertEquals(Expression_Unary.class , exp.getClass());
		assertEquals(OP_EXCL , ((Expression_Unary)exp).op);
		assertEquals(Expression_Unary.class , ((Expression_Unary)exp).e.getClass());
		assertEquals(OP_MINUS , ((Expression_Unary)((Expression_Unary)exp).e).op);
		assertEquals(Expression_IntLit.class , ((Expression_Unary)((Expression_Unary)exp).e).e.getClass());
		assertEquals(5 , ((Expression_IntLit)((Expression_Unary)((Expression_Unary)exp).e).e).value);
	}

	/**
	 * UnaryExpression ::= OP_PLUS UnaryExpression | OP_MINUS UnaryExpression |
	 * UnaryExpressionNotPlusMinus
	 */
	@Test
	public void unaryExpression() throws SyntaxException, LexicalException {
		// String input = "++";
		// String input = "--";
		String input = "+++!-5";
		// String input = "Z";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		
		Expression exp = parser.unaryExpression();
		
		show(exp);
//		Expected Output:
//		Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_EXCL, e=Expression_Unary [op=OP_MINUS, e=Expression_IntLit [value=5]]]]]]
	}

	/**
	 * MultExpression := UnaryExpression ( ( OP_TIMES | OP_DIV | OP_MOD )
	 * UnaryExpression )*
	 */
	@Test
	public void multExpression() throws SyntaxException, LexicalException {
		String input = "!DEF_Y * +!DEF_Y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Expression exp = parser.multExpression();
		show (exp);
		assertEquals(Expression_Binary.class , exp.getClass());
		assertEquals(Expression_Unary.class , ((Expression_Binary)exp).e0.getClass());
		assertEquals(Expression_Unary.class , ((Expression_Binary)exp).e1.getClass());
		assertEquals(Expression_PredefinedName.class , ((Expression_Unary)((Expression_Binary)exp).e0).e.getClass());
		assertEquals(Expression_Unary.class , ((Expression_Unary)((Expression_Binary)exp).e1).e.getClass());
		assertEquals(Expression_PredefinedName.class , ((Expression_Unary)((Expression_Unary)((Expression_Binary)exp).e1).e).e.getClass());
//		Expected Tree
//		Expression_Binary [e0=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_Y]], op=OP_TIMES, e1=Expression_Unary [op=OP_PLUS, e=Expression_Unary [op=OP_EXCL, e=Expression_PredefinedName [name=KW_DEF_Y]]]]
	}

	/**
	 * AddExpression ::= MultExpression ( (OP_PLUS | OP_MINUS ) MultExpression )*
	 */
	@Test
	public void addExpression() throws SyntaxException, LexicalException {
		String input = "x*x + x%x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.addExpression();
	}

	/**
	 * RelExpression ::= AddExpression ( ( OP_LT | OP_GT | OP_LE | OP_GE )
	 * AddExpression)*
	 */
	@Test
	public void relExpression() throws SyntaxException, LexicalException {
		String input = "x*x + x%x < x*x + x%x";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.relExpression();
	}

	/**
	 * EqExpression ::= RelExpression ( (OP_EQ | OP_NEQ ) RelExpression )*
	 */
	@Test
	public void eqExpression() throws SyntaxException, LexicalException {
		String input = "y*y - y*y > y*y - y*y != y*y - y*y > y*y - y*y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.eqExpression();
	}

	/**
	 * AndExpression ::= EqExpression ( OP_AND EqExpression )*
	 */
	@Test
	public void andExpression() throws SyntaxException, LexicalException {
		String input = "y*y - y*y > y*y - y*y = y*y - y*y > y*y - y*y & y*y - y*y > y*y - y*y = y*y - y*y > y*y - y*y & y*y - y*y > y*y - y*y = y*y - y*y > y*y - y*y ";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.andExpression();
	}

	/**
	 * OrExpression ::= AndExpression ( OP_OR AndExpression)*
	 */
	@Test
	public void orExpression() throws SyntaxException, LexicalException {
		String input = "y*y - y*y > y*y - y*y = y*y - y*y > y*y - y*y | 8";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.orExpression();
	}

	/**
	 * Expression ::= OrExpression OP_Q Expression OP_COLON Expression |
	 * OrExpression
	 */
	@Test
	public void testExpression() throws SyntaxException, LexicalException {
		// String input = "x|x:@";
		String input = "x?x:@";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.expression(); // Parse the program
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	/**
	 * AssignmentStatement ::= Lhs OP_ASSIGN Expression
	 */
	@Test
	public void assignmentStatement() throws SyntaxException, LexicalException {
		String input = "meowth = 5";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.assignmentStatement();
	}

	/**
	 * ImageInStatement ::= IDENTIFIER OP_LARROW Source
	 */
	@Test
	public void imageInStatement() throws SyntaxException, LexicalException {
		String input = "String <- @Y";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.imageInStatement();
	}

	/**
	 * Sink ::= IDENTIFIER | KW_SCREEN //ident must be file
	 */
	@Test
	public void sink() throws SyntaxException, LexicalException {
		String input = "SCREEN SCREEN";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.sink();
	}

	/**
	 * ImageOutStatement ::= IDENTIFIER OP_RARROW Sink
	 */
	@Test
	public void imageOutStatement() throws SyntaxException, LexicalException {
		String input = "string -> SCREEN";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.imageOutStatement();
	}

	/**
	 * Statement ::= AssignmentStatement | ImageOutStatement | ImageInStatement
	 */
	@Test
	public void statement() throws SyntaxException, LexicalException {
		String input = "String [ [x,y]] = 789";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.statement();
	}

	/**
	 * ImageDeclaration ::= KW_image (LSQUARE Expression COMMA Expression RSQUARE |
	 * e) IDENTIFIER ( OP_LARROW Source | e )
	 */
	@Test
	public void imageDeclaration() throws SyntaxException, LexicalException {
		String input = "image [ ++++x , ++++x ] string";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.imageDeclaration();
	}

	/**
	 * SourceSinkType := KW_url | KW_file
	 */
	@Test
	public void sourceSinkType() throws SyntaxException, LexicalException {
		String input = "file";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.sourceSinkType();
	}

	/**
	 * Source ::= STRING_LITERAL | IDENTIFIER | OP_AT Expression
	 */
	@Test
	public void source() throws SyntaxException, LexicalException {
		String input = "@5692";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.source();
	}

	/**
	 * SourceSinkDeclaration ::= SourceSinkType IDENTIFIER OP_ASSIGN Source
	 */
	@Test
	public void sourceSinkDeclaration() throws SyntaxException, LexicalException {
		String input = "url is = @ 5";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.sourceSinkDeclaration();
	}

	/**
	 * VarType ::= KW_int | KW_boolean
	 */
	@Test
	public void varType() throws SyntaxException, LexicalException {
		String input = "boolean";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.varType();
	}

	/**
	 * VariableDeclaration ::= VarType IDENTIFIER ( OP_ASSIGN Expression | e )
	 */
	@Test
	public void variableDeclaration() throws SyntaxException, LexicalException {
		String input = "boolean pikachu = 10";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.variableDeclaration();
	}

	/**
	 * Declaration :: = VariableDeclaration | ImageDeclaration |
	 * SourceSinkDeclaration
	 */
	@Test
	public void declaration() throws SyntaxException, LexicalException {
		String input = "int blah = mmoreBlah";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.declaration();
	}

	/**
	 * Program ::= IDENTIFIER ( Declaration SEMI | Statement SEMI )*
	 */
	@Test
	public void testProgram() throws SyntaxException, LexicalException {
		String input = "KW_sin( )";
		// String input = "KW_sin[ ]";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.program();
	}

	@Test
	public void expression3() throws SyntaxException, LexicalException {
		String input = "x * 43 OP_OR 2 + 2";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		parser.expression();
	}

	@Test
	public void testcase55() throws SyntaxException, LexicalException {
		String input = "(6*2/23/4*22*sin(x))||(abs(6*2*12)+cart_x[x,y]+cart_y[(6/23),(7/23)]+polar_a[6/2/2,2/3/4]+polar_r(z))&true";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser Parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			Parser.expression();
		} catch (SyntaxException e) {
			show(e);
			throw e;
		}
	}

	@Test
	public void testExpRecursion() throws SyntaxException, LexicalException {
		String input = "sin ( Def_X )";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser Parser = new Parser(scanner);
		// thrown.expect(SyntaxException.class);
		Parser.expression();
	}
	
	/**
	 * **************************************************************************************************************************************************
	 * **********************************************          GITANG's TESTS                ************************************************************
	 * **************************************************************************************************************************************************
	 * */
	
	@Test
	public void testDec2() throws LexicalException, SyntaxException {
		String input = "prog file gitang = \"hello\";";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_SourceSink dec = (Declaration_SourceSink) ast.decsAndStatements
				.get(0);  
		assertEquals(KW_file, dec.type);
		assertEquals("gitang", dec.name);
		Source_StringLiteral source_StringLiteral = (Source_StringLiteral) dec.source;
		assertEquals(source_StringLiteral.fileOrUrl,"hello");
	}
	
	
	@Test
	public void testDec3() throws LexicalException, SyntaxException {
		String input = "prog file adhiraj = @ 2 + 3;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_SourceSink dec = (Declaration_SourceSink) ast.decsAndStatements
				.get(0);
		assertEquals(KW_file, dec.type);
		assertEquals("adhiraj", dec.name);
		Source_CommandLineParam src_cmd = (Source_CommandLineParam) dec.source;
		Expression_Binary e = (Expression_Binary)src_cmd.paramNum;
		assertEquals(e.op, OP_PLUS);
		Expression_IntLit inte0 = (Expression_IntLit)e.e0;
		Expression_IntLit inte1 = (Expression_IntLit)e.e1;

		assertEquals(2,inte0.value);
		assertEquals(3,inte1.value);
	}	
	
	@Test
	public void testDec4() throws LexicalException, SyntaxException {
		String input = "prog file adhiraj = god;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_SourceSink dec = (Declaration_SourceSink) ast.decsAndStatements
				.get(0);
		assertEquals(KW_file, dec.type);
		assertEquals("adhiraj", dec.name);
		Source_Ident source_ident = (Source_Ident) dec.source;
		assertEquals(source_ident.name,"god");
	}
	
	@Test
	public void testDec5() throws LexicalException, SyntaxException {
		String input = "prog image gitang;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements
				.get(0);
		assertNull(dec.source);
		assertEquals("gitang", dec.name);
		assertNull(dec.xSize);
		assertNull(dec.ySize);
	}
	
	@Test
	public void testDec6() throws LexicalException, SyntaxException {
		String input = "prog image [2,2] gitang;";
		show(input);
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog");
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements
				.get(0);
		assertNull(dec.source);
		assertEquals("gitang", dec.name);
		
		Expression_IntLit inte0 = (Expression_IntLit)dec.xSize;
		Expression_IntLit inte1 = (Expression_IntLit)dec.ySize;

		assertEquals(2,inte0.value);
		assertEquals(2,inte1.value);
	}
	
	/**
	 * **************************************************************************************************************************************************
	 * **********************************************          Sahil's TESTS                ************************************************************
	 * **************************************************************************************************************************************************
	 * */
	
	@Test
    public void testDec2_sahil() throws LexicalException, SyntaxException
    {
        String input = "prog image xxx;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Declaration_Image dec = (Declaration_Image) ast.decsAndStatements.get(0);
        assertEquals(KW_image, dec.firstToken.kind);
        assertNull(dec.xSize);
        assertNull(dec.ySize);
        assertEquals("xxx", dec.name);
        assertNull(dec.source);
    }
	
    @Test
    public void testDec3_sahil() throws LexicalException, SyntaxException
    {
        String input = "prog int abc=1977893;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
        assertEquals(KW_int, dec.type.kind);
        assertEquals("abc", dec.name);
        Expression_IntLit lit= (Expression_IntLit)dec.e;
        assertEquals(INTEGER_LITERAL,lit.firstToken.kind);
        assertEquals(1977893,lit.value);
    }
    
    @Test
    public void testDec4_sahil() throws LexicalException, SyntaxException
    {
        String input = "prog boolean  jbbhh  = true;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements.get(0);
        assertEquals(KW_boolean, dec.type.kind);
        assertEquals("jbbhh", dec.name);
        Expression_BooleanLit lit= (Expression_BooleanLit)dec.e;
        assertEquals(BOOLEAN_LITERAL,lit.firstToken.kind);
        assertEquals(true,lit.value);
    }
    
    @Test
    public void testDec5_sahil() throws LexicalException, SyntaxException
    {
        String input = "prog boolean  jbbhh  = ture;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        thrown.expect(SyntaxException.class);
        try
        {
            parser.parse();; //Parse the program, which should throw an exception
        }
        catch (SyntaxException e)
        {
            show(e);  //catch the exception and show it
            throw e;  //rethrow for Junit
        }
    }
    
    @Test
    public void testImageOutStmt1() throws LexicalException, SyntaxException
    {
        String input = "prog img->SCREEN;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Statement_Out st_out = (Statement_Out) ast.decsAndStatements.get(0);
        assertEquals(IDENTIFIER, st_out.firstToken.kind);
        assertEquals("img", st_out.name);
        //assertEquals(INTEGER_LITERAL,lit.firstToken.kind);
        assertEquals(KW_SCREEN,st_out.sink.firstToken.kind);
        assertEquals("SCREEN",st_out.sink.firstToken.getText());
    }
    
    @Test
    public void testImageOutStmt2() throws LexicalException, SyntaxException
    {
        String input = "prog img2->pointer;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Statement_Out st_out = (Statement_Out) ast.decsAndStatements.get(0);
        assertEquals(IDENTIFIER, st_out.firstToken.kind);
        assertEquals("img2", st_out.name);
        //assertEquals(INTEGER_LITERAL,lit.firstToken.kind);
        assertEquals(IDENTIFIER,st_out.sink.firstToken.kind);
        assertEquals("pointer",st_out.sink.firstToken.getText());
    }
    
    @Test
    public void testImageImgDec1() throws LexicalException, SyntaxException
    {
        String input = "prog image img<-pointer;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Declaration_Image dec_img = (Declaration_Image) ast.decsAndStatements.get(0);
        assertEquals(KW_image, dec_img.firstToken.kind);
        assertEquals("img", dec_img.name);
        //assertEquals(INTEGER_LITERAL,lit.firstToken.kind);
        assertNull(dec_img.xSize);
        assertNull(dec_img.ySize);
        assertEquals(IDENTIFIER,dec_img.source.firstToken.kind);
        assertEquals("pointer",dec_img.source.firstToken.getText());
    }
    
    @Test
    public void testImageImgDec2() throws LexicalException, SyntaxException
    {
        String input = "prog image img<-@Z;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");//This should have one Declaration_Variable object,
        //which is at position 0 in the decsAndStatements list
        Declaration_Image dec_img = (Declaration_Image) ast.decsAndStatements.get(0);
        assertEquals(KW_image, dec_img.firstToken.kind);
        assertEquals("img", dec_img.name);
        //assertEquals(KW_image, dec_img.);
        //Expression_IntLit lit= (Expression_IntLit)dec.e;
        Source source=(Source)dec_img.source;
        //assertEquals(INTEGER_LITERAL,lit.firstToken.kind);
        assertNull(dec_img.xSize);
        assertNull(dec_img.ySize);
        assertEquals(OP_AT,dec_img.source.firstToken.kind);
        assertEquals("@",dec_img.source.firstToken.getText());
        Source_CommandLineParam param=(Source_CommandLineParam)source;
        Expression_PredefinedName name=(Expression_PredefinedName)param.paramNum;
        assertEquals(KW_Z, name.firstToken.kind);
        assertEquals("Z",name.firstToken.getText());
    }
    
    @Test
    public void testImageImgDec3() throws LexicalException, SyntaxException
    {
        String input = "prog image[X,Y] img<-\"http:www.ufl.edu\";";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");
        Declaration_Image dec_img = (Declaration_Image) ast.decsAndStatements.get(0);
        assertEquals(KW_image, dec_img.firstToken.kind);
        Expression_PredefinedName name=(Expression_PredefinedName)dec_img.xSize;
        assertEquals(KW_X,name.firstToken.kind);
        name=(Expression_PredefinedName)dec_img.ySize;
        assertEquals(KW_Y,name.firstToken.kind);
        assertEquals("img", dec_img.name);
        Source_StringLiteral lit=(Source_StringLiteral)dec_img.source;
        assertEquals(STRING_LITERAL, lit.firstToken.kind);
        assertEquals("http:www.ufl.edu", lit.fileOrUrl);
    }
    
    @Test
    public void testImageImgDec4() throws LexicalException, SyntaxException
    {
        String input = "prog image[X,Y] img;";
        show(input);
        Scanner scanner = new Scanner(input).scan();
        show(scanner);
        Parser parser = new Parser(scanner);
        Program ast = parser.parse();
        show(ast);
        assertEquals(ast.name, "prog");
        Declaration_Image dec_img = (Declaration_Image) ast.decsAndStatements.get(0);
        assertEquals(KW_image, dec_img.firstToken.kind);
        Expression_PredefinedName name=(Expression_PredefinedName)dec_img.xSize;
        assertEquals(KW_X,name.firstToken.kind);
        name=(Expression_PredefinedName)dec_img.ySize;
        assertEquals(KW_Y,name.firstToken.kind);
        assertEquals("img", dec_img.name);
        assertNull(dec_img.source);
    }
    
    /**
	 * **************************************************************************************************************************************************
	 * **********************************************          Raktima's TESTS                ************************************************************
	 * **************************************************************************************************************************************************
	 * */
    
	@Test
	public void testDec2_Raktima() throws LexicalException, SyntaxException {
		String input = "prog boolean tera;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "prog"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
				.get(0);  
		assertEquals(KW_boolean, dec.type.kind);
		assertEquals("tera", dec.name);
		assertNull(dec.e);
	}
	
	@Test
	public void testDec3_Raktima() throws LexicalException, SyntaxException {
		String input = "prog int b=1+2;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
	}
	
	@Test
	public void statement4() throws SyntaxException, LexicalException {
	String input = "raktima raktima [[x,y]]=a+b;";
	Scanner scanner = new Scanner(input).scan(); 
	show(scanner); 
	Parser parser = new Parser(scanner);
	Program ast = parser.parse();
	show(ast);
	}
	
	@Test
	public void statement5() throws SyntaxException, LexicalException {
	String input = "a+b;";
	Scanner scanner = new Scanner(input).scan(); 
	show(scanner); 
	Parser parser = new Parser(scanner);
	Expression ast = parser.expression();
	show(ast);
	}
	
	@Test
	public void testDec4_Raktima() throws LexicalException, SyntaxException {
		String input = "prog int k=2;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
	}
	
	@Test
	public void statement_Raktima() throws LexicalException, SyntaxException {
		String input = "2";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
	}
	
	@Test
	public void statement1() throws LexicalException, SyntaxException {
		String input = "abc int def;";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
				.get(0);  
		assertEquals(KW_int, dec.type.kind);
		assertEquals("def", dec.name);
		assertNull(dec.e);
	}
	
	@Test
	public void statement2() throws LexicalException, SyntaxException {
		String input = "abc";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Program ast = parser.parse();
		show(ast);
		assertEquals(ast.name, "abc"); 
	}
	
	@Test
	public void statement3() throws LexicalException, SyntaxException {
		String input = "++++x";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
		//assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
//		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
//				.get(0);  
//		assertEquals(KW_int, dec.type.kind);
//		assertEquals("def", dec.name);
//		assertNull(dec.e);
	}
	
	@Test
	public void statement6() throws LexicalException, SyntaxException {
		String input = "+x=x";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
		//assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
//		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
//				.get(0);  
//		assertEquals(KW_int, dec.type.kind);
//		assertEquals("def", dec.name);
//		assertNull(dec.e);
	}
	
	@Test
	public void statement7() throws LexicalException, SyntaxException {
		String input = "+x=x";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
		//assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
//		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
//				.get(0);  
//		assertEquals(KW_int, dec.type.kind);
//		assertEquals("def", dec.name);
//		assertNull(dec.e);
	}
	
	@Test
	public void statement8() throws LexicalException, SyntaxException {
		String input = "+++x = +(+(+x)) = x";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
		//assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
//		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
//				.get(0);  
//		assertEquals(KW_int, dec.type.kind);
//		assertEquals("def", dec.name);
//		assertNull(dec.e);
	}
	
	@Test
	public void statement9() throws LexicalException, SyntaxException {
		String input = "myprog boolean val=false";
		show(input);
		Scanner scanner = new Scanner(input).scan(); 
		show(scanner); 
		Parser parser = new Parser(scanner);
		Expression ast = parser.expression();
		show(ast);
		//assertEquals(ast.name, "abc"); 
		//This should have one Declaration_Variable object, which is at position 0 in the decsAndStatements list
//		Declaration_Variable dec = (Declaration_Variable) ast.decsAndStatements
//				.get(0);  
//		assertEquals(KW_int, dec.type.kind);
//		assertEquals("def", dec.name);
//		assertNull(dec.e);
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
			parser.program();  //Parse the program
		}
		catch (SyntaxException e) {
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
			parser.program();  //Parse the program
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		} 
	}
	
	@Test
	public void testcase5() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,png] imageName <- imagepng"; //Error as there is not semicolon for ending the statement
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		//Parser.program();
		thrown.expect(SyntaxException.class);
		try {
			parser.program();  //Parse the program
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		} 
	}
	
	@Test
	public void testcase6() throws SyntaxException, LexicalException {
		String input = "imageDeclaration image[\"abcd\"] "; //Should fail for image[""]
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();  //Parse the program
		}
		catch (SyntaxException e) {
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
		Program ast=parser.program();
		show(ast);
		assertEquals("prog",ast.name);
		// First Declaration statement
		Declaration_Image dec = (Declaration_Image) ast.decsAndStatements.get(0);  
		assertEquals(KW_image, dec.firstToken.kind);
		assertEquals("imageName", dec.name);
		Expression_Ident ei=(Expression_Ident)dec.xSize;
		assertEquals("filepng",ei.name);
		ei=(Expression_Ident)dec.ySize;
		assertEquals("png",ei.name);
		Source_Ident s=(Source_Ident) dec.source;
	    assertEquals("imagepng",s.name);
		// Second Declaration statement
	    Declaration_Variable dec2 = (Declaration_Variable) ast.decsAndStatements.get(1);  
		assertEquals("ab", dec2.name);
		assertEquals(KW_boolean, dec2.firstToken.kind);
		Expression_BooleanLit ebi=(Expression_BooleanLit)dec2.e;
		assertEquals(true,ebi.value);		
	}
	
	@Test
	public void testcase8() throws SyntaxException, LexicalException {
		String input = "prog image[filepng,jpg] imageName;"; 
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		Program ast=parser.program();
		show(ast);
		assertEquals("prog",ast.name);
		Declaration_Image dec1 = (Declaration_Image) ast.decsAndStatements.get(0); 
		assertEquals(dec1.name,"imageName");
		Expression_Ident exi=(Expression_Ident)dec1.xSize;
		Expression_Ident eyi=(Expression_Ident)dec1.ySize;
		assertEquals(exi.name,"filepng");
		assertEquals(eyi.name,"jpg");
		assertNull(dec1.source);
	}
	
	@Test
	public void testcase10() throws SyntaxException, LexicalException {
		String input = "prog @expr k=12;"; 
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"prog");
		assertEquals(ast.decsAndStatements.size(),0);
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
		}
		catch (SyntaxException e) {
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
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"prog");
		assertEquals(ast.decsAndStatements.size(),0);
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
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		} 
	}	

	@Test
	public void testcase12() throws SyntaxException, LexicalException {
		String input = "isBoolean boolean ab=true; boolean cd==true; abcd=true ? return true: return false;"; //Should fail for ==
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		} 
	}
	
	@Test
	public void testcase13() throws SyntaxException, LexicalException {
		String input = "isBoolean boolean ab=true; boolean cd==true; abcd=true ? return true: return false;"; //Should fail for =
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		}
		catch (SyntaxException e) {
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
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"isUrl");
		assertEquals(ast.decsAndStatements.size(),1);
		Declaration_SourceSink dss=(Declaration_SourceSink)ast.decsAndStatements.get(0);
		System.out.println("Here"+dss.name);
		assertEquals(dss.name,"filepng");
		assertEquals(dss.type,KW_url);
		Source_StringLiteral s=(Source_StringLiteral)dss.source;
		assertEquals(s.fileOrUrl,"abcd");
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
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		}  
	}
	
	@Test
	public void testcase15() throws SyntaxException, LexicalException {
		String input = "isUrl url filepng=\"abcd\" \n @expr=12; url awesome=@expr; \n url filepng=abcdefg"; //Should fail for ; in line one
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		}
		catch (SyntaxException e) {
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
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"isFile");
		assertEquals(ast.decsAndStatements.size(),1);
		assertEquals(ast.firstToken.kind,IDENTIFIER);
		
		// Declaration Statements
		Declaration_SourceSink ds=(Declaration_SourceSink)ast.decsAndStatements.get(0);
		assertEquals(ds.type,KW_file);
		assertEquals(ds.name,"filepng");
		Source_StringLiteral s=(Source_StringLiteral)ds.source;
		assertEquals(s.fileOrUrl,"abcd");
		//assertEquals(ast.)
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
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		}  
	}
	
	@Test
	public void testcase17() throws SyntaxException, LexicalException {
		String input =  "isFile file filepng=\"abcd\" \n @expr=12; url filepng=@expr; \n url filepng=abcdefg";  //Should fail for ; in line one
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		}  
	}
	
	@Test
	public void testcase18() throws SyntaxException, LexicalException {
		String input =  "isurl url urlname;";  //Should fail for url as url can only be initalised
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		thrown.expect(SyntaxException.class);
		try {
			parser.program();
		}
		catch (SyntaxException e) {
			show(e);
			throw e;
		}  
	}
	
	@Test
	public void testcase19() throws SyntaxException, LexicalException {
		String input =  "declaration int xyz;\n boolean zya;\n image imagename;";  
		show(input);
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"declaration");
		assertEquals(ast.firstToken.kind,IDENTIFIER);
		
		//Declaration statements start
		Declaration_Variable dv1=(Declaration_Variable)ast.decsAndStatements.get(0);
		assertEquals(dv1.name,"xyz");
		assertEquals(dv1.type.kind,KW_int);
		assertNull(dv1.e);
		
		Declaration_Variable dv2=(Declaration_Variable)ast.decsAndStatements.get(1);
		assertEquals(dv2.name,"zya");
		assertEquals(dv2.type.kind,KW_boolean);
		assertNull(dv2.e);
		
		Declaration_Image dv3=(Declaration_Image)ast.decsAndStatements.get(2);	
		assertEquals(dv3.name,"imagename");
		assertNull(dv3.source);
		assertNull(dv3.xSize);
		assertNull(dv3.ySize);
		
		//Declaration statement end
	}
	
	@Test
	public void testcase20() throws SyntaxException, LexicalException {
		String input =  "imageProgram image imageName;"
				+ "\n imageName->abcdpng; "
				+ "\n imageName -> SCREEN; "
				+ "\n imageName <- \"awesome\";"
				+ "\n imageName <- @express; \n"
				+ "\n imageName <- abcdpng;";  // Image related Test cases
		Scanner scanner = new Scanner(input).scan();  
		show(scanner);   
		Parser parser = new Parser(scanner);
		Program ast=parser.program();  //Parse the program
		show(ast);
		assertEquals(ast.name,"imageProgram");
		
		//Declaration statement start
		Declaration_Image dv1=(Declaration_Image)ast.decsAndStatements.get(0);
		assertEquals(dv1.name,"imageName");
		assertNull(dv1.xSize);
		assertNull(dv1.ySize);
		assertNull(dv1.source);
		
		Statement_Out dv2=(Statement_Out)ast.decsAndStatements.get(1);
		assertEquals(dv2.name,"imageName");
		Sink_Ident si2=(Sink_Ident)dv2.sink;
		assertEquals(si2.name,"abcdpng");
	}
}