package example1;

public class Duck implements Animal { // Duck must implement Animal Interface

	@Override
	public void talk() {
		System.out.println("quack");
	}

    public void swim() {
        System.out.println("swim");
    }

}
