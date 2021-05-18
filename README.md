# Structural subtyping in Golang versus Java & Generics

## Einleitung 
Der Artikel wirft einen Blick in die Welt der Polymorphie. Es gibt verschiedene Arten von Formen der Polymorphie. Hier soll auf zwei dieser Arten eingegangen werden. Zuerst wirft der Artikel einen Blick auf die Form der Subtyp Polymorphie. Dabei wird sich herausstellen, dass die Sprache Golang eine spezielle Ausprägung des Subtyping besitzt. Dabei handelt es sich um das sogenannte Structural Subtyping. Dazu im Vergleich wird die Sprache Java gestellt, welche eine andere Form des Subtyping kennt. Im Anschluss soll noch auf parametrische Polymorphie eingegangen werden und welche Vorteile diese mit sich bringt. Dabei wird ein Blick auf Generics in Java geworfen. 

## Subtyping

```mermaid
classDiagram
      Animal <|-- Duck
      Animal <|-- Cat
      Animal : +void talk()
      class Duck{
          +swim()
      }
      class Cat{
          +run()
      }
````
Hier soll zunächst einmal geklärt werden, wobei es sich um Subtyping handelt. Wie bereits erwähnt, handelt es sich beim Subtyping um eine Form der Polymorphie. Es wird dazu verwendet, abstrakteren Code zu schreiben. Als Subtyp wird eine Untereinheit einer bestimmten Entität bezeichnet. In dem Beispiel oben sieht man es konkret. Dort sieht man das `Duck` und `Cat` Subtypen vom Typ `Animal` sind. `Duck` und `Cat` sind also beide speziellere Typen als der Typ `Animal`. Bei `Animal` spricht man auch vom sogenannten Supertypen. Man schreibt auch `Duck <= Animal`.  Im Folgenden werden noch zwei verschiedene Formen des Subtyping vorgestellt.

## Nominal subtyping

Das nominale Subtyping findet in Programmiersprachen wie Java oder C++ Anwendung. Zwei Typen haben dort vereinfacht gesagt denselben Typen, wenn sie den gleichen Namen besitzen. Dies gilt auch wenn ein Typ T1 ein Subtyp von T2 ist, dazu muss der Subtyp jedoch den Supertyp vererbt bekommen. Nun lässt sich beispielsweise `Duck` auch überall dort verwenden, wo ein `Animal` Objekt erwartet wird. Der Grund hierfür ist, das `Duck` ein Subtyp von `Animal` ist.  Dabei ist es besonders wichtig das die Subtypenbeziehung explizit definiert wird. Dies soll nun mit einem Beispiel verdeutlicht werden. 

```java
class Shape { // Supertyp
    int x;
    int y;
}

class Rectangle extends Shape { // Subtype from Shape in Nominal Typed Languages, because Rectangle extends Shape
    int height;
    int width;
}

class Rect { // This is not a Subtype from Shape
    int x;
    int y;
    int height;
    int width;
}

public void main(String[] args) {
    Shape rectangle1 = Rectangle(); // Possible, because Rectangle is a Sutype from Shape
    Shape rectangle2 = Spape(); 
    Shape rectangle3 = Rect(); // Error, this is not possible, but possible in Structual Typed Languages     
}
```

Hier sieht man, dass `Rectangle` ein Subtyp von `Shape` ist. Da wir wie gesagt in einer Nominal Typed Language sind muss die Subtypbeziehung explizit definiert werden, dies erreicht man wie man in dem Beispiel sieht über das `extends`. `Shape` wird also an `Rectangle` vererbt. Die lässt sich auch Formal ausdrücken `Rectangle <= Shape`. In dem Beispiel sieht man aber auch das es nicht reicht, wenn nur die Struktur des Typen mit dem Supertypen übereinstimmt. Dies wird bei der Zuweisung `rectangle3` versucht, was aber nicht möglich ist. Dies ist jedoch in Struktural Typed Sprachen möglich. Auf diese wird nachher noch eingegangen. Es folgt noch ein weiteres Beispiel, um die Polymorphie mit Nominalen Subtypen besser verstehen zu können. 

````java
class Animal {
	public void talk();
}

public class Cat extends Animal { // Cat must implement Animal Interface
	
	@Override
	public void talk() {
		System.out.println("meow");
	}
	
    public void run() {
		System.out.println("run");
    }
}

public class Duck extends Animal { // Duck must implement Animal Interface
	
	@Override
	public void talk() {
		System.out.println("quack");
	}
	
    public void swim() {
        System.out.println("swim");
    }
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
````

Bei diesem Quellcode handelt es sich um Java. Oben wird ein Interface `Animal` definiert. Dieses wird weiter unten in die zwei Klassen `Cat` und `Duck` implementiert. Nun sieht man, dass sich eine Katze oder eine Ente auch einem Tier zuweisen lässt. Wenn man nun die Methode `talk()` aufruft, wird die Implementierung in der jeweiligen Klasse aufgerufen. Daraus folgt das die Katze `meow` und die Ente `quack` sagt. Außerdem sieht man das sich in den Klassen `Duck` und `Cat` noch weitere Methoden definieren lassen. Diese lassen sich aber auch nur aufrufen, wenn die Person von der jeweiligen Klasse war. So lässt sich die Methode `swim()` nur von der Ente aufrufen. Wenn man dies machen möchte, muss man die Klasse `Animal` aber vorher explizit in dem Fall zur `Duck` casten.  Wie dies geht, lässt sich oben im Beispiel Programm nachvollziehen. Dabei sollte man jedoch vorsichtig sein, den ein Tier, das vorher eine Katze war, lässt sich nicht zu einer Ente casten. Dabei würde es zu einem Laufzeitfehler kommen. Dieser Laufzeitfehler lässt sich mit einer Fallunterscheidung vermeiden. Den mit `instanceof` lässt sich in Java feststellen, ob es sich bei der Instanz um das jeweilige Objekt handelt. Im Beispiel sieht man das vor dem Cast geprüft wird, ob das Objekt `duck` wirklich vom Typ `Duck` war. Dies ist in diesem Fall natürlich der Fall.

## Structural subtyping

Golang ist eine Structural-Typed Sprache. Das bedeutet, dass in dieser Sprache nur die Strukturen des spezielleren Typen mit dem Subtypen übereinstimmen muss. Der große Unterschied zu  Nominal-Typed Sprachen ist der, dass das Interface nicht mehr an die Klasse vererbt oder implementiert werden muss. Wobei es in der Programmiersprache Golang keine Klassen, sondern nur Structs gibt. Diese verhalten sich aber ähnlich. Im folgenden Beispiel kann man nachvollziehen, wie dies praktisch in Golang aussieht.

````go
type Animal interface {
	talk()
}

type Cat struct{} // Cat does not implement Animal Interface

type Duck struct{} // Duck does not implement Animal Interface

func (cat Cat) talk() {
	fmt.Printf("meow")
}

func (cat Cat) run() {
	fmt.Printf("run")
}

func (duck Duck) talk() {
	fmt.Printf("quack")
}

func (duck Duck) swim() {
	fmt.Printf("swim")
}

func main() {
	var animal Animal
	animal = Duck{}
	animal.talk() // makes "quack"
}
````

Ganz oben im Programm findet man die Definition des `Animal` Interfaces wieder. Dieses wird jedoch wie bereits erwähnt nirgends implementiert oder vererbt. Es dient lediglich als Supertyp, dem ebenfalls die spezielleren Typen zugewiesen werden können. In dem Fall sind die spezielleren Typen wieder `Cat` und `Duck`. Für diese beiden Objekte müssen lediglich wieder die Methoden implementiert werden, die das Interface, also der Supertyp zur Verfügung stellt. In diesem Fall handelt es sich hierbei nur um die Methode `talk()`. Oben im Code sieht man, wie dies in der Sprache Golang möglich ist. Dazu muss lediglich das Objekt als Receiver-Typ vor die Methode geschrieben werden. Im Anschluss kann die Methode auf dem Objekt aufgerufen werden. Natürlich können die Subtyp Klassen beziehungsweise Structs auch hier wieder mehr Methoden und Member Variablen halten als der eigentliche Supertyp. Im Beispiel Code sieht man etwa, das die Methoden `run()` und `swim` zu den Structs hinzugefügt wurden. Es folgt ein weiteres Beispiel in Golang.

````Golang
type Person interface {
	getName() (string, string)
	getAge() int
}

type Student struct {
	firstName string
	lastName  string
	age       int
	semester  int
}

type Employee struct {
	firstName string
	lastName  string
	age       int
}

func (s Student) getName() (firstName string, lastName string) {
	return s.firstName, s.lastName
}

func (e Employee) getName() (firstName string, lastName string) {
	return e.firstName, e.lastName
}

func (s Student) getAge() int {
	return s.age
}

func (e Employee) getAge() int {
	return e.age
}

func printPersonData(p Person) {
	firstName, lastName := p.getName()
	age := p.getAge()

	student, ok := p.(Student) // check if student or not
	if ok {
		fmt.Printf("name: %s %s age: %d student: %t semester: %d\n", firstName, lastName, age, ok, student.semester)

	} else {
		fmt.Printf("name: %s %s age: %d student: %t\n", firstName, lastName, age, ok)
	}
}

func main() {
	var persons []Person
	persons = append(persons, Student{firstName: "alex", lastName: "mustermann", age: 20, semester: 3})
	persons = append(persons, Student{firstName: "max", lastName: "mustermann", age: 21, semester: 2})
	persons = append(persons, Employee{firstName: "lea", lastName: "musterfrau", age: 19})

	for i := range persons {
		printPersonData(persons[i])
	}
}
````

Das Beispiel oben zeigt einen sinnvollen Anwendungsfall von Polymorphie mit Subtyping. Das Programm soll zu allen Personengruppen den Namen und das Alter ausgeben. Zusätzlich soll es das Semester ausgeben, wenn es sich bei der Person um einen Studenten handelt. Dank der Polymorphie müssen hier nicht für jede Art von Person eine FOR-Schleife durchlaufen werden, sondern nur eine. Dies erhöht die Lesbarkeit bei Algorithmen erheblich. In Golang gibt es kein `instanceof`. Stattdessen wird beim Cast ein Boolean zurückgeliefert, welcher angibt, ob der Cast möglich ist. Dies ist vielleicht sogar noch etwas intuitiver als in Java. Die Überprüfung wird in unserem Fall dafür verwendet, um zu checken, ob es sich bei der Person um einen Studenten handelt. Ist dies der Fall, wird das Semester mit ausgeben.

# Parametrischer Polymorphismus

In Golang existiert bisher leider kein parametrischer Polymorphismus. Allerdings werden derzeit Überlegungen angestellt, dieses Konzept in die Sprache zu implementieren. Jedoch existiert dieser in Sprachen wie beispielsweise Java oder C++. Diese Art von Polymorphismus ist in Java unter dem Namen Generics und unter C++ mit dem Namen Templates bekannt. In diesem Abschnitt werden sie die Vorteile von parametrischer Polymorphie kennenlernen und wie diese unter Java angewandt wird. Es folgt ein Beispiel in Java, welches Generics veranschaulicht.

`````Java
public class Person implements Comparable<Person> {

	private String firstName;
	private String lastName;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public int compareTo(Person other) {
		return this.lastName.compareTo(other.lastName);
	}

}

public class Sort<T extends Comparable<T>> {

	public void bubbleSort(T list[]) {
		for (int i = 1; i < list.length; i++) {
			for (int j = 0; j < list.length - i; j++) {
				if (list[j].compareTo(list[j + 1]) > 0) {
					T temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
				}
			}
		}
	}
}

public static void main(String[] args) {
		Integer[] numbers = { 3, 2, 1, 4, 5, 6, 7, 9, 8 };
		Sort<Integer> sortNumbers = new Sort<>();
		sortNumbers.bubbleSort(numbers);
		System.out.println(Arrays.toString(numbers));

		String[] names = { "Remilia", "Reimu", "Marisa", "Sakuya", "Youmu" };
		Sort<String> sortNames = new Sort<>();
		sortNames.bubbleSort(names);
		System.out.println(Arrays.toString(names));

		Person[] persons = { new Person("Remilia", "Scarlet"), new Person("Reimu", "Hakurei"),
				new Person("Youmu", "Konpaku") };
		Sort<Person> sortPersons = new Sort<>();
		sortPersons.bubbleSort(persons);
		System.out.println();
		for (int i = 0; i < persons.length; i++) {
			System.out.println("Person " + i + ": " + persons[i].getFirstName() + " " + persons[i].getLastName());
		}
	}

`````



Das Beispiel zeigt, wie in Java mit Generics gearbeitet wird. Das Tolle an Generics ist, dass man eine Klasse oder eine Methode nicht für mehrere Typen schreiben muss. Statt den Typen direkt anzugeben, ersetzt man den Typen durch einen Platzhalter. Oben im Beispiel wird dafür der Buchstabe `T` verwendet, der den Typen darstellen soll. Das Beispiel zeigt eine Klasse `Sort`, die eine einfache Methode zum Sortieren einer beliebigen Datenstruktur zur Verfügung stellt. In dem Fall wird der Bubble-Sort verwendet. In Java ist es so, dass die Standarddatentypen in den Wrapper Klassen Integer, String oder Double die Vergleichsmethode `compareTo(T other)` direkt mit implementiert haben. Aus diesem Grund funktioniert der Vergleich der Elemente in der Sortierung ohne Weiteres. Dies ist aber nicht mehr der Fall, sobald man einen eigenen Typen definiert. Woher auch soll die Methode `CompareTo` wissen, auf welche Eigenschaften sie den Datentypen vergleichen soll? Um dies zu realisieren, muss zunächst das Interface `Comparable` implementiert werden. Im Anschluss wird gefordert, dass die Methode `CompareTo(T other)`implementiert werden muss. Dort kann man den Vergleich selbst bestimmen. Im Beispiel sieht man, dass sich dafür entschieden wurde, nach dem Nachnamen zu sortieren. Nun kann man mit der Klasse `Sort` und der Methode `bubbleSort` die Datenstruktur sortieren lassen.



# Zusammenfassung

Schlussendlich lässt sich feststellen, dass Polymorphie die Lesbarkeit deutlich erhöht und redundanten Code vermeidet. Dabei ist es egal, welche Programmiersprache verwendet wird. Wenn möglich, sollte Polymorphie daher verwendet werden.

