package cop5556fa17;

import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.Token;
import static cop5556fa17.Scanner.Kind.*;

public class SimpleParser {

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

	SimpleParser(Scanner scanner) {
		this.scanner = scanner;
		t = scanner.nextToken();
	}

	/**
	 * Main method called by compiler to parser input. Checks for EOF
	 * 
	 * @throws SyntaxException
	 */
	public void parse() throws SyntaxException {
		program();
		matchEOF();
	}

	/**
	 * Program ::= IDENTIFIER ( Declaration SEMI | Statement SEMI )*
	 * 
	 * Program is start symbol of our grammar.
	 * 
	 * @throws SyntaxException
	 */
	void program() throws SyntaxException {
		// TODO implement this

		if (t.kind == IDENTIFIER) {

			matchToken(IDENTIFIER);

			while (t.kind == KW_int || t.kind == KW_boolean || t.kind == KW_image || t.kind == KW_url
					|| t.kind == KW_file || t.kind == IDENTIFIER) {

				if (t.kind == KW_int || t.kind == KW_boolean || t.kind == KW_image || t.kind == KW_url
						|| t.kind == KW_file) {
					declaration();
					if (t.kind == SEMI) {
						matchToken(SEMI);
					} else {
						throw new SyntaxException(t, "Missing Semicolon");
					}

				} else if (t.kind == IDENTIFIER) {
					statement();
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
	}

	void declaration() throws SyntaxException {
		switch (t.kind) {
		case KW_int:
		case KW_boolean:
			variableDeclaration();
			break;
		case KW_image:
			imageDeclaration();
			break;
		case KW_url:
		case KW_file:
			sourceSinkDeclaration();
			break;
		default:
			throw new SyntaxException(t, "Illegal Declaration");
		}
	}

	void statement() throws SyntaxException {
		if (t.kind == IDENTIFIER && scanner.peek().kind == OP_RARROW) {
			imageOutStatement();
		} else if (t.kind == IDENTIFIER && scanner.peek().kind == OP_LARROW) {
			imageInStatement();
		} else if ((t.kind == IDENTIFIER && scanner.peek().kind == OP_ASSIGN)
				|| (t.kind == IDENTIFIER && scanner.peek().kind == LSQUARE)) {
			assignmentStatement();
		} else {
			throw new SyntaxException(t, "Illegal Statement");
		}
	}

	void imageOutStatement() throws SyntaxException {
		matchToken(IDENTIFIER, OP_RARROW);
		sink();
	}

	void sink() throws SyntaxException {
		switch (t.kind) {
		case IDENTIFIER:
			matchToken(IDENTIFIER);
			break;
		case KW_SCREEN:
			matchToken(KW_SCREEN);
			break;
		default:
			throw new SyntaxException(t, "Illegal Sink");
		}
	}

	void imageInStatement() throws SyntaxException {
		matchToken(IDENTIFIER, OP_LARROW);
		source();
	}

	void assignmentStatement() throws SyntaxException {
		lhs();
		matchToken(OP_ASSIGN);
		expression();
	}

	void lhs() throws SyntaxException {
		matchToken(IDENTIFIER);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			lhsSelector();
			matchToken(RSQUARE);
		}
	}

	void lhsSelector() throws SyntaxException {
		matchToken(LSQUARE);
		if (t.kind == KW_x) {
			xySelector();
		} else if (t.kind == KW_r) {
			raSelector();
		}
		matchToken(RSQUARE);
	}

	void xySelector() throws SyntaxException {
		matchToken(KW_x, COMMA, KW_y);
	}

	void raSelector() throws SyntaxException {
		matchToken(KW_r, COMMA, KW_A);
	}

	void variableDeclaration() throws SyntaxException {
		varType();
		matchToken(IDENTIFIER);
		if (t.kind == OP_ASSIGN) {
			matchToken(OP_ASSIGN);
			expression();
		}
	}

	void varType() throws SyntaxException {
		switch (t.kind) {
		case KW_boolean:
			matchToken(KW_boolean);
			break;
		case KW_int:
			matchToken(KW_int);
			break;
		default:
			throw new SyntaxException(t, "Illegal varType");
		}
	}

	void imageDeclaration() throws SyntaxException {
		matchToken(KW_image);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			expression();
			matchToken(COMMA);
			expression();
			matchToken(RSQUARE);
		}
		matchToken(IDENTIFIER);
		if (t.kind == OP_LARROW) {
			matchToken(OP_LARROW);
			source();
		}

	}

	void sourceSinkDeclaration() throws SyntaxException {
		sourceSinkType();
		matchToken(IDENTIFIER, OP_ASSIGN);
		source();
	}

	void sourceSinkType() throws SyntaxException {
		switch (t.kind) {
		case KW_url:
			matchToken(KW_url);
			break;
		case KW_file:
			matchToken(KW_file);
			break;
		default:
			throw new SyntaxException(t, "Illegal Source Sink Type");
		}
	}

	void source() throws SyntaxException {
		switch (t.kind) {
		case STRING_LITERAL:
			matchToken(STRING_LITERAL);
			break;
		case OP_AT:
			matchToken(OP_AT);
			expression();
			break;
		case IDENTIFIER:
			matchToken(IDENTIFIER);
			break;
		default:
			throw new SyntaxException(t, "Illegal Source");
		}
	}

	/**
	 * Expression ::= OrExpression OP_Q Expression OP_COLON Expression |
	 * OrExpression
	 * 
	 * Our test cases may invoke this routine directly to support incremental
	 * development.
	 * 
	 * @throws SyntaxException
	 */
	void expression() throws SyntaxException {
		// TODO implement this.
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
			orExpression();
			if (t.kind == OP_Q) {
				matchToken(OP_Q);
				expression();
				matchToken(OP_COLON);
				expression();
			}
			break;
		default:
			throw new SyntaxException(t, "Illegal Start of Expression");
		}
	}

	void orExpression() throws SyntaxException {
		andExpression();
		while (t.kind == OP_OR) {
			matchToken(OP_OR);
			andExpression();
		}
	}

	void andExpression() throws SyntaxException {
		eqExpression();
		while (t.kind == OP_AND) {
			matchToken(OP_AND);
			eqExpression();
		}
	}

	void eqExpression() throws SyntaxException {
		relExpression();
		while (t.kind == OP_EQ || t.kind == OP_NEQ) {
			if (t.kind == OP_EQ) {
				matchToken(OP_EQ);
			} else if (t.kind == OP_NEQ) {
				matchToken(OP_NEQ);
			}
			relExpression();
		}
	}

	void relExpression() throws SyntaxException {
		addExpression();
		while (t.kind == OP_LT || t.kind == OP_GT || t.kind == OP_LE || t.kind == OP_GE) {
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
			addExpression();
		}
	}

	void addExpression() throws SyntaxException {
		multExpression();
		while (t.kind == OP_PLUS || t.kind == OP_MINUS) {
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
			multExpression();
		}
	}

	void multExpression() throws SyntaxException {
		unaryExpression();
		while (t.kind == OP_TIMES || t.kind == OP_DIV || t.kind == OP_MOD) {
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
			unaryExpression();
		}
	}

	void unaryExpression() throws SyntaxException {
		switch (t.kind) {
		case OP_PLUS:
			matchToken(OP_PLUS);
			unaryExpression();
			break;
		case OP_MINUS:
			matchToken(OP_MINUS);
			unaryExpression();
			break;
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
			unaryExpressionNotPlusMinus();
			break;
		default:
			throw new SyntaxException(t, "Illegal Unary Expression");
		}
	}

	void unaryExpressionNotPlusMinus() throws SyntaxException {
		switch (t.kind) {
		case OP_EXCL:
			matchToken(OP_EXCL);
			unaryExpression();
			break;
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
			primary();
			break;
		case IDENTIFIER:
			identOrPixelSelectorExpression();
			break;
		case KW_x:
			matchToken(KW_x);
			break;
		case KW_y:
			matchToken(KW_y);
			break;
		case KW_r:
			matchToken(KW_r);
			break;
		case KW_a:
			matchToken(KW_a);
			break;
		case KW_X:
			matchToken(KW_X);
			break;
		case KW_Y:
			matchToken(KW_Y);
			break;
		case KW_Z:
			matchToken(KW_Z);
			break;
		case KW_A:
			matchToken(KW_A);
			break;
		case KW_R:
			matchToken(KW_R);
			break;
		case KW_DEF_X:
			matchToken(KW_DEF_X);
			break;
		case KW_DEF_Y:
			matchToken(KW_DEF_Y);
			break;
		default:
			throw new SyntaxException(t, "Illegal Unary Expression");
		}
	}

	void primary() throws SyntaxException {
		switch (t.kind) {
		case INTEGER_LITERAL:
			matchToken(INTEGER_LITERAL);
			break;
		case LPAREN:
			matchToken(LPAREN);
			expression();
			matchToken(RPAREN);
			break;
		case KW_sin:
		case KW_cos:
		case KW_atan:
		case KW_abs:
		case KW_cart_x:
		case KW_cart_y:
		case KW_polar_a:
		case KW_polar_r:
			functionApplication();
			break;
		case BOOLEAN_LITERAL:
			matchToken(BOOLEAN_LITERAL);
			break;
		default:
			throw new SyntaxException(t, "Illegal Primary Expression");
		}
	}

	void identOrPixelSelectorExpression() throws SyntaxException {
		matchToken(IDENTIFIER);
		if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			selector();
			matchToken(RSQUARE);
		}
	}

	void functionApplication() throws SyntaxException {
		functionName();
		if (t.kind == LPAREN) {
			matchToken(LPAREN);
			expression();
			matchToken(RPAREN);
		} else if (t.kind == LSQUARE) {
			matchToken(LSQUARE);
			selector();
			matchToken(RSQUARE);
		}
	}

	void functionName() throws SyntaxException {
		switch (t.kind) {
		case KW_sin:
			matchToken(KW_sin);
			break;
		case KW_cos:
			matchToken(KW_cos);
			break;
		case KW_atan:
			matchToken(KW_atan);
			break;
		case KW_abs:
			matchToken(KW_abs);
			break;
		case KW_cart_x:
			matchToken(KW_cart_x);
			break;
		case KW_cart_y:
			matchToken(KW_cart_y);
			break;
		case KW_polar_a:
			matchToken(KW_polar_a);
			break;
		case KW_polar_r:
			matchToken(KW_polar_r);
			break;
		default:
			throw new SyntaxException(t, "Illegal Function Name");
		}
	}

	void selector() throws SyntaxException {
		expression();
		matchToken(COMMA);
		expression();
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
