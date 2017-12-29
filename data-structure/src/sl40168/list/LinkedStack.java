package sl40168.list;

public class LinkedStack<T> {

	public static void main(String[] args) {
		LinkedStack<Integer> ls = new LinkedStack<Integer>();
		for (int i = 1; i < 6; i++) {
			Element<Integer> ele = new Element<Integer>(i);
			ls.push(ele);
		}
		
		Element<Integer> top = ls.pop();
		while (null != top) {
			System.out.println(top.getData());
			top = ls.pop();
		}
	}
	
	Element<T> top;
	
	public void push(Element<T> element) {
		element.setNext(this.top);
		this.top = element;
	}
	
	public Element<T> pop() {
		Element<T> ele = this.top;
		if (null != this.top) {
			this.top = this.top.getNext();	
		}
		return ele;
		
	}
	
	public Element<T> peek() {
		return this.top;
	}
	
	static class Element<T> {
		Element<T> next;
		
		T data;
		
		public Element(T data) {
			this.data = data;
		}
		
		public T getData() {
			return data;
		}
		
		public Element<T> getNext() {
			return next;
		}
		
		public void setNext(Element<T> next) {
			this.next = next;
		}
	}

}
