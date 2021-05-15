package example1;

public class Cat implements Animal { // Cat must implement Animal Interface

	@Override
	public void talk() {
		System.out.println("meow");
	}

    public void run() {
        System.out.println("run");
    }

    public static void main(String[] args) {
        Animal cat = new Cat();
        Animal duck = new Duck();
        cat.talk(); // makes meow
        duck.talk(); // makes quack
        if(duck instanceof Duck) {
            ((Duck) duck).swim(); // swim
        }  
    }
}
