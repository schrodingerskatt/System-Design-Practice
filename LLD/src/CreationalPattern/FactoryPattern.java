/*
A parent class defines a method for object creation, but subclasses decide which concrete object to
instantiate.

Structure :
1. Creator → declares factory method
2. Concrete Creator → overrides factory method
3. Product → interface/base class
4. Concrete Product → actual implementation
*/

interface Shape{
    void draw();
}

class Circle implements Shape{
    public void draw(){
        System.out.println("Drawing a circle");
    }
}

class Rectangle implements Shape{
    public void draw(){
        System.out.println("Drawing a rectangle");
    }
}

class ShapeFactory{

    public static Shape createShape(String shapeType){

        if(shapeType.equalsIgnoreCase("Circle")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("Rectangle")){
            return new Rectangle();
        }
    return null;
    }
}