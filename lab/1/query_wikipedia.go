package main

import (
	"fmt"
	"log"
//	"os"
//	"encoding/xml"
	"net/http"
	"io/ioutil"
)

func main() {
	urlPrefix := "https://en.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&exintro=&explaintext=&titles="
	res, err := http.Get(urlPrefix + "Paris")
	if err != nil {
		log.Fatal(err)
	}
	b, err := ioutil.ReadAll(res.Body)
	res.Body.Close()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(b)
}

