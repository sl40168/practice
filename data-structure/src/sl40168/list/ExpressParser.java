package sl40168.list;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ExpressParser {
	public static final char OP_ADD = '+';
	public static final char OP_MINUS = '-';
	public static final char OP_MULTI = '*';
	public static final char OP_DEVIDE = '/';
	private static final char BRACE_SMALL_LEFT = '(';
	private static final char BRACE_SMALL_RIGHT = ')';
	private static final char BRACE_MIDDLE_LEFT = '[';
	private static final char BRACE_MIDDLE_RIGHT = ']';
	private static final char BRACE_LARGE_LEFT = '{';
	private static final char BRACE_LARGE_RIGHT = '}';

	public static void main(String[] args) {
		String express = "9 - 3 *2+[(3-1) * (8-2)]*3+10/2";
		List<Node> list = new ExpressParser().split(express);
		for (Node node : list) {
			System.out.println(node.toString());
		}
		System.out.println(express);
		System.out.println(new ExpressParser().parseInternal(express));
		System.out.println(new ExpressParser().parse2(express));
	}

	public ExpressParser() {
	}

	private String parseInternal(String express) {
		List<Node> convert = this.parse(express);
		StringBuilder sb = new StringBuilder();
		for (Node node : convert) {
			sb.append(node.toString());
		}
		return sb.toString();
	}
	
	public List<Node> parse(String express) {
		List<Node> nodes = this.split(express);
		List<Node> convert = this.convert(nodes);
		return convert;
	}


	List<Node> convert(List<Node> nodes) {
		List<Node> result = new LinkedList<Node>();
		
		Stack<Node> stack = new Stack<Node>();
		for (Node node : nodes) {
			if (node.type == NodeType.NUMBER)
				result.add(node);
			else if (node.type == NodeType.OPERATOR) {
				if (stack.empty())
					stack.push(node);
				else if (this.compareOperatorLevel(stack.peek().express.charAt(0), node.express.charAt(0)) < 0) {
					stack.push(node);
				} else {
					Node top = stack.pop();
					while (true) {
						result.add(top);
						if (stack.empty() || this.compareOperatorLevel(stack.peek().express.charAt(0),
								node.express.charAt(0)) > 0)
							break;
						top = stack.pop();
					}
					stack.push(node);
				}
			} else if (node.type == NodeType.EXPRESS) {
				result.addAll(this.convert(node.children));
			}
		}
		while (!stack.empty()) {
			result.add(stack.pop());
		}
		
		return result;
	}
	List<Node> split(String express) {
		List<Node> result = new LinkedList<Node>();
		char current = 0;
		String exps = "";
		NodeType nodeType = NodeType.UNKNOWN;
		for (int i = 0; i < express.length(); i++) {
			current = express.charAt(i);
			NodeType predicateNodeType = this.predicateNodeType(current);
			if (predicateNodeType == NodeType.UNKNOWN)
				continue;
			if (predicateNodeType != nodeType && nodeType != NodeType.UNKNOWN) {// it
																				// means
																				// that
																				// we
																				// are
																				// entering
																				// a
																				// new
																				// express
				Node node = new Node();
				node.express = exps;
				node.type = nodeType;
				exps = "";
				nodeType = NodeType.UNKNOWN;
				if (node.type == NodeType.EXPRESS)
					node.children.addAll(this.split(node.express));
				result.add(node);
			}
			if (this.isNumber(current)) {
				exps += current;
				nodeType = NodeType.NUMBER;
			} else if (this.isOperator(current)) {
				exps += current;
				nodeType = NodeType.OPERATOR;
			} else {
				int matchedBrace = this.getMatchedBrace(express, i);
				exps += express.substring(i + 1, matchedBrace);
				i = matchedBrace;
				nodeType = NodeType.EXPRESS;
			}
		}
		Node node = new Node();
		node.express = exps;
		node.type = nodeType;
		if (node.type == NodeType.EXPRESS)
			node.children.addAll(this.split(node.express));
		result.add(node);
		return result;
	}

	int getMatchedBrace(String express, int current) {
		char c = express.charAt(current);
		int depth = 0;
		for (int i = current + 1; i < express.length(); i++) {
			char at = express.charAt(i);
			if (at == c)
				depth++;
			else if (this.isMatched(c, at) && depth-- == 0) {
				return i;
			}
		}
		return -1;
	}

	NodeType predicateNodeType(char c) {
		if (this.isNumber(c))
			return NodeType.NUMBER;
		if (this.isBrace(c))
			return NodeType.EXPRESS;
		if (this.isOperator(c))
			return NodeType.OPERATOR;
		return NodeType.UNKNOWN;
	}

	boolean isNumber(char c) {
		return 48 <= c && 57 >= c;
	}

	boolean isLeftBrace(char c) {
		return c == BRACE_SMALL_LEFT || c == BRACE_MIDDLE_LEFT || c == BRACE_LARGE_LEFT;
	}

	boolean isRightBrace(char c) {
		return c == BRACE_SMALL_RIGHT || c == BRACE_MIDDLE_RIGHT || c == BRACE_LARGE_RIGHT;
	}

	boolean isBrace(char c) {
		return isLeftBrace(c) || isRightBrace(c);
	}

	boolean isMatched(char current, char expected) {
		return (current == BRACE_SMALL_LEFT && expected == BRACE_SMALL_RIGHT)
				|| (current == BRACE_MIDDLE_LEFT && expected == BRACE_MIDDLE_RIGHT)
				|| (current == BRACE_LARGE_LEFT && expected == BRACE_LARGE_RIGHT);
	}

	boolean isOperator(char c) {
		return c == OP_ADD || c == OP_MINUS || c == OP_MULTI || c == OP_DEVIDE;
	}

	int compareOperatorLevel(char a, char b) {
		int aLevel = a == OP_ADD || a == OP_MINUS ? 1 : ((a == OP_MULTI || a == OP_DEVIDE) ? 2 : 0);
		int bLevel = b == OP_ADD || b == OP_MINUS ? 1 : ((b == OP_MULTI || b == OP_DEVIDE) ? 2 : 0);
		return aLevel - bLevel;
	}
	
	String parse2(String express) {
		StringBuilder sb = new StringBuilder();
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < express.length(); i++) {
			char c = express.charAt(i);
			if (this.isNumber(c))
				sb.append(c);
			else if (c == ' ')
				continue;
			else if (this.isLeftBrace(c))
				stack.push(c);
			else if (this.isRightBrace(c)) {
				while (!this.isMatched(stack.peek(), c)) {
					sb.append(" ").append(stack.pop());
				}
				stack.pop();
			}
			else if (this.isOperator(c)) {
				sb.append(" ");
				if (stack.empty() || this.compareOperatorLevel(c,
						stack.peek()) > 0)
					stack.push(c);
				else {
					char top = stack.peek();
					while(this.compareOperatorLevel(top, c) >= 0) {
						sb.append(" ").append(stack.pop()).append(" ");
						if (stack.empty())
							break;
						top = stack.peek();
					}
					stack.push(c);
				}
			}
		}
		while(!stack.empty()) {
			sb.append(" ").append(stack.pop());
		}
		return sb.toString();
	}

}
