package main

import (
	"fmt"
	"os"
)

type Element struct {
	id int
	isEnd bool
	name string
}

func main() {
	if len(os.Args) != 3 {
		fmt.Println("usage: ./1 [ xml_file ] [xpath_query ]")
		return
	}

}
