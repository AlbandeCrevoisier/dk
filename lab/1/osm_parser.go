package main

import (
	"fmt"
	"os"
	"encoding/xml"
)

func main() {
	names := make(map[string]bool)
	f, _ := os.Open("map.osm")
	defer f.Close()
	d := xml.NewDecoder(f)
	for {
		t, _ := d.Token()
		if t == nil {
			break
		}
		switch et := t.(type) {
		case xml.StartElement:
			if et.Name.Local == "tag" && et.Attr[0].Value == "name" {
				names[et.Attr[1].Value] = true
			}
		}
	}
	for k, _ := range names {
		fmt.Println(k)
	}
}
