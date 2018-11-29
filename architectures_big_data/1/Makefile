SOURCES = *.go

all: pubsub_client pubsub_server

clean:
	rm -rf pubsub_client pubsub_server

%: %.go
	go build $^
