package sl40168.list;

import java.util.LinkedList;
import java.util.List;

public class Node {
	public NodeType type;
	public String express;
	public List<Node> children = new LinkedList<Node>();
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(String express, NodeType type) {
		this.express = express;
		this.type = type;
	}
	
	@Override
	public String toString() {
		if (NodeType.EXPRESS == this.type) {
			StringBuilder sb = new StringBuilder(" ");
			for (Node child : children) {
				sb.append("(").append(child.toString()).append(") ");
			}
			return sb.toString();
		}
		else
			return null != express ? " " + express + " " : " ";
	}
}
