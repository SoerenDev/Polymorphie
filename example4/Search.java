package example4;
import java.util.Arrays;

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

}
