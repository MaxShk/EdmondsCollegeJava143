/*
 * Maksym Shkola
 * 6/8/2023
 * Group Project ; CS143 Spring
 * Group #4
 * 
 * Description: The Personal Information Database Management System is
 * a program that manipulates a person(s) data. The program asks the user
 * to import a file contianing user data. Then the program displays the user with options
 * on how to manipulate that data in addition to encrypting/decrypting the data, and
 * veryfiying whether an individual is at-risk for identity theft.
 * 
 * Main Options:
 *  1) Binary Tree Management - Add, Edit, Remove, and Display All Players
 *  2) Encryption/Decryption - Encrypt and Decrypt a persons information
 *  3) Identity Theft Detector - Evaluates if an individual is at-risk for identity theft
 * 
 * Methods:
 *  1) insert
 *  2) insertRecursively (insert helper method)
 *  3) inOrderTraversal
 *  4) inOrderTraversal (helper method)
 *  5) add
 *  6) addRecursively (add helper method)
 *  7) remove
 *  8) removeRecursively (remove helper method)
 *  9) findMinNode
 *  10) editPerson
 *  11) editPersonRecursively (edit helper method)
 * 
 * BinaryTreeManagement() class represents a binary tree data
 * structure for managing Person objects.
 * 
 *  
 */

import java.util.ArrayList;

class BinaryTreeManagement {
    public Node root;

    // Node class for the binary tree
    static class Node {
        Person data;
        Node left;
        Node right;

        Node(Person person) {
            this.data = person;
            this.left = null;
            this.right = null;
        }
    }

    // Constructor to initialize BinaryTreeManagement with an ArrayList of Person objects
    // parameter represents a collection of Person objects that will
    // be used to construct the binary tree.
    BinaryTreeManagement(ArrayList<Person> people) {
        for (Person person : people) {
            insert(person);
        }
    }

    // Insert a person into the binary tree
    // person parameter represents a collection of Person objects
    // that will be used to construct the binary tree.
    private void insert(Person person) {
        if (root == null) {
            root = new Node(person);
        } else {
            insertRecursively(root, person);
        }
    }

    /*
     * The method insertRecursively has two parameters:
     *  Node currentNode: It represents the current node
     *  in the binary tree where the insertion is being performed.
     *  This node is used to determine the proper position for
     *  inserting the Person object. Person person: It represents
     *  the Person object that needs to be inserted into the binary tree.
     *  The purpose of the insertRecursively method is to insert a Person
     *  object into the binary tree in a recursive manner. It compares
     *  the first name of the person with the first name of the currentNode.data
     *  to determine whether the person should be inserted to the left or right
     *  of the currentNode. If the first name of the person is less than the first
     *  name of the currentNode.data, it recursively calls insertRecursively
     *  on the left subtree. If the first name is greater, it recursively calls
     *  insertRecursively on the right subtree. This process continues until an
     *  appropriate position is found where the person can be inserted as a
     *  new node in the binary tree.
     */
    private void insertRecursively(Node currentNode, Person person) {
        if (person.getFirstName().compareTo(currentNode.data.getFirstName()) < 0) {
            if (currentNode.left == null) {
                currentNode.left = new Node(person);
            } else {
                insertRecursively(currentNode.left, person);
            }
        } else {
            if (currentNode.right == null) {
                currentNode.right = new Node(person);
            } else {
                insertRecursively(currentNode.right, person);
            }
        }
    }

    // Perform an in-order traversal of the binary tree
    void inOrderTraversal() {
        inOrderTraversal(root);
    }

    // Helper method to perform an in-order traversal of the binary tree
    // node parameter
    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("First Name: " + node.data.getFirstName());
            System.out.println("Last Name: " + node.data.getLastName());
            System.out.println("Social Security: " + node.data.getSsid());
            System.out.println("Address: " + node.data.getAddress());
            System.out.println("First Login Country: " + node.data.getCountryOne());
            System.out.println("Second Login Country: " + node.data.getCountryTwo());
            System.out.println("Third Login Country: " + node.data.getCountryThree());
            System.out.print("\n\n");
            inOrderTraversal(node.right);
        }
    }

        // Add a person to the binary tree
    public void add(Person person) {
        root = addRecursively(root, person);
    }

    /*
     * The addRecursively method has two parameters: Node currentNode:
     *  It represents the current node in the binary tree where the insertion
     *  is being performed. This node is used to determine the proper position
     *  for inserting the Person object. Person person: It represents the
     *  Person object that needs to be inserted into the binary tree. The
     *  purpose of the addRecursively method is to insert a Person object
     *  into the binary tree in a recursive manner. It follows the same
     *  logic as the insertRecursively method. If the currentNode is null,
     *  indicating an empty subtree, a new node with the person object is
     *  created and returned. If the first name of the person is less than
     *  the first name of the currentNode.data, the addRecursively method
     *  is called on the left subtree. If the first name is greater, the
     *  method is called on the right subtree. This process continues
     *  recursively until an appropriate position is found where the person
     *  can be inserted as a new node in the binary tree.
     *  The updated currentNode is then returned.
     */
    private Node addRecursively(Node currentNode, Person person) {
        if (currentNode == null) {
            return new Node(person);
        }

        if (person.getFirstName().compareTo(currentNode.data.getFirstName()) < 0) {
            currentNode.left = addRecursively(currentNode.left, person);
        } else {
            currentNode.right = addRecursively(currentNode.right, person);
        }

        return currentNode;
    }



    // Remove a person from the binary tree
    public void remove(Person person) {
        root = removeRecursively(root, person);
    }

    /*
     * The removeRecursively method has two parameters: Node currentNode
     *  and Person person. It recursively removes a Person object from
     *  the binary tree. It traverses the tree to find the node containing
     *  the specified person. There are three cases to handle: Node has
     *  no children (leaf node): Return null to remove the node. Node 
     * has one child: Return the non-null child to replace the current node.
     *  Node has two children: Find the successor
     *  (smallest value in the right subtree), replace the current node's
     *  data with the successor's data, and remove the successor from the
     *  right subtree. The method returns the updated currentNode.
     */
    private Node removeRecursively(Node currentNode, Person person) {
        if (currentNode == null) {
            return null;
        }

        // Traverse the tree to find the node containing the person
        if (person.getFirstName().compareTo(currentNode.data.getFirstName()) < 0) {
            currentNode.left = removeRecursively(currentNode.left, person);
        } else if (person.getFirstName().compareTo(currentNode.data.getFirstName()) > 0) {
            currentNode.right = removeRecursively(currentNode.right, person);
        } else {
            // Found the node containing the person

            // Case 1: Node has no children (leaf node)
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            }
            
            // Case 2: Node has one child
            if (currentNode.left == null) {
                return currentNode.right;
            }
            
            if (currentNode.right == null) {
                return currentNode.left;
            }

            // Case 3: Node has two children
            // Find the successor (smallest value in the right subtree)
            Node successor = findMinNode(currentNode.right);
            
            // Replace the current node's data with the successor's data
            currentNode.data = successor.data;
            
            // Remove the successor from the right subtree
            currentNode.right = removeRecursively(currentNode.right, successor.data);
        }

        return currentNode;
    }

        // Helper method to find the node with the minimum value in the subtree
    private Node findMinNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Edit the data of a person in the binary tree
    void editPerson(Person person, Person newPerson) {
        root = editPersonRecursively(root, person, newPerson);
    }

    /*
     * The editPersonRecursively method has three parameters:
     *  Node currentNode, Person person, and Person newPerson.
     *  It recursively edits the data of a Person object in the binary tree. 
     * The method traverses the tree to find the node containing the
     *  specified person. If the first name of the person is less than
     *  the first name of the current node's data, it continues the search
     *  in the left subtree. If the first name is greater, it continues in
     *  the right subtree. When it finds the matching node, it updates the
     *  data with the new person. The method returns the updated currentNode.
     */
    private Node editPersonRecursively(Node currentNode, Person person, Person newPerson) {
        if (currentNode == null) {
            return null;
        }

        // Traverse the tree to find the node to edit
        if (person.getFirstName().compareTo(currentNode.data.getFirstName()) < 0) {
            currentNode.left = editPersonRecursively(currentNode.left, person, newPerson);
        } else if (person.getFirstName().compareTo(currentNode.data.getFirstName()) > 0) {
            currentNode.right = editPersonRecursively(currentNode.right, person, newPerson);
        } else {
            // Found the node to edit
            currentNode.data = newPerson;
        }

        return currentNode;
    }

}
