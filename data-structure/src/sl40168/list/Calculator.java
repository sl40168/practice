package sl40168.list;

import java.util.List;
import java.util.Stack;

public class Calculator {

	public static void main(String[] args) {
		String express = "9-3*2+[(3-1)*(8-2)]*3+10/2";
		System.out.println(express + " = ");
		System.out.println(calculate(express));
	}
	
	static ExpressParser parser = new ExpressParser();
	
	static int calculate(String express) {
		Stack<Integer> stack = new Stack<Integer>();
		List<Node> nodes = parser.parse(express);
		for (Node node : nodes) {
			if (node.type == NodeType.NUMBER)
				stack.push(Integer.valueOf(node.express));
			else if (node.type == NodeType.OPERATOR) {
				int temp = calculate(stack.pop(), stack.pop(), node.express);
				stack.push(temp);
			}
		}
		return stack.pop();
	}
	
	static int calculate(int a, int b, String operator) {
		if (ExpressParser.OP_ADD == operator.charAt(0))
			return b + a;
		if (ExpressParser.OP_MINUS == operator.charAt(0))
			return b - a;
		if (ExpressParser.OP_MULTI == operator.charAt(0))
			return b * a;
		if (ExpressParser.OP_DEVIDE == operator.charAt(0))
			return b / a;
		return 0;
	}

}
