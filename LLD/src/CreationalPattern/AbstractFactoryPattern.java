/*
Creates families of related objects without specifying concrete classes. Provides an interface for 
creating multiple related products.

Structure :
1. Abstract Factory → interface for creating products
2. Concrete Factory → creates related product family
3. Abstract Products → product interfaces
4. Concrete Products → implementations
*/

interface Button{
    void paint();
}

class WinButton implements Button{
    @Override 
    public void paint(){
        System.out.println("Rendering a button in window style");
    }
}

class MacButton implements Button{
    @Override
    public void paint(){
        System.out.println("Rendering a button in mac style");
    }
}

interface CheckBox{
    void paint();
}

class WinCheck implements CheckBox{
    @Override
    public void paint(){
        System.out.println("Rendering a checkBox in window style");
    }
}

class MacCheck implements CheckBox{
    @Override
    public void paint(){
        System.out.println("Rendering a checkBox in mac style");
    }
}

interface GUIFactory{
    Button createButton();
    CheckBox createCheckBox();
}

class WinFactory implements GUIFactory{
    @Override
    public Button createButton(){
        return new WinButton();
    }

    @Override
    public CheckBox createCheckBox(){
        return new WinCheckBox();
    }
}

class MacFactory implements GUIFactory{

    @Override
    public Button createButton(){
        return new MacButton();
    }

    @Override
    public CheckBox createCheckBox(){
        return new MacCheck();
    }
}

public class AbstractFactoryPattern{

    private static Application configureApplication(){

        Application app;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("win")){
            factory = new WinFactory();
        }else{
            factory = new MacFactory();
        }
        app = new Application(factory);
        return app;
    }
}

class Application{

    private final Button button;
    private final CheckBox checkbox;

    public Application(GUIFactory factory){
        button = factory.createButton();
        checkBox = factory.createCheckBox();
    }

    public void paint(){
        button.paint();
        button.checkBox();
    }
}