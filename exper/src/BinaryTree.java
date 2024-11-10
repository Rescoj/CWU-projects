public class BinaryTree
{
    //declaring class attributes
    Node root;

    public void insertNode(int data)
    {

        //declaring insert Node
        Node newNode = new Node(data);

        //inserting root if tree is empty
        if (root == null)
        {
            root = newNode;
            return;
        }

        //declaring traversal nodes
        Node previous = root;
        Node temp = root;

        //traversing the tree
        while (temp != null)
        {
            if (newNode.data > temp.data)
            {
                previous = temp;
                temp = temp.right;
            } else if (newNode.data < temp.data)
            {
                previous = temp;
                temp = temp.left;
            }
        }

        //assigning pointers
        newNode.previous = previous;
        if (newNode.data < previous.data)
        {
            previous.left = newNode;
        }
        else
        {
            previous.right = newNode;
        }
    }
}
class Node
{

    //declaring Node class attributes
    int data;
    Node previous;
    Node left;
    Node right;

    //Node constructor
    public Node(int data)
    {
        this.data = data;
        previous = null;
        left = null;
        right = null;
    }
}