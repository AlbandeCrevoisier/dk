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

func newsPrinter(printOrder chan string) {
	conn, err := redis.Dial("tcp", "localhost:6379")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer conn.Close()
	for {
		w := strings.Split(<-printOrder, "ð")
		tag, news_id := w[0], w[1]
		title, _ := redis.String(conn.Do("get", "news:" + news_id))
		body, _ := redis.String(conn.Do("get", "news:" + news_id + ":body"))
		fmt.Println(tag + " --- " + title + " --- " + body)
	}
}

/* Subscribe to and print the content of the given channel. */
func subChan(chanName string, printOrder chan string, conn redis.Conn) {
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
			/* Hack to keep atomic operations: use a separator that should
			not appear in the tag nor the title in order to do only one send. */
			printOrder <- r.Channel + "ð" + string(r.Data)
		}
	}
}

func main() {
	c := make(chan string)
	printOrder := make(chan string)
	conn, err := redis.Dial("tcp", "localhost:6379")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer conn.Close()
	go newsPrinter(printOrder)
	go getChanName(c, conn)
	for {
		go subChan(<-c, printOrder, conn)
	}
}
