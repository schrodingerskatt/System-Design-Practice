import java.util.*;

public class FileSystem{

    static class Dir{
        String name;
        Dir parent;
        TreeMap<String, Dir>children;

        Dir(String name, Dir parent){
            this.name = name;
            this.parent = parent;
            this.children = new TreeMap<>();
        }
    }

    private Dir root;
    private Dir cwd;

    public FileSystem(){
        root = new Dir("", null);
        root.parent = root; // root is parent of itself
        cwd = root;
    }

}