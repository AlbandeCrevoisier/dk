package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
	"encoding/xml"
	"github.com/gomodule/redigo/redis"
)

func main() {
	conn, err := redis.Dial("tcp", "localhost:6379")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer conn.Close()

	f, _ := os.Open("data.xml")
	defer f.Close()
	d := xml.NewDecoder(f)
	news_id := 0
	id := strconv.Itoa(news_id)
	for {
		t, _ := d.Token()
		if t == nil {
			break
		}
		switch et := t.(type) {
		case xml.StartElement:
			if et.Name.Local == "article" {
				news_id++
				id = strconv.Itoa(news_id)
				conn.Do("set", "news:" + id, et.Attr[0].Value)
			} else if et.Name.Local == "tag" {
				conn.Do("sadd", "news:" + id + ":tags", et.Attr[0].Value)
				conn.Do("hset", "tags:news", et.Attr[0].Value, id)
				conn.Do("publish", et.Attr[0].Value, id)
			}
		case xml.CharData:
			str := strings.TrimSpace(string([]byte(et)))
			if str != "" {
				conn.Do("set", "news:" + id + ":body", str)
			}
		}
	}
}
