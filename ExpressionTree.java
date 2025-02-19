//Nicholas Pietruszka
import java.util.Scanner;

class TreeNode
{
    String value;
    TreeNode left, right;

    TreeNode(String item)
    {
        value = item;
        left = right = null;
    }
}

public class ExpressionTree
{

    // Method to check if a given character is an operator
    boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Method to check the precedence of operators
    int precedence(char op)
    {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    // Method to construct the expression tree from an infix expression
    TreeNode constructTree(String infix)
    {
        return constructTreeHelper(infix, 0, infix.length() - 1);
    }

    TreeNode constructTreeHelper(String infix, int start, int end)
    {
        if (start > end)
        {
            return null;
        }

        // Find the operator with the lowest precedence
        int minPrecedence = Integer.MAX_VALUE;
        int minPrecedenceIndex = -1;

        for (int i = start; i <= end; i++)
        {
            char c = infix.charAt(i);

            if (isOperator(c))
            {
                int prec = precedence(c);
                if (prec <= minPrecedence)
                {
                    minPrecedence = prec;
                    minPrecedenceIndex = i;
                }
            }
        }

        // If no operator is found, it's a number
        if (minPrecedenceIndex == -1)
        {
            return new TreeNode(infix.substring(start, end + 1));
        }

        TreeNode node = new TreeNode(Character.toString(infix.charAt(minPrecedenceIndex)));
        node.left = constructTreeHelper(infix, start, minPrecedenceIndex - 1);
        node.right = constructTreeHelper(infix, minPrecedenceIndex + 1, end);

        return node;
    }

    // Method to evaluate the expression tree
    double evaluate(TreeNode root)
    {
        if (root == null)
        {
            return 0;
        }

        if (root.left == null && root.right == null)
        {
            return Double.parseDouble(root.value);
        }

        double leftEval = evaluate(root.left);
        double rightEval = evaluate(root.right);

        switch (root.value)
        {
            case "+":
                return leftEval + rightEval;
            case "-":
                return leftEval - rightEval;
            case "*":
                return leftEval * rightEval;
            case "/":
                if (rightEval == 0)
                {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return leftEval / rightEval;
        }

        return 0;
    }

    public static void main(String[] args) {
        ExpressionTree et = new ExpressionTree();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the expression: ");
        String infix = input.nextLine(); // Example infix expression without parentheses
        TreeNode root = et.constructTree(infix);

        System.out.println("The result of the expression is: " + et.evaluate(root));

        input.close();
    }
}
