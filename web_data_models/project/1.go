/* XPath: Simple Path with a stack. */

package main

import (
	"fmt"
	"strings"
	"os"
	"bufio"
)

func main() {
	if len(os.Args) != 3 {
		fmt.Println("usage: ./1 [ xml_file ] [xpath_query ]")
		return
	}

	query := strings.Split(os.Args[2], "/")[2:]
	p := 0
	stack := make([]int, 0)

	f, err := os.Open(os.Args[1])
	if err != nil {
		fmt.Println(err)
		return
	}
	defer f.Close()
	id := -1

	scanner := bufio.NewScanner(f)
	for scanner.Scan() {
		w := strings.Split(scanner.Text(), " ")
		if strings.Compare(w[0], "1") == 0 {
			if len(stack) == 1 {
				stack = stack[:0]
				p = 0
			} else {
				stack = stack[:len(stack) - 1]
				p = stack[len(stack) - 1]
			}
			continue
		}
		id++
		if strings.Compare(w[1], query[p]) == 0 {
			p++
			if p == len(query) {
				fmt.Println(id)
				p = 0
				if strings.Compare(w[1], query[0]) == 0 {
					p = 0
				}
			}
			stack = append(stack, p)
		} else {
				p = 0
				stack = append(stack, p)
		}
	}
}
