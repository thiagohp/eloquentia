# Eloquentia: A quirky, tag-based blog engine


## Why?

Why not? :) I (Thiago) for a long time had an idea of writing a blog engine that was heavily based on tags and
which provided specific the option of having per-tag and per-post style. In addition, it would be used as a light CMS too,
so that's why Eloquentia uses the word 'page', not 'post'.

Another reason was creating a pratical, useful project to learn some new interesting stuff like Apache Tapestry 5.4
(due to the project at work, I barely had used T5.2 and later), MongoDB (I've never used a NoSQL database before) and
Twitter Bootstrap.

## Tags, tags everywhere

Each tag has:

* A name, which can only use lowercase letters, digits or dashes ('-')
* A boolean 'subdomain' field which defines affects the tag URL.
* A title
* An optional subtitle, which is used in the tag page and in pages which have this tag as their first one.
* CSS which will be added to its page and to all pages which have that tag.

The URL of a tag is based on its the name. For example, if a tag name is `tapestry`, the URL
will be `tapestry.domain.com` if it's marked as a subdomain and `domain.com/tag/tapestry` otherwise.

## Pages (posts)

Each page has:
* An URI, which is used to define the page's URL. It can use lowercase letters, digits, dashes ('-') and slashes ('-'),
but cannot start or end with a slash.
* A title
* A teaser, which is a short text describing the page and used in the page listing and RSS feed.
* The content itself.
* The date (including time) in which it was posted. Dates in the future are used to schedule when a given page should appear.
* The date (including time) in which it was last edited
* An author

The URL of a given page depends on its first (as in order) of its tags, if any. Suppose a page with
URI `stuff/eloquentia` and tags `project` and `java`. If `project` is a tag marked as a subdomain, the URL
of the page will be `http://project.domain.com/stuff/eloquentia`. Otherwise, it'll be 
`http://domain.com/stuff/eloquentia`.

## Database

Eloquentia is configured to use a local MongoDB instance without authentication. Its `page`, `tag` and `user`
collections are supposed to be in the `eloquentia` database. This isn't configurable yet.

## Eloquentia is powered by

* Java
* Apache Tapestry 5.4 - http://tapestry.apache.org - Web framework
* MongoDB - http://www.mongodb.org - NoSQL database (document-oriented)
* MongoJack - http://mongojack.org - POJO to MongoDB BSON object mapper based on Jackson JSON Processor.
* Twitter Bootstrap 3 - http://getbootstrap.com - CSS framework
* WYMeditor - http://www.wymeditor.org - rich text editor
* Prism.js - http://prismjs.com - code syntax highlighter
