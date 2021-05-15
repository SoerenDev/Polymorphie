package main

import "fmt"

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
