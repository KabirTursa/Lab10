
package org.example;
import java.io.*;
import java.util.*;;
/*
 * MorseTree.java
 * CSSSKL 143 Binary Search Tree Lab
 * Author: Rob Nash & Kabir Tursa
 * 
 * This class reads in data from a text file ("data.txt") and populates a binary tree with an 
 * ordering constraint.  See the lab instructions for more information, but in general, dots go right 
 * and dashes go left when constructing or traversing a Morse code tree.  Search for //TODO
 * in the code to see what code you have to implement.
 * 
 * Start with the constructor. In your constructor read each line in from the textfile first, 
 * calling add() for each {letter, morseCodeStr} pair.
 * 
 */

public class MorseTree {
    // data member called "root" goes here
    private TreeNode root;
    
    // Complete constructor
    public MorseTree() {
       
		//first, open data.txt, add each line to the tree
		Scanner fin;
		try {
            fin = new Scanner(System.in);
            File data = new File("D:\\IntelliJ\\Lab10\\src\\main\\java\\org\\example\\data.txt");
            BufferedReader parser = new BufferedReader(new FileReader(data));

            String line = parser.readLine();

            while (line != null) {
                char c = line.charAt(0);
                String s = line.substring(2);
                add(s, c);
                System.out.println("Letter:" + c + "\nMorse: " + s);
                line = parser.readLine();
            }

			//for each line in the file, 
			//  get the letter(char) and the Morse string
			//  call add() with this data
			//  print out the letter and Morse string here for debugging

		    fin.close();
            parser.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}

    
    
    public void add(String morseStr, char letter) {
        root = insertInSubtree(morseStr, letter, root);
    }
    
    //TODO: recursively complete this function.  It's only a few characters different from findInSubtree()
	private TreeNode<Character> insertInSubtree(String morseStr, char letter, TreeNode subtree) {
		//base case 1 : subtree is null
        if (subtree == null) {
            subtree = new TreeNode<Character>(letter, null, null);
        }

		//base case 2 : morseStr is of length 0
        if (morseStr.length() == 0) {
            return subtree;
        }
        String condensed;
        try {
            condensed = morseStr.substring(1);
        } catch (IndexOutOfBoundsException e) {
            condensed = "";
        }
		//recursive case 1: the first char in morseStr is a '.', so recursively traverse tree
        if (morseStr.charAt(0) == '.') {
            insertInSubtree(condensed, letter, subtree.right);
        }
		//recursive case 2: the first char in the morseStr is a '-', so recurse accordingly
		if (morseStr.charAt(0) == '-') {
            insertInSubtree(condensed, letter, subtree.left);
        }
		return subtree;  //always the last line, always return the node you are working on
	}
    
    public Character translate(String morseStr) {
        return findInSubtree(morseStr, root);
    }
    
    //TODO: recursively complete this function.  Very similar to insertInSubtree()
	private Character findInSubtree(String morseStr, TreeNode subtree) {

        //base case 1 : subtree is null
        if (subtree == null) {
            return null;
        }

        //base case 2 : morseStr is of length 0
        if (morseStr.length() == 0) {
            return (Character) subtree.data;
        }
        String condensed;
        try {
            condensed = morseStr.substring(1);
        } catch (IndexOutOfBoundsException e) {
            condensed = "";
        }
        //recursive case 1: the first char in morseStr is a '.', so recursively traverse tree
        if (morseStr.charAt(0) == '.') {
            return findInSubtree(condensed, subtree.right);
        }
        //recursive case 2: the first char in the morseStr is a '-', so recurse accordingly
        if (morseStr.charAt(0) == '-') {
            return findInSubtree(condensed, subtree.left);
        }

        //error. This should never return
        return 'E';
	}
    
    //TODO: Non-recursive function that calls other (recursive) functions
	public String translateString(String tokens) {
		String retVal = "";
		//build a scanner here using tokens as input
        Scanner s = new Scanner(tokens);
		//iterate over the tokens calling translate on each token (substring separated by a space)
        s.useDelimiter(" ");
        while (s.hasNext()) {
            char temp = translate(s.next());
            retVal += temp;
        }
		//concat these characters and return them
		
		return retVal;
	}
	
    public String toMorseCode(Character c) {

        //walk the tree looking for the TreeNode with the char c in it
            //preorder walk?
            //inorder walk?
            //postorder walk?
        
        //when you've found the char c, report the path from the root to the node
        //and build the morse code by adding a "." when you go right, "-" when you go left
        return toMorseHelper(root, c, "");
    }

    private String toMorseHelper(TreeNode node, Character c, String morse) {
        if (node == null)
            return null;

        if ((Character) node.data == c) {
            return morse;
        }

        String a = toMorseHelper(node.left, c, morse + "-");
        String b = toMorseHelper(node.right, c, morse + ".");

        if (a != null)
            return a;
        if (b != null)
            return b;
        return null;
    }


    public String toString() {
        return inorderWalk();
    }
    private String inorderWalk() {  
        
        return inOrderHelper("", root);
    }

    private String inOrderHelper(String s, TreeNode node) {

        if (node.left == null)
            s += node.data;
        else {
            s += inOrderHelper(s, node.left);
            s += node.data;
            if (node.right != null)
                s += inOrderHelper(s, node.right);
            else {
                //do nothing
            }
        }

        return s;
    }
    
    public static void main(String[] args) {
        MorseTree mt = new MorseTree();  //builds our tree using data from a file

        //System.out.println(mt.translate("..."));  //prints out S
        //System.out.println(mt.translate("---"));  //prints out O
        //System.out.println(mt.translate(".......-"));  //prints out null
        
        //System.out.println(mt.translateString("... --- ..."));  //SOS
        //System.out.println(mt.toMorseCode('S'));  //find where we are in the tree, remember path to root
    }

    // Inner class to create the linked structure
    private class TreeNode<Character> {
        
        Object data;     // holds a given node’s data
        TreeNode right;
        TreeNode left;
        
        public TreeNode() {
            this.data = null;
            this.right = null;
            this.left = null;
        }

        public TreeNode(Object data, TreeNode right, TreeNode left) {
            this.data = data;
            this.right = right;
            this.left = left;
        }
        
        public void setRight(TreeNode rightNode) {
            this.right = rightNode;
        }
        
         public void setLeft(TreeNode leftNode) {
            this.left = leftNode;
        }
            
    }
}
