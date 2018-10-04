# Lab Report #

This is a lab about redis. We were asked to implement a Publish/Subscribe news
system: see `redis.pdf` for more informations about the lab instructions.

## Input Data ##

The data is provided as an XML document with the following structure:
```
<articles>
  <article title="Title">
    <tag name="firstTag">
    <tag name="secondTag">
    <body>
      The text of the article.
    </body>
  </article>
</articles>
```
There can be an arbitrary number of articles & of tags.

## Redis  model ##

The storage model I used is as follows:

Data structure | key | value
--- | --- | ---
Simple key-value | news:<news_id> | Title
Set | news:<news_id>:tags | [ <tag> ]
Simple key-value | news:<news_id>:body | Body of the article
Hash | tags:news | <tag, news_id>

## Server ##

The server parses the XML document using SAX and stores the data in the redis
storage. As it does so, it creates a channel per tag and publishes on it the id
of each news with this tag.

## Client ##

The client reads the standard input where it can accept two commands:

* `sub [tag]` which subscribes to the channel associated with the tag.
* `unsub [tag]` which unsubscribes from the channel associated with the tag.

When the client subscribed to a channel, its content will be continuously read.
Whenever a message is received, the corresponding article is fetched from the
redis databes and printed on the standard output.

## Running the project ##

A `Makefile` is provided to compile and clean the project.

Once the code has been compiled, start any number of clients and subscribe each
one to any number of tags. Then, execute the server (it only runs once, as it
returns when the XML has been entirely read).

## Issues ##

The tags must be known so that the clients can subscribe to the corresponding
channels. A cleaner solution would have been to first store all the data and
print each title followed by its tags, then publish on each tag channel the id
of the corresponding news. However, the code would have been twice as long and
I'd rather keep it short for the sake of quick readability: it is only a lab,
not a fully fledged project.

Another issue is that once a client unsubscribed from one channel, it changes
the context of the redis connection, which basically blocks the connection if
the client is still subscribed to other channels (may be a redis concurrent
access issue).

## Sample output of the client ##

```
subs tag1
usage: [sub | unsub] <channel name>
sub tag1
Subscribed to: tag1
sub tag2
Subscribed to: tag2
tag1 --- News 1 --- This is the first news.
tag2 --- News 1 --- This is the first news.
tag1 --- News 2 --- This is the second news.
tag2 --- News 3 --- This is the third news.
unsub tag1
Unsubscribed to: tag1
```
