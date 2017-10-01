/* *
 * Scanner for the class project in COP5556 Programming Language Principles 
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Scanner {
	
	@SuppressWarnings("serial")
	public static class LexicalException extends Exception {
		
		int pos;

		public LexicalException(String message, int pos) {
			super(message);
			this.pos = pos;
		}
		
		public int getPos() { return pos; }

	}

	public static enum Kind {
		
		IDENTIFIER,
		INTEGER_LITERAL,
		BOOLEAN_LITERAL,
		STRING_LITERAL, 
		KW_x/* x */,
		KW_X/* X */,
		KW_y/* y */,
		KW_Y/* Y */,
		KW_r/* r */,
		KW_R/* R */,
		KW_a/* a */, 
		KW_A/* A */,
		KW_Z/* Z */,
		KW_DEF_X/* DEF_X */,
		KW_DEF_Y/* DEF_Y */,
		KW_SCREEN/* SCREEN */, 
		KW_cart_x/* cart_x */,
		KW_cart_y/* cart_y */,
		KW_polar_a/* polar_a */,
		KW_polar_r/* polar_r */, 
		KW_abs/* abs */,
		KW_sin/* sin */,
		KW_cos/* cos */,
		KW_atan/* atan */,
		KW_log/* log */, 
		KW_image/* image */,
		KW_int/* int */, 
		KW_boolean/* boolean */,
		KW_url/* url */,
		KW_file/* file */,
		OP_ASSIGN/* = */,
		OP_GT/* > */,
		OP_LT/* < */, 
		OP_EXCL/* ! */,
		OP_Q/* ? */,
		OP_COLON/* : */,
		OP_EQ/* == */,
		OP_NEQ/* != */,
		OP_GE/* >= */,
		OP_LE/* <= */, 
		OP_AND/* & */,
		OP_OR/* | */,
		OP_PLUS/* + */,
		OP_MINUS/* - */,
		OP_TIMES/* * */,
		OP_DIV/* / */,
		OP_MOD/* % */, 
		OP_POWER/* ** */,
		OP_AT/* @ */,
		OP_RARROW/* -> */,
		OP_LARROW/* <- */,
		LPAREN/* ( */,
		RPAREN/* ) */, 
		LSQUARE/* [ */,
		RSQUARE/* ] */,
		SEMI/* ; */,
		COMMA/* , */,
		EOF;
		
	}
	
	public static enum State {

		START/* Starts */,
		GOT_IDENTIFIER/* Got an Identifier */,
		GOT_INTEGER/* Got and Integer Literal */,
		GOT_ASSIGN/* Got "=" */,
		GOT_EXCLAMATION/* Got "!" */,
		GOT_GREATER_THAN/* Got ">" */,
		GOT_LESS_THAN/* Got "<" */,
		GOT_FORWARD_SLASH/* Got "/" */,
		GOT_DOUBLE_FORWARD_SLASH/* Got "//" */,
		GOT_MINUS/* Got "-" */,
		GOT_STAR/* Got "*" */,
		GOT_STRING_LITERAL/* Got '"'*/;

	}
	
	private HashMap<String, Kind> reservedKeywordMap = new HashMap<String, Kind>();

	/** Class to represent Tokens. 
	 * 
	 * This is defined as a (non-static) inner class
	 * which means that each Token instance is associated with a specific 
	 * Scanner instance.  We use this when some token methods access the
	 * chars array in the associated Scanner.
	 * 
	 * 
	 * @author Beverly Sanders
	 *
	 */
	public class Token {
		public final Kind kind;
		public final int pos;
		public final int length;
		public final int line;
		public final int pos_in_line;

		public Token(Kind kind, int pos, int length, int line, int pos_in_line) {
			super();
			this.kind = kind;
			this.pos = pos;
			this.length = length;
			this.line = line;
			this.pos_in_line = pos_in_line;
		}

		public String getText() {
			if (kind == Kind.STRING_LITERAL) {
				return chars2String(chars, pos, length);
			}
			else return String.copyValueOf(chars, pos, length);
		}

		/**
		 * To get the text of a StringLiteral, we need to remove the
		 * enclosing " characters and convert escaped characters to
		 * the represented character.  For example the two characters \ t
		 * in the char array should be converted to a single tab character in
		 * the returned String
		 * 
		 * @param chars
		 * @param pos
		 * @param length
		 * @return
		 */
		private String chars2String(char[] chars, int pos, int length) {
			StringBuilder sb = new StringBuilder();
			for (int i = pos + 1; i < pos + length - 1; ++i) {// omit initial and final "
				char ch = chars[i];
				if (ch == '\\') { // handle escape
					i++;
					ch = chars[i];
					switch (ch) {
					case 'b':
						sb.append('\b');
						break;
					case 't':
						sb.append('\t');
						break;
					case 'f':
						sb.append('\f');
						break;
					case 'r':
						sb.append('\r'); //for completeness, line termination chars not allowed in String literals
						break;
					case 'n':
						sb.append('\n'); //for completeness, line termination chars not allowed in String literals
						break;
					case '\"':
						sb.append('\"');
						break;
					case '\'':
						sb.append('\'');
						break;
					case '\\':
						sb.append('\\');
						break;
					default:
						assert false;
						break;
					}
				} else {
					sb.append(ch);
				}
			}
			return sb.toString();
		}

		/**
		 * precondition:  This Token is an INTEGER_LITERAL
		 * 
		 * @returns the integer value represented by the token
		 */
		public int intVal() {
			assert kind == Kind.INTEGER_LITERAL;
			return Integer.valueOf(String.copyValueOf(chars, pos, length));
		}

		public String toString() {
			return "[" + kind + "," + String.copyValueOf(chars, pos, length)  + "," + pos + "," + length + "," + line + ","
					+ pos_in_line + "]";
		}

		/** 
		 * Since we overrode equals, we need to override hashCode.
		 * https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-
		 * 
		 * Both the equals and hashCode method were generated by eclipse
		 * 
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((kind == null) ? 0 : kind.hashCode());
			result = prime * result + length;
			result = prime * result + line;
			result = prime * result + pos;
			result = prime * result + pos_in_line;
			return result;
		}

		/**
		 * Override equals method to return true if other object
		 * is the same class and all fields are equal.
		 * 
		 * Overriding this creates an obligation to override hashCode.
		 * 
		 * Both hashCode and equals were generated by eclipse.
		 * 
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Token other = (Token) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (kind != other.kind)
				return false;
			if (length != other.length)
				return false;
			if (line != other.line)
				return false;
			if (pos != other.pos)
				return false;
			if (pos_in_line != other.pos_in_line)
				return false;
			return true;
		}

		/**
		 * used in equals to get the Scanner object this Token is 
		 * associated with.
		 * @return
		 */
		private Scanner getOuterType() {
			return Scanner.this;
		}

	}

	/** 
	 * Extra character added to the end of the input characters to simplify the
	 * Scanner.  
	 */
	static final char EOFchar = 0;
	
	/**
	 * The list of tokens created by the scan method.
	 */
	final ArrayList<Token> tokens;
	
	/**
	 * An array of characters representing the input.  These are the characters
	 * from the input string plus and additional EOFchar at the end.
	 */
	final char[] chars;  



	
	/**
	 * position of the next token to be returned by a call to nextToken
	 */
	private int nextTokenPos = 0;

	Scanner(String inputString) {
		int numChars = inputString.length();
		this.chars = Arrays.copyOf(inputString.toCharArray(), numChars + 1); // input string terminated with null char
		chars[numChars] = EOFchar;
		tokens = new ArrayList<Token>();
		
		reservedKeywordMap.put("true", Kind.BOOLEAN_LITERAL);
		reservedKeywordMap.put("false", Kind.BOOLEAN_LITERAL);
		reservedKeywordMap.put("x", Kind.KW_x);
		reservedKeywordMap.put("X", Kind.KW_X);
		reservedKeywordMap.put("y", Kind.KW_y);
		reservedKeywordMap.put("Y", Kind.KW_Y);
		reservedKeywordMap.put("r", Kind.KW_r);
		reservedKeywordMap.put("R", Kind.KW_R);
		reservedKeywordMap.put("a", Kind.KW_a);
		reservedKeywordMap.put("A", Kind.KW_A);
		reservedKeywordMap.put("Z", Kind.KW_Z);
		reservedKeywordMap.put("DEF_X", Kind.KW_DEF_X);
		reservedKeywordMap.put("DEF_Y", Kind.KW_DEF_Y);
		reservedKeywordMap.put("SCREEN", Kind.KW_SCREEN);
		reservedKeywordMap.put("cart_x", Kind.KW_cart_x);
		reservedKeywordMap.put("cart_y", Kind.KW_cart_y);
		reservedKeywordMap.put("polar_a", Kind.KW_polar_a);
		reservedKeywordMap.put("polar_r", Kind.KW_polar_r);
		reservedKeywordMap.put("abs", Kind.KW_abs);
		reservedKeywordMap.put("sin", Kind.KW_sin);
		reservedKeywordMap.put("cos", Kind.KW_cos);
		reservedKeywordMap.put("atan", Kind.KW_atan);
		reservedKeywordMap.put("log", Kind.KW_log);
		reservedKeywordMap.put("image", Kind.KW_image);
		reservedKeywordMap.put("int", Kind.KW_int);
		reservedKeywordMap.put("boolean", Kind.KW_boolean);
		reservedKeywordMap.put("url", Kind.KW_url);
		reservedKeywordMap.put("file", Kind.KW_file);
	}


	/**
	 * Method to scan the input and create a list of Tokens.
	 * 
	 * If an error is encountered during scanning, throw a LexicalException.
	 * 
	 * @return
	 * @throws LexicalException
	 */
	public Scanner scan() throws LexicalException {
		/* TODO  Replace this with a correct and complete implementation!!! */
		int pos = 0;
		int line = 1;
		int posInLine = 1;
		int startPos = 0;
		int startPosLine = 1;
		State state = State.START;
		
		while (pos < chars.length) {
			
			char ch = chars[pos];

			switch (state) {
			case START:
				startPos = pos;
				startPosLine = posInLine;
				if (Character.isWhitespace(ch)) {
					if (ch == '\r') {
						if (chars[pos + 1] == '\n') {
							pos++;
						}
						line++;
						posInLine = 1;
					} else if (ch == '\n') {
						line++;
						posInLine = 1;
					} else {
						posInLine++;
					}
					pos++;
					break;
				}
				switch (ch) {
				case EOFchar:
					tokens.add(new Token(Kind.EOF, startPos, 0, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '=':
					state = State.GOT_ASSIGN;
					pos++;
					posInLine++;
					break;
				case '"':
					state = State.GOT_STRING_LITERAL;
					pos++;
					posInLine++;
					break;
				case '/':
					state = State.GOT_FORWARD_SLASH;
					pos++;
					posInLine++;
					break;
				case '>':
					state = State.GOT_GREATER_THAN;
					pos++;
					posInLine++;
					break;
				case '<':
					state = State.GOT_LESS_THAN;
					pos++;
					posInLine++;
					break;
				case '-':
					state = State.GOT_MINUS;
					pos++;
					posInLine++;
					break;
				case '!':
					state = State.GOT_EXCLAMATION;
					pos++;
					posInLine++;
					break;
				case '?':
					tokens.add(new Token(Kind.OP_Q, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case ':':
					tokens.add(new Token(Kind.OP_COLON, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '&':
					tokens.add(new Token(Kind.OP_AND, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '|':
					tokens.add(new Token(Kind.OP_OR, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '+':
					tokens.add(new Token(Kind.OP_PLUS, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '*':
					state = State.GOT_STAR;
					pos++;
					posInLine++;
					break;
				case '%':
					tokens.add(new Token(Kind.OP_MOD, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '@':
					tokens.add(new Token(Kind.OP_AT, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '(':
					tokens.add(new Token(Kind.LPAREN, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case ')':
					tokens.add(new Token(Kind.RPAREN, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case '[':
					tokens.add(new Token(Kind.LSQUARE, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case ']':
					tokens.add(new Token(Kind.RSQUARE, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case ';':
					tokens.add(new Token(Kind.SEMI, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				case ',':
					tokens.add(new Token(Kind.COMMA, startPos, 1, line, startPosLine));
					pos++;
					posInLine++;
					break;
				default:
					if (Character.isDigit(ch)) {
						if (ch == '0') {
							pos++;
							posInLine++;
							tokens.add(new Token(Kind.INTEGER_LITERAL, startPos, 1, line, startPosLine));
						} else {
							state = State.GOT_INTEGER;
							pos++;
							posInLine++;
						}
					} else if (Character.isJavaIdentifierStart(ch)) {
						state = State.GOT_IDENTIFIER;
						pos++;
						posInLine++;
					} else {
						throw new LexicalException("Illegal Character " + ch + " encountered ", pos);
					}
				}
				break;
			case GOT_STAR:
				if (ch == '*') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_POWER, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_TIMES, startPos, 1, line, startPosLine));
				}
				state = State.START;
				break;
			case GOT_ASSIGN:
				if (ch == '=') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_EQ, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_ASSIGN, startPos, pos - startPos, line, startPosLine));
				}
				state = State.START;
				break;
			case GOT_FORWARD_SLASH:
				if (ch == '/') {
					pos++;
					posInLine++;
					state = State.GOT_DOUBLE_FORWARD_SLASH;
				} else {
					state = State.START;
					tokens.add(new Token(Kind.OP_DIV, startPos, pos - startPos, line, startPosLine));
				}
				break;
			case GOT_DOUBLE_FORWARD_SLASH:
				if (ch == '\n' || ch == '\r' || ch == EOFchar) {
					state = State.START;
				} else {
					pos++;
					posInLine++;
				}
				break;
			case GOT_GREATER_THAN:
				if (ch == '=') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_GE, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_GT, startPos, pos - startPos, line, startPosLine));
				}
				state = State.START;
				break;
			case GOT_IDENTIFIER:
				if (ch == '\b' || ch == '\f' || ch == '\"' || ch == '\'' || ch == '\\') {
					throw new LexicalException("Escape Sequence encountered within an Identifier", pos);
				} else if (Character.isJavaIdentifierPart(ch) && ch != EOFchar) {
					state = State.GOT_IDENTIFIER;
					pos++;
					posInLine++;
				} else {
					state = State.START;
					String subString = new String(chars, startPos, pos - startPos);
					if (reservedKeywordMap.get(subString) == null) {
						tokens.add(new Token(Kind.IDENTIFIER, startPos, pos - startPos, line, startPosLine));
					} else {
						Kind type = reservedKeywordMap.get(subString);
						tokens.add(new Token(type, startPos, pos - startPos, line, startPosLine));
					}
				}
				break;
			case GOT_STRING_LITERAL:
				if (ch != '"') {
					if (ch == '\\') {
						if (chars[pos + 1] == 'b' || chars[pos + 1] == 't' || chars[pos + 1] == 'n' || chars[pos + 1] == 'f' || chars[pos + 1] == 'r' | chars[pos + 1] == '\"' | chars[pos + 1] == '\'' | chars[pos + 1] == '\\') {
							pos++;
							posInLine++;
						} else if (!(chars[pos + 1] == 'b' || chars[pos + 1] == 't' || chars[pos + 1] == 'n' || chars[pos + 1] == 'f' || chars[pos + 1] == 'r' | chars[pos + 1] == '\"' | chars[pos + 1] == '\'' | chars[pos + 1] == '\\')) {
							throw new LexicalException("Illegal escape sequence encountered within the String Literal", pos + 1);
						}
					} else if (ch == '\n' || ch == '\r') {
						throw new LexicalException("Line Feed or Carriage Return encountered within String Literal", pos);
					} else if (ch == EOFchar) {
						throw new LexicalException("String Literal is not completed with proper closing inverted commas", pos);
					}
					state = State.GOT_STRING_LITERAL;
					pos++;
					posInLine++;
				} else {
					state = State.START;
					pos++;
					posInLine++;
					//String subString = new String(chars, startPos, pos - startPos);
					tokens.add(new Token(Kind.STRING_LITERAL, startPos, pos - startPos, line, startPosLine));
				}
				break;
			case GOT_INTEGER:
				if (Character.isDigit(ch)) {
					state = State.GOT_INTEGER;
					pos++;
					posInLine++;
				} else {
					state = State.START;
					
					String token = new String(chars, startPos, pos - startPos);
					
					try {
						Integer.parseInt(token);
					} catch (NumberFormatException e) {
						throw new LexicalException("Not a valid Integer Literal", startPos);
					}
					
					tokens.add(new Token(Kind.INTEGER_LITERAL, startPos, pos - startPos, line, startPosLine));
				}
				break;
			case GOT_LESS_THAN:
				if (ch == '-') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_LARROW, startPos, pos - startPos, line, startPosLine));
				} else if (ch == '=') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_LE, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_LT, startPos, pos - startPos, line, startPosLine));
				}
				state = State.START;
				break;
			case GOT_MINUS:
				if (ch == '>') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_RARROW, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_MINUS, startPos, pos - startPos, line, startPosLine));
				}
				state = State.START;
				break;
			case GOT_EXCLAMATION:
				if (ch == '=') {
					pos++;
					posInLine++;
					tokens.add(new Token(Kind.OP_NEQ, startPos, pos - startPos, line, startPosLine));
				} else {
					tokens.add(new Token(Kind.OP_EXCL, startPos, pos - startPos, line, startPosLine));
				}
				state = State.START;
				break;
			default:
				
			}
		}
		return this;

	}


	/**
	 * Returns true if the internal interator has more Tokens
	 * 
	 * @return
	 */
	public boolean hasTokens() {
		return nextTokenPos < tokens.size();
	}

	/**
	 * Returns the next Token and updates the internal iterator so that
	 * the next call to nextToken will return the next token in the list.
	 * 
	 * It is the callers responsibility to ensure that there is another Token.
	 * 
	 * Precondition:  hasTokens()
	 * @return
	 */
	public Token nextToken() {
		return tokens.get(nextTokenPos++);
	}
	
	/**
	 * Returns the next Token, but does not update the internal iterator.
	 * This means that the next call to nextToken or peek will return the
	 * same Token as returned by this methods.
	 * 
	 * It is the callers responsibility to ensure that there is another Token.
	 * 
	 * Precondition:  hasTokens()
	 * 
	 * @return next Token.
	 */
	public Token peek() {
		return tokens.get(nextTokenPos);
	}
	
	
	/**
	 * Resets the internal iterator so that the next call to peek or nextToken
	 * will return the first Token.
	 */
	public void reset() {
		nextTokenPos = 0;
	}

	/**
	 * Returns a String representation of the list of Tokens 
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Tokens:\n");
		for (int i = 0; i < tokens.size(); i++) {
			sb.append(tokens.get(i)).append('\n');
		}
		return sb.toString();
	}

}