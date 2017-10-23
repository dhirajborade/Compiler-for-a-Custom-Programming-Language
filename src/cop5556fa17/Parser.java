package cop5556fa17;

import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.Token;
import cop5556fa17.AST.ASTNode;
import cop5556fa17.AST.Declaration;
import cop5556fa17.AST.Declaration_Image;
import cop5556fa17.AST.Declaration_SourceSink;
import cop5556fa17.AST.Declaration_Variable;
import cop5556fa17.AST.Expression;
import cop5556fa17.AST.Expression_Binary;
import cop5556fa17.AST.Expression_BooleanLit;
import cop5556fa17.AST.Expression_Conditional;
import cop5556fa17.AST.Expression_FunctionApp;
import cop5556fa17.AST.Expression_FunctionAppWithExprArg;
import cop5556fa17.AST.Expression_FunctionAppWithIndexArg;
import cop5556fa17.AST.Expression_Ident;
import cop5556fa17.AST.Expression_IntLit;
import cop5556fa17.AST.Expression_PixelSelector;
import cop5556fa17.AST.Expression_PredefinedName;
import cop5556fa17.AST.Expression_Unary;
import cop5556fa17.AST.Index;
import cop5556fa17.AST.LHS;
import cop5556fa17.AST.Program;
import cop5556fa17.AST.Sink;
import cop5556fa17.AST.Sink_Ident;
import cop5556fa17.AST.Sink_SCREEN;
import cop5556fa17.AST.Source;
import cop5556fa17.AST.Source_CommandLineParam;
import cop5556fa17.AST.Source_Ident;
import cop5556fa17.AST.Source_StringLiteral;
import cop5556fa17.AST.Statement;
import cop5556fa17.AST.Statement_Assign;
import cop5556fa17.AST.Statement_In;
import cop5556fa17.AST.Statement_Out;

import static cop5556fa17.Scanner.Kind.*;

import java.util.ArrayList;

public class Parser {

	@SuppressWarnings("serial")
	public class SyntaxException extends Exception {
		Token t;

		public SyntaxException(Token t, String message) {
			super(message);
			this.t = t;
		}

	}

	Scanner scanner;
	Token t;

	Parser(Scanner scanner) {
		this.scanner = scanner;
		t = scanner.nextToken();
	}

	/**
	 * Main method called by compiler to parser input. Checks for EOF
	 * 
	 * @throws SyntaxException
	 */
	public Program parse() throws SyntaxException {
		Program p = program();
		matchEOF();
		return p;
	}

	/**
	 * Program ::= IDENTIFIER ( Declaration SEMI | Statement SEMI )*
	 * 
	 * Program is start symbol of our grammar.
	 * 
	 * @throws SyntaxException
	 */
	Program program() throws SyntaxException {
		// TODO implement this
		ArrayList<ASTNode> decsAndStatements = new ArrayList<>();
		Token firstToken = t;
		Token name = t;

		if (t.kind == IDENTIFIER) {
			matchToken(IDENTIFIER);
			while (t.kind == KW_int || t.kind == KW_boolean || t.kind == KW_image || t.kind == KW_url
					|| t.kind == KW_file || t.kind == IDENTIFIER) {
				if (t.kind == KW_int || t.kind == KW_boolean || t.kind == KW_image || t.kind == KW_url
						|| t.kind == KW_file) {
					decsAndStatements.add(declaration());
					if (t.kind == SEMI) {
						matchToken(SEMI);
					} else {
						throw new SyntaxException(t, "Missing Semicolon");
					}

				} else if (t.kind == IDENTIFIER) {
					decsAndStatements.add(statement());
					if (t.kind == SEMI) {
						matchToken(SEMI);
					} else {
						throw new SyntaxException(t, "Missing Semicolon");
					}
				}
			}
		} else {
			throw new SyntaxException(t, "Illegal Start of Program");
		}
		return new Program(firstToken, name, decsAndStatements);
	}

	Declaration declaration() throws SyntaxException {
		switch (t.kind) {
		case KW_int:
		case KW_boolean:
			return variableDeclaration();
		case KW_image:
			return imageDeclaration();
		case KW_url:
		case KW_file:
			return sourceSinkDeclaration();
		default:
			throw new SyntaxException(t, "Illegal Declaration");
		}
	}

	Declaration_Variable variableDeclaration() throws SyntaxException {
		Token firstToken = t;
		Token type = varType();
		Token name = t;
		Expression e = null;
		matchToken(IDENTIFIER);
		if (t.kind == OP_ASSIGN) {
			matchToken(OP_ASSIGN);
			e = expression();
		}
		return new Declaration_Variable(firstToken, type, name, e);
	}

	Declaration_Image imageDeclaration() throws SyntaxException {
		Token firstToken = t;
		Source source = null;
		Expression xSize = null;
		Expression ySize = null;
		matchToken(KW_image);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			xSize = expression();
			matchToken(COMMA);
			ySize = expression();
			matchToken(RSQUARE);
		}
		Token name = t;
		matchToken(IDENTIFIER);
		if (t.kind == OP_LARROW) {
			matchToken(OP_LARROW);
			source = source();
		}
		return new Declaration_Image(firstToken, xSize, ySize, name, source);
	}

	Declaration_SourceSink sourceSinkDeclaration() throws SyntaxException {
		Token firstToken = t;
		Token type = sourceSinkType();
		Token name = t;
		matchToken(IDENTIFIER, OP_ASSIGN);
		Source source = source();
		return new Declaration_SourceSink(firstToken, type, name, source);
	}

	Statement statement() throws SyntaxException {
		if (t.kind == IDENTIFIER && scanner.peek().kind == OP_RARROW) {
			return imageOutStatement();
		} else if (t.kind == IDENTIFIER && scanner.peek().kind == OP_LARROW) {
			return imageInStatement();
		} else if ((t.kind == IDENTIFIER && scanner.peek().kind == OP_ASSIGN)
				|| (t.kind == IDENTIFIER && scanner.peek().kind == LSQUARE)) {
			return assignmentStatement();
		} else {
			throw new SyntaxException(t, "Illegal Statement");
		}
	}

	Statement_Out imageOutStatement() throws SyntaxException {
		Token firstToken = t;
		Token name = t;
		matchToken(IDENTIFIER, OP_RARROW);
		Sink sink = sink();
		return new Statement_Out(firstToken, name, sink);
	}

	Statement_In imageInStatement() throws SyntaxException {
		Token firstToken = t;
		Token name = t;
		matchToken(IDENTIFIER, OP_LARROW);
		Source source = source();
		return new Statement_In(firstToken, name, source);
	}

	Statement_Assign assignmentStatement() throws SyntaxException {
		Token firstToken = t;
		LHS lhs = lhs();
		matchToken(OP_ASSIGN);
		Expression e = expression();
		return new Statement_Assign(firstToken, lhs, e);
	}

	Sink sink() throws SyntaxException {
		Token firstToken = t;
		switch (t.kind) {
		case IDENTIFIER:
			Token name = t;
			matchToken(IDENTIFIER);
			return new Sink_Ident(firstToken, name);
		case KW_SCREEN:
			matchToken(KW_SCREEN);
			return new Sink_SCREEN(firstToken);
		default:
			throw new SyntaxException(t, "Illegal Sink");
		}
	}

	Source source() throws SyntaxException {
		Token firstToken = t;
		switch (t.kind) {
		case STRING_LITERAL:
			String fileOrUrl = t.getText();
			matchToken(STRING_LITERAL);
			return new Source_StringLiteral(firstToken, fileOrUrl);
		case OP_AT:
			matchToken(OP_AT);
			Expression paramNum = expression();
			return new Source_CommandLineParam(firstToken, paramNum);
		case IDENTIFIER:
			Token name = t;
			matchToken(IDENTIFIER);
			return new Source_Ident(firstToken, name);
		default:
			throw new SyntaxException(t, "Illegal Source");
		}
	}

	LHS lhs() throws SyntaxException {
		Token firstToken = t;
		Token name = t;
		Index index = null;
		matchToken(IDENTIFIER);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			index = lhsSelector();
			matchToken(RSQUARE);
		}
		return new LHS(firstToken, name, index);
	}

	Index lhsSelector() throws SyntaxException {
		Index index = null;
		matchToken(LSQUARE);
		if (t.kind == KW_x) {
			index = xySelector();
		} else if (t.kind == KW_r) {
			index = raSelector();
		}
		matchToken(RSQUARE);
		return index;
	}

	Index xySelector() throws SyntaxException {
		Token firstToken = t;
		Kind kind = t.kind;
		Expression e0 = new Expression_PredefinedName(firstToken, kind);
		matchToken(KW_x, COMMA);
		firstToken = t;
		kind = t.kind;
		Expression e1 = new Expression_PredefinedName(firstToken, kind);
		matchToken(KW_y);
		return new Index(firstToken, e0, e1);
	}

	Index raSelector() throws SyntaxException {
		Token firstToken = t;
		Kind kind = t.kind;
		Expression e0 = new Expression_PredefinedName(firstToken, kind);
		matchToken(KW_r, COMMA);
		firstToken = t;
		kind = t.kind;
		Expression e1 = new Expression_PredefinedName(firstToken, kind);
		matchToken(KW_A);
		return new Index(firstToken, e0, e1);
	}

	Token varType() throws SyntaxException {
		Token type;
		switch (t.kind) {
		case KW_boolean:
			type = t;
			matchToken(KW_boolean);
			return type;
		case KW_int:
			type = t;
			matchToken(KW_int);
			return type;
		default:
			throw new SyntaxException(t, "Illegal varType");
		}
	}

	Token sourceSinkType() throws SyntaxException {
		Token type;
		switch (t.kind) {
		case KW_url:
			type = t;
			matchToken(KW_url);
			return type;
		case KW_file:
			type = t;
			matchToken(KW_file);
			return type;
		default:
			throw new SyntaxException(t, "Illegal Source Sink Type");
		}
	}

	/**
	 * Expression ::= OrExpression OP_Q Expression OP_COLON Expression |
	 * OrExpression
	 * 
	 * Our test cases may invoke this routine directly to support incremental
	 * development.
	 * @return 
	 * 
	 * @throws SyntaxException
	 */
	Expression expression() throws SyntaxException {
		// TODO implement this.
		
		Token firstToken = t;
		Expression condition = null;
		Expression trueExpression = null;
		Expression falseExpression = null;
		
		switch (t.kind) {
		case OP_PLUS:
		case OP_MINUS:
		case OP_EXCL:
		case INTEGER_LITERAL:
		case LPAREN:
		case KW_sin:
		case KW_cos:
		case KW_atan:
		case KW_abs:
		case KW_cart_x:
		case KW_cart_y:
		case KW_polar_a:
		case KW_polar_r:
		case IDENTIFIER:
		case KW_x:
		case KW_y:
		case KW_r:
		case KW_a:
		case KW_X:
		case KW_Y:
		case KW_Z:
		case KW_A:
		case KW_R:
		case KW_DEF_X:
		case KW_DEF_Y:
		case BOOLEAN_LITERAL:
			condition = orExpression();
			if (t.kind == OP_Q) {
				matchToken(OP_Q);
				trueExpression = expression();
				matchToken(OP_COLON);
				falseExpression = expression();
				return new Expression_Conditional(firstToken, condition, trueExpression, falseExpression);
			}
			return condition;
		default:
			throw new SyntaxException(t, "Illegal Start of Expression");
		}
	}

	Expression orExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = andExpression();
		Expression e1 = null;
		while (t.kind == OP_OR) {
			op = t;
			matchToken(OP_OR);
			e1 = andExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression andExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = eqExpression();
		Expression e1 = null;
		while (t.kind == OP_AND) {
			op = t;
			matchToken(OP_AND);
			e1 = eqExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression eqExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = relExpression();
		Expression e1 = null;
		while (t.kind == OP_EQ || t.kind == OP_NEQ) {
			op = t;
			if (t.kind == OP_EQ) {
				matchToken(OP_EQ);
			} else if (t.kind == OP_NEQ) {
				matchToken(OP_NEQ);
			}
			e1 = relExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression relExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = addExpression();
		Expression e1 = null;
		while (t.kind == OP_LT || t.kind == OP_GT || t.kind == OP_LE || t.kind == OP_GE) {
			op = t;
			switch (t.kind) {
			case OP_LT:
				matchToken(OP_LT);
				break;
			case OP_GT:
				matchToken(OP_GT);
				break;
			case OP_LE:
				matchToken(OP_LE);
				break;
			case OP_GE:
				matchToken(OP_GE);
				break;
			default:
				throw new SyntaxException(t, "Illegal Compare Expression");
			}
			e1 = addExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression addExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = multExpression();
		Expression e1 = null;
		while (t.kind == OP_PLUS || t.kind == OP_MINUS) {
			op = t;
			switch (t.kind) {
			case OP_PLUS:
				matchToken(OP_PLUS);
				break;
			case OP_MINUS:
				matchToken(OP_MINUS);
				break;
			default:
				throw new SyntaxException(t, "Illegal Add/Subtract Expression");
			}
			e1 = multExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression multExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = null;
		Expression e0 = unaryExpression();
		Expression e1 = null;
		while (t.kind == OP_TIMES || t.kind == OP_DIV || t.kind == OP_MOD) {
			op = t;
			switch (t.kind) {
			case OP_TIMES:
				matchToken(OP_TIMES);
				break;
			case OP_DIV:
				matchToken(OP_DIV);
				break;
			case OP_MOD:
				matchToken(OP_MOD);
				break;
			default:
				throw new SyntaxException(t, "Illegal Multiplication Expression");
			}
			e1 = unaryExpression();
			e0 = new Expression_Binary(firstToken, e0, op, e1);
		}
		return e0;
	}

	Expression unaryExpression() throws SyntaxException {
		Token firstToken = t;
		Token op = t;
		Expression e = null;
		switch (t.kind) {
		case OP_PLUS:
			matchToken(OP_PLUS);
			e = unaryExpression();
			return new Expression_Unary(firstToken, op, e);
		case OP_MINUS:
			matchToken(OP_MINUS);
			e = unaryExpression();
			return new Expression_Unary(firstToken, op, e);
		case OP_EXCL:
		case INTEGER_LITERAL:
		case LPAREN:
		case KW_sin:
		case KW_cos:
		case KW_atan:
		case KW_abs:
		case KW_cart_x:
		case KW_cart_y:
		case KW_polar_a:
		case KW_polar_r:
		case IDENTIFIER:
		case KW_x:
		case KW_y:
		case KW_r:
		case KW_a:
		case KW_X:
		case KW_Y:
		case KW_Z:
		case KW_A:
		case KW_R:
		case KW_DEF_X:
		case KW_DEF_Y:
		case BOOLEAN_LITERAL:
			return unaryExpressionNotPlusMinus();
		default:
			throw new SyntaxException(t, "Illegal Unary Expression");
		}
	}

	Expression unaryExpressionNotPlusMinus() throws SyntaxException {
		Token firstToken = t;
		Token op = t;
		Kind kind = t.kind;
		Expression e = null;
		switch (t.kind) {
		case OP_EXCL:
			matchToken(OP_EXCL);
			e = unaryExpression();
			return new Expression_Unary(firstToken, op, e);
		case INTEGER_LITERAL:
		case LPAREN:
		case KW_sin:
		case KW_cos:
		case KW_atan:
		case KW_abs:
		case KW_cart_x:
		case KW_cart_y:
		case KW_polar_a:
		case KW_polar_r:
		case BOOLEAN_LITERAL:
			return primary();
		case IDENTIFIER:
			return identOrPixelSelectorExpression();
		case KW_x:
			matchToken(KW_x);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_y:
			matchToken(KW_y);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_r:
			matchToken(KW_r);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_a:
			matchToken(KW_a);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_X:
			matchToken(KW_X);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_Y:
			matchToken(KW_Y);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_Z:
			matchToken(KW_Z);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_A:
			matchToken(KW_A);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_R:
			matchToken(KW_R);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_DEF_X:
			matchToken(KW_DEF_X);
			return new Expression_PredefinedName(firstToken, kind);
		case KW_DEF_Y:
			matchToken(KW_DEF_Y);
			return new Expression_PredefinedName(firstToken, kind);
		default:
			throw new SyntaxException(t, "Illegal Unary Expression");
		}
	}

	Expression primary() throws SyntaxException {
		Token firstToken = t;
		Expression e = null;
		switch (t.kind) {
		case INTEGER_LITERAL:
			int intValue = t.intVal();
			matchToken(INTEGER_LITERAL);
			return new Expression_IntLit(firstToken, intValue);
		case LPAREN:
			matchToken(LPAREN);
			e = expression();
			matchToken(RPAREN);
			return e;
		case KW_sin:
		case KW_cos:
		case KW_atan:
		case KW_abs:
		case KW_cart_x:
		case KW_cart_y:
		case KW_polar_a:
		case KW_polar_r:
			return functionApplication();
		case BOOLEAN_LITERAL:
			boolean boolValue = t.getText().equals("true") ? true : false;
			matchToken(BOOLEAN_LITERAL);
			return new Expression_BooleanLit(firstToken, boolValue);
		default:
			throw new SyntaxException(t, "Illegal Primary Expression");
		}
	}

	Expression identOrPixelSelectorExpression() throws SyntaxException {
		Token firstToken = t;
		Token name = t;
		Token ident = t;
		matchToken(IDENTIFIER);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			Index index = selector();
			matchToken(RSQUARE);
			return new Expression_PixelSelector(firstToken, name, index);
		}
		return new Expression_Ident(firstToken, ident);
	}

	Expression_FunctionApp functionApplication() throws SyntaxException {
		Token firstToken = t;
		Kind function = t.kind;
		functionName();
		if (t.kind == LPAREN) {
			matchToken(LPAREN);
			Expression arg = expression();
			matchToken(RPAREN);
			return new Expression_FunctionAppWithExprArg(firstToken, function, arg);
		} else if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			Index arg = selector();
			matchToken(RSQUARE);
			return new Expression_FunctionAppWithIndexArg(firstToken, function, arg);
		} else {
			throw new SyntaxException(t, "Illegal Function Application");
		}
	}

	Token functionName() throws SyntaxException {
		Token firstToken = t;
		switch (t.kind) {
		case KW_sin:
			matchToken(KW_sin);
			return firstToken;
		case KW_cos:
			matchToken(KW_cos);
			return firstToken;
		case KW_atan:
			matchToken(KW_atan);
			return firstToken;
		case KW_abs:
			matchToken(KW_abs);
			return firstToken;
		case KW_cart_x:
			matchToken(KW_cart_x);
			return firstToken;
		case KW_cart_y:
			matchToken(KW_cart_y);
			return firstToken;
		case KW_polar_a:
			matchToken(KW_polar_a);
			return firstToken;
		case KW_polar_r:
			matchToken(KW_polar_r);
			return firstToken;
		default:
			throw new SyntaxException(t, "Illegal Function Name");
		}
	}

	Index selector() throws SyntaxException {
		Token firstToken = t;
		Expression e0 = expression();
		matchToken(COMMA);
		Expression e1 = expression();
		return new Index(firstToken, e0, e1);
	}

	/**
	 * Only for check at end of program. Does not "consume" EOF so no attempt to get
	 * nonexistent next Token.
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	private Token matchEOF() throws SyntaxException {
		if (t.kind == EOF) {
			return t;
		}
		String message = "Expected EOL at " + t.line + ":" + t.pos_in_line;
		throw new SyntaxException(t, message);
	}

	private void matchToken(Kind kind) throws SyntaxException {
		if (t.kind == kind) {
			consume();
		} else {
			String message = "Got token of kind " + t.kind + " instead of " + kind;
			throw new SyntaxException(t, message);
		}
	}

	private void matchToken(Kind... kinds) throws SyntaxException {
		for (Kind k : kinds) {
			if (k == t.kind) {
				consume();
			} else {
				String message = "Got token of kind " + t.kind + " instead of " + k;
				throw new SyntaxException(t, message);
			}
		}
	}

	private Token consume() throws SyntaxException {
		Token currentToken = t;
		t = scanner.nextToken();
		return currentToken;
	}
}
