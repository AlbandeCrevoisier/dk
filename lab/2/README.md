# MongoDB lab #

## Setting up ##

I'm on Archlinux and mongodb management can be done through packages, so the set
up commands are rather simple:

1.
`systemctl start mongodb.service` to start the server via systemd, runs on port
27017.
`mongoimport moviepeople-10.json -c moviepeopleten` to import the JSON file in
a collection named moviepeopleten (otherwise, the collection is named as the
file, but mongodb does not accept a number as the last element of
an identifier.
`mongo` to start a client.
`show collections` to find the one we need, `moviepeopleten` here, then:
`db.moviepeopleten.find()` to get all its documents.

## Import, query ##

2. find Anabela: 
```
> db.moviepeople.find({"person-name": /Anabela/})
{ "person-name" : "Teixeira, Anabela", "info" : { "trivia" : [ "Her favorite actor is 'Marcello Mastroianni' (qv) and her favorite actress is 'Juliette Binoche' (qv).", "Partner of musician Frederico Pereira.", "Has two brothers, one biological and one adopted.", "Vice-President of the Portuguese Cinema Academy.", "She was first noticed by the public with the Tv series A Viúva do Enforcado (1992).", "Made her theater debut in 1992 in the play Os Processos of Dostolevsky.", "Did some workshops of dance: dance in movement with Peter Diez and dance classes with Madalena Vitorino.", "Besides Portuguese, she speaks English and French." ], "birthnotes" : [ "Lisbon, Portugal" ], "birthdate" : [ "18 May 1973" ], "birthname" : [ "Teixeira, Anabela Cristina Alves" ], "interviews" : [ "TV Guia (Portugal), 1997, Iss. 960, pg. 24-25, by: Pedro Teixeira", "A Capital (Portugal), 30 April 1998, pg. 55, by: Helena Mata" ] } }
```
3. Birthplace of Spielberg:
```
> db.moviepeople.find({"person-name" : /Spielberg/}, {"_id" : 0, "person-name" : 1, "info.birthnotes" : 1})
{ "person-name" : "Spielberg, Steven", "info" : { "birthnotes" : [ "Cincinnati, Ohio, USA" ] } }
```
4. Number of people born in Lisbon:
```
> db.moviepeople.find({"info.birthnotes" : /Lisbon/}, {"info.birthnotes": 1}).count()
106
```
5. People taller than 170cm:
```
> db.moviepeople.find({"info.height": {$gt: "170 cm", $gt: "5' 5"}}).count()
197
```

---
**Everything below this ruler has been done after the deadline, please disregard
for grading.**

6. Name of people whose information contains "Opera":

First, create a text index on all the fields of the document that have a string
field-value:
```
> db.moviepeople.createIndex({"$**":"text"})
{
        "createdCollectionAutomatically" : false,
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "ok" : 1
}
```

Then, make a text query:
```
> db.moviepeopleten.find({$text: {$search: "opera"}}, {"person-name" : 1})
{ "_id" : ObjectId("5bc0cb4eb534c7dbe6e1f6df"), "person-name" : "Hemsworth, Liam" }
```

## More querying ##

7. For each person whose birthplace is known, find lat:long:pop of that city,
if the info exists:
```
```
