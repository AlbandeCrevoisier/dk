# Lab Report #

This is a lab about redis. We were asked to implement a Publish/Subscribe news
system: see `redis.pdf` for mor informations about the instructions.

### Input ###

The data is provided as an XML document with the following structure:
```
<article title="Title">
  <tag name="firstTag">
  <tag name="secondTag">
  <body>
    The text of the article.
  </body>
</article>
```
There can be an arbitrary number of tags.

### Redis  model ###

The storage model I used is as follows:

Data structure | key | value
--- | --- | ---
Simple key-value | news:<news_id> | Title
Set | news:<news_id>:tags | [set of tags]
Simple key-value | news:<news_id>:body | Body of the article
Hash | tags:news | <tag, news_id>

### Server ###

The server parses the XML document using SAX and stores the data in the redis
storage. As it does so, it creates creates a channel per tag and publishes on
it the id of each news with this tag.

### Client ###

The client reads the standard input where it can accept two commands:

* `sub [tag]` which subscribes to the channel associated with a tag.
* `unsub [tag]` which unsubscribes to the channel associated with a tag.

When the client subscribed to a channel, its content will be continuously read.
Whenever a message is received, the corresponding article is fetched from the
redis databes and printed on the standard output.

### Running the project ###

A Makefile is provided to compile and clean the project.

Once the code has been compiled, start any number of clients and subscribe each
one to any number of tags. Then, execute the server (it only runs once, as it
returns when the XML has been entirely read).

### Issues ###

The tags must be known so that the clients can subscribe to the corresponding
channels. A cleaner solution would have been to first store all the data and
print each title followed by its tags, then publish on each tag channel the id
of the corresponding news.

