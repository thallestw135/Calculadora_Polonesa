import java.util.*;

public class CalculadoraPolonesa {

    // posfixa
    public static List<String> infixToPostfix(List<String> infixList) {
        List<String> postfixList = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : infixList) {
            if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    if (precedence(token) <= precedence(stack.peek())) {
                        postfixList.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfixList.add(stack.pop());
                }
                stack.pop();
            } else {
                postfixList.add(token);
            }
        }

        while (!stack.isEmpty()) {
            postfixList.add(stack.pop());
        }

        return postfixList;
    }

    // Convertendo de posfixa para prefixa
    public static List<String> postfixToPrefix(List<String> postfixList) {
        Stack<String> stack = new Stack<>();

        for (String token : postfixList) {
            if (isOperator(token)) {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String expression = token + " " + operand1 + " " + operand2;
                stack.push(expression);
            } else {
                stack.push(token);
            }
        }

        return Arrays.asList(stack.pop().split(" "));
    }

    public static int evaluatePostfix(List<String> postfixList) {
        Stack<Integer> stack = new Stack<>();

        for (String token : postfixList) {
            if (isOperator(token)) {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = evaluate(token, operand1, operand2);
                stack.push(result);
            } else {
                int operand = Integer.parseInt(token);
                stack.push(operand);
            }
        }

        return stack.pop();
    }

    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    public static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                throw new IllegalArgumentException("Operador inválido: " + operator);
        }
    }

    public static int evaluate(String operator, int operand1, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Operador inválido: " + operator);
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Digite a operação infixa: ");
        String infix = inputScanner.nextLine();

        List<String> infixList = Arrays.asList(infix.split(" "));
        List<String> postfixList = infixToPostfix(infixList);
        List<String> prefixList = postfixToPrefix(postfixList);

        System.out.print("Operação em notação pós-fixa: ");
        System.out.println(postfixList);

        System.out.print("Operação em notação pré-fixa: ");
        System.out.println(prefixList);

        int result = evaluatePostfix(postfixList);

        System.out.println("Resultado: " + result);
        inputScanner.close();
    }
}