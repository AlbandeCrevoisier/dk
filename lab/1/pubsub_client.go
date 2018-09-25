package main

import (
	"fmt"
	"github.com/gomodule/redigo/redis"
)

func main() {
	c, err := redis.Dial("tcp", "localhost:6379")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer c.Close()
	psc := redis.PubSubConn{Conn: c}
	psc.Subscribe("chan1")
	for {
		switch r := psc.Receive().(type) {
		case error:
			fmt.Println("Error")
			return
		case redis.Message:
			fmt.Printf("%s %s\n", r.Channel, r.Data)
		}
	}
}

