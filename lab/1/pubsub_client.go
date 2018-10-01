package main

import (
	"fmt"
	"strings"
	"os"
	"bufio"
	"github.com/gomodule/redigo/redis"
)

/* Reading standard input is blocking, so it is read in a go routine. */
func getChanName(c chan string, conn redis.Conn) {
	scanner := bufio.NewScanner(os.Stdin)
	psc := redis.PubSubConn{Conn: conn}
	for {
		scanner.Scan()
 		inText := scanner.Text()
		w := strings.Split(inText, " ")
		if strings.Compare(w[0], "sub") == 0 {
			c <- w[1]
		} else if strings.Compare(w[0], "unsub") == 0 {
			psc.Unsubscribe(w[1])
		} else {
			fmt.Println("usage: [sub | unsub] <channel name>")
		}
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
			switch r.Kind {
			case "subscribe":
				fmt.Println("Subscribed to: " + r.Channel)
			case "unsubscribe":
				fmt.Println("Unsubscribed to: " + r.Channel)
				return
			}
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
	go getChanName(c, conn)
	for {
		go subChan(<- c, conn)
	}
}
