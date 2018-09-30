package main

import (
	"fmt"
	"os"
	"bufio"
	"github.com/gomodule/redigo/redis"
)

/* Reading standard input is blocking, so it is read in a go routine. */
func getChanName(c chan string) {
	scanner := bufio.NewScanner(os.Stdin)
	for {
		scanner.Scan()
 		c <- scanner.Text()
	}
}

/* Subscribe to and print the content of the given channel. */
func subChan(chanName string, conn redis.Conn) {
	psc := redis.PubSubConn{Conn: conn}
	psc.Subscribe(chanName)
	for {
		switch r := psc.Receive().(type) {
		case error:
			fmt.Println(r.Error())
			return
		case redis.Subscription:
			fmt.Println("Subscribed to: " + r.Channel)
		case redis.Message:
			fmt.Println(r.Channel + ":" + string(r.Data))
		}
	}
}

func main() {
	c := make(chan string)
	conn, err := redis.Dial("tcp", "localhost:6379")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer conn.Close()
	go getChanName(c)
	for {
		go subChan(<- c, conn)
	}
}
