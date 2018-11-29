.PHONY: clean test

1: 1.go
	go build 1.go

test:
	./1 test1 //a/b
	./1 test1 //a/b/c
	./1 test2 //a/b
	./1 test2 //a/b/c
	./1 test2 //a

clean:
	rm -f 1
