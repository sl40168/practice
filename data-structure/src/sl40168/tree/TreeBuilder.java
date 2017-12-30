package sl40168.tree;

public class TreeBuilder {
	static Node build() {
		Node root = new Node(8,
				new Node(4, new Node(2, new Node(1, null, null), new Node(3, null, null)),
						new Node(6, new Node(5, null, null), new Node(7, null, null))),
				new Node(12, new Node(10, new Node(9, null, null), new Node(11, null, null)),
						new Node(14, new Node(13, null, null), new Node(15, null, null))));
		return root;
	}

	static String preorder(Node root) {
		StringBuilder sb = new StringBuilder();
		sb.append(root.data).append("->");
		if (null != root.getLeft())
			sb.append(preorder(root.getLeft()));
		if (null != root.getRight())
			sb.append(preorder(root.getRight()));
		return sb.toString();
	}

	static String inorder(Node root) {
		StringBuilder sb = new StringBuilder();
		if (null != root.getLeft())
			sb.append(inorder(root.getLeft()));
		sb.append(root.getData()).append("->");
		if (null != root.getRight())
			sb.append(inorder(root.getRight()));
		return sb.toString();
	}

	static String postorder(Node root) {
		StringBuilder sb = new StringBuilder();
		if (null != root.getLeft())
			sb.append(postorder(root.getLeft()));
		if (null != root.getRight())
			sb.append(postorder(root.getRight()));
		sb.append(root.getData()).append("->");
		return sb.toString();
	}
	
	static String depthFirst(Node root) {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
	
	static String widthFirst(Node root) {
		StringBuilder sb =  new StringBuilder();
		
		return sb.toString();
	}

	public static void main(String[] args) {
		Node root = build();
		System.out.println("preorder:");
		System.out.println(preorder(root));
		System.out.println("inorder:");
		System.out.println(inorder(root));
		System.out.println("postorder:");
		System.out.println(postorder(root));
	}
}
