import java.util.*;

import javax.lang.model.util.ElementScanner14;
 
public class FamilyTreeOfUniSprouts_CLIENT
{
    static FamilyTreeOfUniSprouts_Node root;
    static String names[][] = {{"Jones", "ROOT-Node"},
    {"Bill", "Jones"},  {"Katy", "Jones"}, {"Mike", "Jones"}, {"Tom", "Jones"},
    {"Dave", "Bill"},  {"Mary", "Bill"}, {"Leo", "Katy"}, {"Betty", "Mike"}, {"Roger","Mike"},
    {"Larry","Mary"}, {"Paul","Mary"}, {"Penny","Mary"}, {"Don","Betty"}};    
    public static void main(String[]args)
    {
        int  playAgain;
        String name, namesList= "\n"; 
        Scanner sc = new Scanner(System.in);
        
        // Build the Family of UniSprouts Tree       
        boolean success = buildFamilyTreeOfUniSprouts();
        
        if(success)
        {
            do 
            {
                // Gather names
                for (int r=0; r<names.length; r++)
                    namesList += "   " + names[r][0] + "     \t" + names[r][1] + "\n";                
             
                // Output namesList         
                System.out.println(" nameList: \n  Parent Child" + namesList);
           
                printFamilyTreeOfUniSprouts(root);            
             
                // Input name + print all relatives
                System.out.print("\n\n Enter a name from which to get " + 
                             "GrandParent/Parent/Siblings/Cousins/Children/GrandChildren/"+
                             "Nieces/Nephews: ");
                name = sc.next();
                printRelatives(name);
                                    
                // Asking player if they want to continue to play the game
                System.out.print("\n\n Play Again? (1=yes, 2=no): ");
                playAgain = sc.nextInt();            
            } while (playAgain == 1);
        }
    }   
 
    public static boolean buildFamilyTreeOfUniSprouts()
    {      
        root = new FamilyTreeOfUniSprouts_Node(0, names[0][0]);
        FamilyTreeOfUniSprouts_Node current = root;

        for(int i = 1; i < names.length; i++) // going in the whole list 
        {
            FamilyTreeOfUniSprouts_Node add = exploreHelper(names[i][1], current); //calling the helper method to find the parent node
            if(add != null) // if the parent node was found, then we add it into the tree
                placeNodeInFamilyTreeOfUniSprouts(add, new FamilyTreeOfUniSprouts_Node(add.getGenerationLevel()+1, names[i][0]));
            else //if add == null then the parent doesn't exist so we prompt the user error
            {
                System.out.println("There was an error - Parent Not Found. Please check input data. Stopping program.");
                return false;
            }
        }
        return true;
    }  

    //backtracking recursive
    public static FamilyTreeOfUniSprouts_Node exploreHelper(String name, FamilyTreeOfUniSprouts_Node current)
    {
        if(current == null)
            return null;
        else if(current.getName().equals(name))
            return current;
        else
        {
            FamilyTreeOfUniSprouts_Node node1 = exploreHelper(name, current.getNext());
            FamilyTreeOfUniSprouts_Node node2 = exploreHelper(name, current.getChildren());
            if(node1 != null)
                return node1;
            return node2;
        }
    }
     
    public static void placeNodeInFamilyTreeOfUniSprouts(FamilyTreeOfUniSprouts_Node parent, FamilyTreeOfUniSprouts_Node child)                                                                        
    {    
        FamilyTreeOfUniSprouts_Node current = parent;
        if(!parent.hasChildren()) // if the parent doesn't have a child, then set their children to the node
        {
            parent.setChildren(child);
            child.setPrevious(parent);
        }
        else{ // if the parent already has a child, then connect to their sibling
            current = parent.getChildren();
            while(current.hasNext())
                current = current.getNext();
            current.setNext(child);
            child.setPrevious(current);
        }
    }
    
    //printing the relatives or family members of a given person
    public static void printRelatives(String name)
    {
        if(exploreHelper(name, root) == null)
            System.out.println("Name not found.");
        else
        {
            System.out.println();
            System.out.println("Below is " + name + "'s family members and relatives: ");
            printGrandparent(name);
            printParent(name);
            Queue<FamilyTreeOfUniSprouts_Node> q = printChildren(name);
            if(q.size() == 0)
                System.out.print("- No child");
            else
            {
                if(q.size() > 0)
                    System.out.print("- Child(ren): " + q.remove().getName());
                while(q.size() > 0)
                    System.out.print(", " + q.remove().getName());
            }
            System.out.println();
            printGrandChildren(name);
            System.out.println();
            q = printSibling(name);
            if(q.size() == 0)
                System.out.println("- No sibling");
            else
            {
                System.out.print("- Sibling(s): " + q.remove().getName());
                while(q.size() > 0)
                    System.out.print(", " + q.remove().getName());
                System.out.println();
            }
            printCousin(name);
            q = printNieceNephews(name);
            if(q.size() == 0)
                System.out.println("- No niece/nephews");
            else
            {
                System.out.print("- Niece/Nephews: " + q.remove().getName());
                while(q.size() > 0)
                    System.out.print(", " + q.remove().getName());
                System.out.println();
            }
        }
    }

    //printing the grandparent of a given person
    private static void printGrandparent(String name)
    {
        FamilyTreeOfUniSprouts_Node current = root;
        current = exploreHelper(name, current);
        final int grandparentLevel = current.getGenerationLevel()-2;
        int level = current.getGenerationLevel();
        boolean found = false;

        while(current.hasPrevious()) //looking for the grandparent node by going back to previous node
        {
            current = current.getPrevious();
            level = current.getGenerationLevel();
            if(level == grandparentLevel) //if the first node found is the grandparent generation then the node is grandparent
            {
                found = true;
                break;
            }
        }
        if(found)
            System.out.println("- Grandparent: " + current.getName());
        else
            System.out.println("- No Grandparent");
    }

    //printing the parent of a given person
    private static void printParent(String name)
    {
        FamilyTreeOfUniSprouts_Node current = root;
        current = exploreHelper(name, current);
        final int parentLevel = current.getGenerationLevel()-1;
        int level = current.getGenerationLevel();
        boolean found = false;

        while(current.hasPrevious())
        {
            current = current.getPrevious();
            level = current.getGenerationLevel();
            if(level == parentLevel)
            {
                found = true;
                break;
            }
        }
        if(found)
            System.out.println("- Parent: " + current.getName());
        else
            System.out.println("- No parent");
    }

    //printing the children of a given person
    private static Queue<FamilyTreeOfUniSprouts_Node> printChildren(String name)
    {
        boolean found = false;
        Queue<FamilyTreeOfUniSprouts_Node> q = new LinkedList<FamilyTreeOfUniSprouts_Node>();
        FamilyTreeOfUniSprouts_Node current = root;
        current = exploreHelper(name, current);
        if(current.hasChildren())
        {
            found = true;
            current = current.getChildren();
            q.add(current);
        }

        while(found && current.hasNext())
        {
            current = current.getNext();
            q.add(current);
        }
        return q;
    }

    //printing the grandchildren of a given person
    private static void printGrandChildren(String name)
    {
        FamilyTreeOfUniSprouts_Node current = root;
        Queue<FamilyTreeOfUniSprouts_Node> q = new LinkedList<FamilyTreeOfUniSprouts_Node>();
        current = exploreHelper(name, current);
        boolean found = false;
        boolean print = false;
       
        if(current.hasChildren())
        {
            current = current.getChildren();
            found = true;
            q = printChildren(current.getName());
            if(q.size() > 0)
            {
                System.out.print("- Grandchild(ren): " + q.remove().getName());
                print = true;
                while(q.size() > 0)
                    System.out.print(", " + q.remove().getName());
            }
        }
        while(found && current.hasNext())
        {
            current = current.getNext();
            q = printChildren(current.getName());
            if(!print && q.size() > 0)
            {
                print = true;
                System.out.print("- Grandchild(ren): " + q.remove().getName());
            }
            while(q.size()>0)
                System.out.print(", " + q.remove().getName());
        }
        if(!print)
            System.out.print("- No grandchild");
    }

    //printing the siblings of a given person
    private static Queue<FamilyTreeOfUniSprouts_Node> printSibling(String name)
    {
        FamilyTreeOfUniSprouts_Node current1 = root; 
        current1 = exploreHelper(name, current1);
        FamilyTreeOfUniSprouts_Node current2 = current1;
        int level = current1.getGenerationLevel();
        Queue<FamilyTreeOfUniSprouts_Node> q = new LinkedList<FamilyTreeOfUniSprouts_Node>();

        while(current2.hasNext())
        {
            current2 = current2.getNext();
            q.add(current2);
        }
        while(current2 != current1)
            current2 = current2.getPrevious();
        while(current2.hasPrevious())
        {
            current2 = current2.getPrevious();
            if(current2.getGenerationLevel()!=level)
                break;
            else
                q.add(current2);
        }
        return q;
    }

    //printing the cousin of a given person
    private static void printCousin(String name)
    {
        FamilyTreeOfUniSprouts_Node current = root;
        current = exploreHelper(name, current);
        int parentLevel = current.getGenerationLevel()-1;
        int level = current.getGenerationLevel();

        //getting the parent node
        while(current.hasPrevious())
        {
            current = current.getPrevious();
            level = current.getGenerationLevel();
            if(level == parentLevel)
                break;
        }

        //cousins are parents' siblings' children
        Queue<FamilyTreeOfUniSprouts_Node> q = printNieceNephews(current.getName());
        if(q.size() == 0)
            System.out.println("- No cousin");
        else
        {
            System.out.print("- Cousin(s): " + q.remove().getName());
            while(q.size() > 0)
                System.out.print(", " + q.remove().getName());
            System.out.println();
        }
    }

    //printing the niece/nephews of a given person
    private static Queue<FamilyTreeOfUniSprouts_Node> printNieceNephews(String name)
    {
        Queue<FamilyTreeOfUniSprouts_Node> q1 = printSibling(name); // getting the sibling list to get each sibling's children
        Queue<FamilyTreeOfUniSprouts_Node> nieceNephews = new LinkedList<FamilyTreeOfUniSprouts_Node>();
        while(q1.size() > 0)
        {
            FamilyTreeOfUniSprouts_Node current = q1.remove();
            Queue<FamilyTreeOfUniSprouts_Node> q2 = printChildren(current.getName());
            while(q2.size() > 0)
                nieceNephews.add(q2.remove());
        }
        return nieceNephews;
    }

    public static void printFamilyTreeOfUniSprouts(FamilyTreeOfUniSprouts_Node current) {
        for (String row : subtreeToString(current))
            System.out.println(row);
    } 

    public static String[] subtreeToString(FamilyTreeOfUniSprouts_Node node) {
        final List<String[]> subtrees = new LinkedList<>();
        int rowCount = 0;
        int colCount = 0;
        for (FamilyTreeOfUniSprouts_Node c = node.getChildren(); c != null; c = c.getNext()) {
            final String[] subtree = subtreeToString(c);
            rowCount = Math.max(subtree.length, rowCount);
            colCount += subtree[0].length();
            subtrees.add(subtree);
        }
        if (rowCount == 0)
            rowCount++;
        else
            rowCount += 2;
        final String[] rowStrings = new String[rowCount];
        final String name = node.getName();
        colCount += subtrees.size() - 1;
        if (name.length() > colCount) {
            if (rowCount > 1)   {
                final String[] firstCol = subtrees.get(0);
                final String firstBorder = " ".repeat((name.length() - colCount) / 2);
                for (int i = 0; i < firstCol.length; i++)
                    firstCol[i] = firstBorder + firstCol[i];
                
                final String[] lastCol = subtrees.get(subtrees.size() - 1);
                final String lastBorder = " ".repeat((name.length() - colCount + 1) / 2);
                for (int i = 0; i < lastCol.length; i++)
                    lastCol[i] += lastBorder;
            }
            colCount = name.length();
        }
        rowStrings[0] = " ".repeat((colCount - name.length()) / 2)
            + name
            + " ".repeat((colCount - name.length() + 1) / 2);
        if (rowCount > 1) {
            final StringBuilder[] builders = new StringBuilder[rowStrings.length - 2];
            for (int i = 0; i < builders.length; i++)
                builders[i] = new StringBuilder(colCount);
            final char[] branchChars = new char[colCount];
            for (int i = 0; i < branchChars.length; i++)
                branchChars[i] = '\u2500';
            final int center = (colCount - 1) / 2;
            int i = 0;
            final ListIterator<String[]> iter = subtrees.listIterator();
            while (iter.hasNext()) {
                final boolean isFirst = !iter.hasPrevious();
                final String[] col = iter.next();
                final int subColSize = col[0].length();
                final boolean isLast = !iter.hasNext();
                final int subCenter = (subColSize - 1) / 2;

                char centerChar = '\u252c';
                if (isFirst ^ isLast) {
                    if (isFirst) {
                        centerChar = '\u250c';
                        for (int j = 0; j < subCenter; j++)
                            branchChars[j] = ' ';
                    }
                    else { // isLast
                        centerChar = '\u2510';
                        for (int j = i + subCenter; j < branchChars.length; j++)
                            branchChars[j] = ' ';
                    }
                }
                else if (isFirst && isLast) {
                    centerChar = ' ';
                    for (int j = 0; j < branchChars.length; j++)
                        branchChars[j] = ' ';
                }
                branchChars[i + subCenter] = centerChar;

                for (int j = 0; j < col.length; j++) {
                    builders[j].append(col[j]);
                    assert col[j].length() == subColSize;
                }
                for (int j = col.length; j < builders.length; j++)
                    builders[j].append(" ".repeat(subColSize));

                if (!isLast) {
                    for (StringBuilder builder : builders)
                        builder.append(' ');
                }
                
                i += subColSize + 1;
            }

            char centerChar;
            switch (branchChars[center]) {
                case '\u2500':
                    centerChar = '\u2534';
                    break;
                case '\u252c': 
                    centerChar = '\u253c';
                    break;
                case ' ':
                    centerChar = '\u2502';
                    break;
                default:
                    throw new IllegalStateException("Illegal central character '" + branchChars[center] + "' in \"" + new String(branchChars) + '"');
            }

            branchChars[center] = centerChar;

            rowStrings[1] = new String(branchChars);

            for (i = 0; i < builders.length; i++)
                rowStrings[i + 2] = builders[i].toString();
        }
        return rowStrings;
    }
}  

