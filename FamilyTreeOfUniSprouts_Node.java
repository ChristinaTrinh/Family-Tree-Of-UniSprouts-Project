import org.w3c.dom.NameList;

public class FamilyTreeOfUniSprouts_Node {
    private int generationLevel;
    private String name;
    private FamilyTreeOfUniSprouts_Node previous, next, children;

    public FamilyTreeOfUniSprouts_Node() {
        generationLevel = -1;
        name = "";
        previous = next = children = null;
    }

    public FamilyTreeOfUniSprouts_Node(int generationLevel, String name) {
        this.generationLevel = generationLevel;
        this.name = name;
        this.previous = null;
        next = null;
        children = null;
    }

    // ================== Accessor Methods ==================
    public int getGenerationLevel() {
        return generationLevel;
    }

    public String getName() {
        return name;
    }

    public FamilyTreeOfUniSprouts_Node getNext() {
        return next;
    }

    public FamilyTreeOfUniSprouts_Node getPrevious() {
        return previous;
    }

    public FamilyTreeOfUniSprouts_Node getChildren() {
        return children;
    }

    public boolean hasNext()
    {
        if(this.getNext() != null)
            return true;
        return false;
    }

    public boolean hasChildren()
    {
        if(this.getChildren() != null)
            return true;
        return false;
    }

    public boolean hasPrevious()
    {
        if(this.getPrevious() != null)
            return true;
        return false;
    }

    // ================== Mutator Methods ==================
    public void setPrevious(FamilyTreeOfUniSprouts_Node previous) {
        this.previous = previous;
    }

    public void setNext(FamilyTreeOfUniSprouts_Node next) {
        this.next = next;
    }

    public void setChildren(FamilyTreeOfUniSprouts_Node children) {
        this.children = children;
    }

}
