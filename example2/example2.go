// Structural subtyping

package main

import "fmt"

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
