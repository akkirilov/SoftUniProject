import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ex08_EvaluateExpression {

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);

		Map<String, Integer> priorities = new HashMap<>();
		priorities.put("*", 3);
		priorities.put("/", 3);
		priorities.put("+", 2);
		priorities.put("-", 2);
		priorities.put("(", 1);

		Deque<String> outputQueue = new ArrayDeque<>();
		Deque<String> operatorStack = new ArrayDeque<>();
		Pattern num = Pattern.compile("([0-9])+(\\.[0-9]*)?");

		String[] expression = scanner.nextLine().split(" ");
		scanner.close();
		
		for (int i = 0; i < expression.length; i++) {
			String token = expression[i];
			Matcher numMatcher = num.matcher(token);
			if (numMatcher.find()) {
				outputQueue.add(token);
			} else if (token.equals("(")) {
				operatorStack.push(token);
			} else if (token.equals(")")) {
				while (!operatorStack.peek().equals("(")) {
					outputQueue.add(operatorStack.pop());
				}
				operatorStack.pop();
			} else {
				while (!operatorStack.isEmpty() && priorities.get(token) <= priorities.get(operatorStack.peek())) {
					outputQueue.add(operatorStack.pop());
				}
				operatorStack.push(token);
			}
		}

		while (!operatorStack.isEmpty()) {
			outputQueue.add(operatorStack.pop());
		}

		Deque<Double> resultStack = new ArrayDeque<>();
		while (!outputQueue.isEmpty()) {
			try {
				Double number = Double.parseDouble(outputQueue.peek());
				outputQueue.poll();
				resultStack.push(number);
			} catch (NumberFormatException nfe) {
				String operator = outputQueue.poll();
				Double operand2 = resultStack.pop();
				Double operand1 = resultStack.pop();
				switch (operator) {
				case "+":
					resultStack.push(operand1 + operand2);
					break;
				case "-":
					resultStack.push(operand1 - operand2);
					break;
				case "*":
					resultStack.push(operand1 * operand2);
					break;
				case "/":
					resultStack.push(operand1 / operand2);
					break;
				}
			}
		}

		System.out.printf("%.2f", resultStack.pop());
	}

}
