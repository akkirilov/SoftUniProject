
public class Person {
	
	private String name;
	private int age;
	
	public Person() {
		super();
		this.name = "No name";
		this.age = 1;
	}
	
	public Person(int age) {
		this();
		this.age = age;
	}

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}