# Lab 1 #

## Exercise 0 ##

```
http://CannesFilmFestival -- http://date -> http://1994
                          |- http://film -> http://PulpFiction -- http://date -> http://1994
                          |                                    |- http://directedBy -> http://QuentinTarantino
                          |                                    |- http://starring -> http://JohnTravolta
                          |- http://country -> http://USA
http://CannesFilmFestival -- http://date -> http://1997
                          |- http://film -> http://TasteOfCherry -- http://date -> http://1997
                          |                                      |- http://directedBy -> http://AbbasKiarostami
                          |                                      |-http://starring -> http://HomayounErshadi
                          |- http://country -> http://Iran

http://SaturdayNightFever -- http://date -> http://1977
                          |- http://directedBy -> http://JohnBadham
                          |- http://starring -> http://JohnTravolta
```

## Exercise 1 ##

```
@prefix res: <http://dbpedia.org/ressource/>
@prefix onto: <http://dbpedia.org/ontology/>
@prefix t: <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>

res:JohnTravolta t: onto:Actor
res:JohnTravolta t: onto:Producer
res:JohnTravolta onto:starring res:Basic(film)
res:JohnTravolta onto:starring res:Shout(film)
```

## Exercise 2 ##

### Q1 ###

Paper

### Q2 ###

mercury, venus, earth, moon, mars, phobos, deimos

### Q3 ###

(a) 2 (b) 3 (c) 1

## Exercise 3 ##

* Name of all movies.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?name
WHERE {
  ?movie rdf:type ex:Movie .
  ?movie rdfs:label ?name
}
```

* Names of movies and directors sorting in descending order by the year the
movie appeared.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?name ?dir
WHERE {
  ?movie rdf:type ex:Movie .
  ?movie rdfs:label ?name .
  ?movie ex:year ?date .
  ?movie ex:director ?director .
  ?director foaf:familyName ?dir
}
ORDER BY DESC(?date)
```

* Names and directors of all movies before 1996.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?name ?dir
WHERE {
  ?movie rdf:type ex:Movie .
  ?movie rdfs:label ?name .
  ?movie ex:year ?date .
  ?movie ex:director ?director .
  ?director foaf:familyName ?dir
  FILTER (?date < "1996"^^xsd:gYear)
}
```

* Names of all movies whose genre is Crime.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?name
WHERE {
  ?movie rdf:type ex:Movie .
  ?movie ex:genre ex:Crime .
  ?movie rdfs:label ?name
}
```

* Names of all actors who were above 50 in 2016.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?name
WHERE {
  ?actor rdf:type ex:Actor .
  ?actor foaf:familyName ?name .
  ?actor ex:birthYear ?bday
  FILTER (?bday > "1966"^^xsd:gYear)
}
```

* Names of all movies whose director were above 70 in 2016.
```
PREFIX ex: <http://example.org/movies/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?name
WHERE {
  ?movie rdf:type ex:Movie .
  ?movie ex:director ?director .
  ?movie rdfs:label ?name .
  ?director ex:birthYear ?bday
  FILTER (?bday > "1946"^^xsd:gYear)
}
```

## Exercise 4 ##

See 1.md & movies.ttl

## Exercise 5 ##

```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbp: <http://dbpedia.org/property/>
PREFIX dbo: <http://dbpedia.org/ontology/>
PREFIX yago: <http://yago-knowledge.org/resource/>
PREFIX res: <http://dbpedia.org/resource/>
SELECT ?height
WHERE {
  res:Claudia_Schiffer dbo:height ?height
}

1m81
```

## Exercie 6 ##


