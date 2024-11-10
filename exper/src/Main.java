public class Main
{
    public static void main (String [] Args)
    {
        BinaryTree tree = new BinaryTree();
        tree.insertNode(5);
        tree.insertNode(3);
        tree.insertNode(1);
        tree.insertNode(4);
        tree.insertNode(10);
        tree.insertNode(15);
        tree.insertNode(7);
        System.out.println(tree.root.right.left.data);
    }
}